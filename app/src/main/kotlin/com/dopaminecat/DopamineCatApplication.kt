// filepath: app/src/main/kotlin/com/dopaminecat/DopamineCatApplication.kt
package com.dopaminecat

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class DopamineCatApplication : Application(), Configuration.Provider {

    /**
     * WorkManager 기본 자동 초기화를 AndroidManifest에서 제거했으므로
     * HiltWorkerFactory 를 여기서 수동으로 주입해 WorkManager 에 등록한다.
     * 덕분에 Worker 내부에서도 @Inject 를 통한 DI가 가능해진다.
     */
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(
                if (BuildConfig.DEBUG) android.util.Log.DEBUG
                else android.util.Log.ERROR
            )
            .build()
}
