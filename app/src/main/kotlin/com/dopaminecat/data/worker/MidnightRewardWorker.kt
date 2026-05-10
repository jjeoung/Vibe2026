// filepath: app/src/main/kotlin/com/dopaminecat/data/worker/MidnightRewardWorker.kt
package com.dopaminecat.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.dopaminecat.data.service.NotificationHelper
import com.dopaminecat.domain.usecase.ClaimMidnightRewardUseCase
import com.dopaminecat.domain.usecase.RewardResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

/**
 * 매일 자정 직후(00:01) 에 한 번 실행되는 Worker.
 *
 * 실행 순서:
 * 1. [ClaimMidnightRewardUseCase] 호출 → 오늘 목표 달성 여부 판정
 * 2. 결과에 따라 알림 표시
 * 3. 다음 날 자정 실행을 [WorkScheduler] 로 재예약 (자가 체인)
 *
 * [HiltWorker] + [AssistedInject] 조합:
 * Context/WorkerParameters 는 WorkManager 가 주입하고,
 * 나머지 의존성은 Hilt 가 주입한다.
 * DopamineCatApplication 에서 HiltWorkerFactory 를 등록해야 동작한다.
 */
@HiltWorker
class MidnightRewardWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val claimMidnightRewardUseCase: ClaimMidnightRewardUseCase,
    private val workScheduler: WorkScheduler,
    private val notificationHelper: NotificationHelper,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return runCatching { claimMidnightRewardUseCase() }
            .fold(
                onSuccess = { result ->
                    handleRewardResult(result)
                    scheduleNextMidnight()
                    Result.success()
                },
                onFailure = {
                    // 최대 3회 재시도 후 포기 — 다음 날 Application.onCreate 에서 재예약됨
                    if (runAttemptCount < MAX_RETRY_COUNT) Result.retry()
                    else {
                        scheduleNextMidnight()
                        Result.failure()
                    }
                },
            )
    }

    private fun handleRewardResult(result: RewardResult) {
        when (result) {
            is RewardResult.Rewarded       -> notificationHelper.showRewardNotification(result.coinsAwarded)
            is RewardResult.GoalsExceeded  -> notificationHelper.showGoalExceededNotification(result.exceededGoals)
            is RewardResult.AlreadyClaimed -> Unit
            is RewardResult.NoActiveGoals  -> Unit
        }
    }

    /** 다음 날 자정 실행을 예약한다. REPLACE 로 갱신해 중복 예약을 방지한다. */
    private fun scheduleNextMidnight() {
        workScheduler.scheduleMidnightReward(ExistingWorkPolicy.REPLACE)
    }

    companion object {
        const val WORK_NAME      = "MidnightRewardWorker"
        const val TAG            = "midnight_reward"
        private const val MAX_RETRY_COUNT = 3
    }
}
