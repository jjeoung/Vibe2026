// filepath: app/src/main/kotlin/com/dopaminecat/data/worker/WorkScheduler.kt
package com.dopaminecat.data.worker

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkScheduler @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    /**
     * 다음 자정(00:01) 에 [MidnightRewardWorker] 가 실행되도록 예약한다.
     *
     * @param policy
     *  - [ExistingWorkPolicy.KEEP]    앱 최초 실행·재시작 시 기존 예약 유지
     *  - [ExistingWorkPolicy.REPLACE] 기기 재부팅·Worker 자가 재예약 시 갱신
     */
    fun scheduleMidnightReward(policy: ExistingWorkPolicy = ExistingWorkPolicy.KEEP) {
        val delayMillis = calculateDelayToNextMidnightMillis()

        val request = OneTimeWorkRequestBuilder<MidnightRewardWorker>()
            .setInitialDelay(delayMillis, TimeUnit.MILLISECONDS)
            .addTag(MidnightRewardWorker.TAG)
            .build()

        WorkManager.getInstance(context)
            .enqueueUniqueWork(MidnightRewardWorker.WORK_NAME, policy, request)
    }

    fun cancelMidnightReward() {
        WorkManager.getInstance(context).cancelUniqueWork(MidnightRewardWorker.WORK_NAME)
    }

    /**
     * 현재 시각 → 내일 00:01 까지의 밀리초를 계산한다.
     * 00:00 정각이 아닌 00:01 을 목표로 하는 이유:
     * 자정 경계 직전의 네트워크·DB 지연을 피하기 위함.
     */
    private fun calculateDelayToNextMidnightMillis(): Long {
        val now = System.currentTimeMillis()
        val nextMidnight = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 1)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
        return (nextMidnight - now).coerceAtLeast(0L)
    }
}
