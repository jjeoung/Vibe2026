// filepath: app/src/main/kotlin/com/dopaminecat/di/DataSourceModule.kt
package com.dopaminecat.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Top-level 프로퍼티로 선언해야 DataStore 싱글톤 보장 (공식 권장 방식)
private val Context.dataStore: DataStore<Preferences>
        by preferencesDataStore(name = "dopamine_cat_prefs")

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    /**
     * 저장 항목 예시:
     *  - ONBOARDING_COMPLETED  : 온보딩 완료 여부
     *  - USAGE_PERMISSION_ASKED: PACKAGE_USAGE_STATS 안내 팝업 노출 여부
     *  - LAST_REWARD_DATE      : 마지막 물고기 코인 지급일 (중복 지급 방지)
     */
    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> = context.dataStore
}
