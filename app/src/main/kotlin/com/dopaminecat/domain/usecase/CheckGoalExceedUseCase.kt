// filepath: app/src/main/kotlin/com/dopaminecat/domain/usecase/CheckGoalExceedUseCase.kt
package com.dopaminecat.domain.usecase

import com.dopaminecat.domain.model.ExceededGoal
import com.dopaminecat.domain.repository.CatRepository
import com.dopaminecat.domain.repository.GoalRepository
import com.dopaminecat.domain.repository.UsageRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * 사용자가 설정한 목표 중 초과된 항목을 확인하고 고양이 상태를 갱신한다.
 *
 * 호출 주체: UsageTrackingService (5분 주기 폴링)
 *
 * 비즈니스 규칙:
 * - 초과된 목표 1개당 happiness -[HAPPINESS_PENALTY_PER_EXCEEDED] 감소
 * - 초과된 목표 1개당 쓰레기 +[TRASH_PER_EXCEEDED] 추가
 * - happiness 하한 0, 쓰레기 상한 [MAX_TRASH_COUNT]
 * - 초과 없음 → cat 상태 변경 없음 (회복은 자정 보상에서만 발생)
 */
class CheckGoalExceedUseCase @Inject constructor(
    private val goalRepository: GoalRepository,
    private val usageRepository: UsageRepository,
    private val catRepository: CatRepository,
) {
    companion object {
        const val HAPPINESS_PENALTY_PER_EXCEEDED = 10
        const val TRASH_PER_EXCEEDED = 1
        const val MAX_TRASH_COUNT = 20
    }

    suspend operator fun invoke(): GoalCheckResult {
        val cat = catRepository.getCatStream().first()
        val activeGoals = goalRepository.getAllGoalsStream().first().filter { it.isActive }

        if (activeGoals.isEmpty()) {
            return GoalCheckResult(
                exceededGoals = emptyList(),
                newHappiness = cat.happiness,
                newTrashCount = cat.trashCount,
            )
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

        if (exceededGoals.isEmpty()) {
            return GoalCheckResult(
                exceededGoals = emptyList(),
                newHappiness = cat.happiness,
                newTrashCount = cat.trashCount,
            )
        }

        val newHappiness = (cat.happiness - exceededGoals.size * HAPPINESS_PENALTY_PER_EXCEEDED)
            .coerceIn(0, 100)
        val newTrashCount = (cat.trashCount + exceededGoals.size * TRASH_PER_EXCEEDED)
            .coerceAtMost(MAX_TRASH_COUNT)

        catRepository.updateCatState(newHappiness, newTrashCount)

        return GoalCheckResult(
            exceededGoals = exceededGoals,
            newHappiness = newHappiness,
            newTrashCount = newTrashCount,
        )
    }
}

data class GoalCheckResult(
    val exceededGoals: List<ExceededGoal>,
    val newHappiness: Int,
    val newTrashCount: Int,
) {
    val hasExceeded: Boolean get() = exceededGoals.isNotEmpty()
}
