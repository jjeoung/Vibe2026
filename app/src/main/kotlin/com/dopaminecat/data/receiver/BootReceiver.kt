// filepath: app/src/main/kotlin/com/dopaminecat/data/receiver/BootReceiver.kt
package com.dopaminecat.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.work.ExistingWorkPolicy
import com.dopaminecat.data.service.UsageTrackingService
import com.dopaminecat.data.worker.WorkScheduler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * 기기 재부팅 및 앱 업데이트 후 추적 서비스와 WorkManager 를 재시작한다.
 *
 * 재부팅 후에는 모든 WorkManager 작업이 취소되므로 [WorkScheduler] 로 재예약이 필수다.
 * [Intent.ACTION_BOOT_COMPLETED] 수신자는 AndroidManifest 에 RECEIVE_BOOT_COMPLETED 선언이 필요하다.
 */
@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {

    @Inject
    lateinit var workScheduler: WorkScheduler

    override fun onReceive(context: Context, intent: Intent) {
        val isBootCompleted   = intent.action == Intent.ACTION_BOOT_COMPLETED
        val isPackageReplaced = intent.action == Intent.ACTION_MY_PACKAGE_REPLACED

        if (!isBootCompleted && !isPackageReplaced) return

        // 포그라운드 서비스 재시작 (API 26+: startForegroundService 필수)
        ContextCompat.startForegroundService(
            context,
            UsageTrackingService.startIntent(context),
        )

        // 재부팅으로 소멸된 WorkManager 작업 재등록
        workScheduler.scheduleMidnightReward(policy = ExistingWorkPolicy.REPLACE)
    }
}
