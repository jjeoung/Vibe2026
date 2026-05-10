// filepath: app/src/main/kotlin/com/dopaminecat/di/RepositoryModule.kt
package com.dopaminecat.di

import com.dopaminecat.data.repository.CatRepositoryImpl
import com.dopaminecat.data.repository.CoinRepositoryImpl
import com.dopaminecat.data.repository.GoalRepositoryImpl
import com.dopaminecat.data.repository.UsageRepositoryImpl
import com.dopaminecat.domain.repository.CatRepository
import com.dopaminecat.domain.repository.CoinRepository
import com.dopaminecat.domain.repository.GoalRepository
import com.dopaminecat.domain.repository.UsageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// @Binds 는 abstract class 에서만 사용 가능
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds @Singleton
    abstract fun bindCatRepository(impl: CatRepositoryImpl): CatRepository

    @Binds @Singleton
    abstract fun bindGoalRepository(impl: GoalRepositoryImpl): GoalRepository

    @Binds @Singleton
    abstract fun bindUsageRepository(impl: UsageRepositoryImpl): UsageRepository

    @Binds @Singleton
    abstract fun bindCoinRepository(impl: CoinRepositoryImpl): CoinRepository
}
