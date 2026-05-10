// filepath: app/src/main/kotlin/com/dopaminecat/data/service/UsageTrackingService.kt
package com.dopaminecat.data.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.IBinder
import androidx.core.app.ServiceCompat
import com.dopaminecat.domain.usecase.CheckGoalExceedUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 실시간 스크린타임 감시 포그라운드 서비스.
 *
 * [POLL_INTERVAL_MS] 주기로 [CheckGoalExceedUseCase] 를 호출해 목표 초과 여부를 확인하고
 * 고양이 상태(happiness, trashCount)를 Room DB 에 반영한다.
 * Room Flow 를 구독하는 위젯·ViewModel 은 DB 변경을 자동으로 수신한다.
 *
 * UsageStatsManager 는 약 5분 간격으로 데이터를 갱신하므로
 * [POLL_INTERVAL_MS] 를 그보다 짧게 설정해도 실질적 정밀도는 5분이다.
 */
@AndroidEntryPoint
class UsageTrackingService : Service() {

    @Inject
    lateinit var checkGoalExceedUseCase: CheckGoalExceedUseCase

    @Inject
    lateinit var notificationHelper: NotificationHelper

    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.Default + serviceJob)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> startTracking()
            ACTION_STOP  -> stopTracking()
        }
        return START_STICKY
    }

    private fun startTracking() {
        ServiceCompat.startForeground(
            this,
            NotificationHelper.NOTIFICATION_ID_FOREGROUND,
            notificationHelper.buildForegroundNotification(),
            ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC,
        )

        serviceScope.launch {
            while (isActive) {
                runCatching { checkGoalExceedUseCase() }
                    .onSuccess { result ->
                        if (result.hasExceeded) {
                            notificationHelper.showGoalExceededNotification(result.exceededGoals)
                        }
                    }
                delay(POLL_INTERVAL_MS)
            }
        }
    }

    private fun stopTracking() {
        ServiceCompat.stopForeground(this, ServiceCompat.STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    override fun onDestroy() {
        serviceScope.cancel()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    companion object {
        const val ACTION_START = "com.dopaminecat.action.START_TRACKING"
        const val ACTION_STOP  = "com.dopaminecat.action.STOP_TRACKING"

        private const val POLL_INTERVAL_MS = 5 * 60 * 1000L // 5분

        fun startIntent(context: Context): Intent =
            Intent(context, UsageTrackingService::class.java).apply {
                action = ACTION_START
            }

        fun stopIntent(context: Context): Intent =
            Intent(context, UsageTrackingService::class.java).apply {
                action = ACTION_STOP
            }
    }
}
