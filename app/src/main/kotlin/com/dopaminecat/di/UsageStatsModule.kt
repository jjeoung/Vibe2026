// filepath: app/src/main/kotlin/com/dopaminecat/di/UsageStatsModule.kt
package com.dopaminecat.di

import android.app.usage.UsageStatsManager
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UsageStatsModule {

    /**
     * UsageStatsManager 는 시스템 서비스이므로 직접 생성 불가.
     * DI로 주입하면 테스트에서 Mock으로 교체 가능하여
     * 실제 권한 없이도 Repository / UseCase 단위 테스트가 가능해진다.
     */
    @Provides
    @Singleton
    fun provideUsageStatsManager(
        @ApplicationContext context: Context,
    ): UsageStatsManager =
        context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
}
