// filepath: app/src/main/kotlin/com/dopaminecat/domain/usecase/ClaimMidnightRewardUseCase.kt
package com.dopaminecat.domain.usecase

import com.dopaminecat.domain.model.ExceededGoal
import com.dopaminecat.domain.repository.CatRepository
import com.dopaminecat.domain.repository.CoinRepository
import com.dopaminecat.domain.repository.GoalRepository
import com.dopaminecat.domain.repository.UsageRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * 자정에 당일 목표 달성 여부를 판정하고 물고기 코인을 지급한다.
 *
 * 호출 주체: MidnightRewardWorker (자정 직후 1회 실행)
 *
 * 비즈니스 규칙:
 * - 이미 오늘 보상을 받은 경우 → [RewardResult.AlreadyClaimed]
 * - 활성 목표가 없는 경우      → [RewardResult.NoActiveGoals]
 * - 하나라도 초과된 목표 있음  → [RewardResult.GoalsExceeded] (코인 미지급)
 * - 모든 목표 달성             → [RewardResult.Rewarded]
 *     · 코인: 달성 목표 수 × [COINS_PER_MET_GOAL]
 *     · happiness: +[HAPPINESS_BONUS] (상한 100)
 *     · 쓰레기: -[TRASH_REDUCTION] (하한 0)
 */
class ClaimMidnightRewardUseCase @Inject constructor(
    private val goalRepository: GoalRepository,
    private val usageRepository: UsageRepository,
    private val catRepository: CatRepository,
    private val coinRepository: CoinRepository,
) {
    companion object {
        const val COINS_PER_MET_GOAL = 3
        const val HAPPINESS_BONUS = 15
        const val TRASH_REDUCTION = 5
    }

    suspend operator fun invoke(): RewardResult {
        if (coinRepository.hasReceivedRewardToday()) {
            return RewardResult.AlreadyClaimed
        }

        val activeGoals = goalRepository.getAllGoalsStream().first().filter { it.isActive }

        if (activeGoals.isEmpty()) {
            return RewardResult.NoActiveGoals
        }

        val usageMap = usageRepository.getTodayUsageMinutesForPackages(
            packageNames = activeGoals.map { it.packageName },
        )

        val exceededGoals = activeGoals.mapNotNull { goal ->
            val usedMinutes = usageMap[goal.packageName] ?: 0
            if (usedMinutes > goal.dailyLimitMinutes) {
                ExceededGoal(
                    goal = goal,
                    usedMinutes = usedMinutes,
                    exceededByMinutes = usedMinutes - goal.dailyLimitMinutes,
                )
            } else null
        }

        if (exceededGoals.isNotEmpty()) {
            return RewardResult.GoalsExceeded(exceededGoals = exceededGoals)
        }

        // 모든 목표 달성 → 보상 지급
        val coinsToAward = activeGoals.size * COINS_PER_MET_GOAL
        coinRepository.addReward(
            amount = coinsToAward,
            reason = "Daily goal achievement (${activeGoals.size} goals met)",
        )

        val cat = catRepository.getCatStream().first()
        val newHappiness = (cat.happiness + HAPPINESS_BONUS).coerceAtMost(100)
        val newTrashCount = (cat.trashCount - TRASH_REDUCTION).coerceAtLeast(0)
        catRepository.updateCatState(newHappiness, newTrashCount)

        return RewardResult.Rewarded(
            coinsAwarded = coinsToAward,
            happinessGained = newHappiness - cat.happiness,
            trashRemoved = cat.trashCount - newTrashCount,
        )
    }
}

sealed class RewardResult {
    data class Rewarded(
        val coinsAwarded: Int,
        val happinessGained: Int,
        val trashRemoved: Int,
    ) : RewardResult()

    data object AlreadyClaimed : RewardResult()

    data object NoActiveGoals : RewardResult()

    data class GoalsExceeded(
        val exceededGoals: List<ExceededGoal>,
    ) : RewardResult()
}
