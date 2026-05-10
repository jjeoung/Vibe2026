// filepath: app/src/main/kotlin/com/dopaminecat/DopamineCatApplication.kt
package com.dopaminecat

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.dopaminecat.data.service.NotificationHelper
import com.dopaminecat.data.worker.WorkScheduler
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class DopamineCatApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var notificationHelper: NotificationHelper

    @Inject
    lateinit var workScheduler: WorkScheduler

    override fun onCreate() {
        super.onCreate()

        // 알림 채널은 앱 시작 시 항상 등록 (이미 존재하면 no-op)
        notificationHelper.createNotificationChannels()

        // 최초 설치 또는 업데이트 후 Worker 가 없을 때를 대비한 안전망 예약
        // KEEP 정책: 이미 예약된 작업이 있으면 건드리지 않음
        workScheduler.scheduleMidnightReward()
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(
                if (BuildConfig.DEBUG) android.util.Log.DEBUG
                else android.util.Log.ERROR
            )
            .build()
}
