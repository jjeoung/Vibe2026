// filepath: app/src/main/kotlin/com/dopaminecat/presentation/widget/CatWidgetEntryPoint.kt
package com.dopaminecat.presentation.widget

import com.dopaminecat.domain.repository.CatRepository
import com.dopaminecat.domain.repository.CoinRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Glance Widget 은 @AndroidEntryPoint 를 지원하지 않으므로
 * EntryPointAccessors 를 통해 Hilt SingletonComponent 에서 직접 의존성을 가져온다.
 */
@EntryPoint
@InstallIn(SingletonComponent::class)
interface CatWidgetEntryPoint {
    fun catRepository(): CatRepository
    fun coinRepository(): CoinRepository
}
