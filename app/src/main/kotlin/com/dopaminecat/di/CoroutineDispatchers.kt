// filepath: app/src/main/kotlin/com/dopaminecat/di/CoroutineDispatchers.kt
package com.dopaminecat.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

// ── Qualifier 어노테이션 ────────────────────────────────────────────────────
// Dispatcher 를 DI로 주입하면 테스트에서 TestDispatcher로 교체할 수 있어
// 실시간 UsageStats 폴링 같은 타이밍 의존 코드도 결정론적으로 테스트 가능.

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher

// ── Module ─────────────────────────────────────────────────────────────────
@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @Provides
    @IoDispatcher
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @DefaultDispatcher
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @MainDispatcher
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main.immediate
}
