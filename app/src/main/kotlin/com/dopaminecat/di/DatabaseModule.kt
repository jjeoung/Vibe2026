// filepath: app/src/main/kotlin/com/dopaminecat/di/DatabaseModule.kt
package com.dopaminecat.di

import android.content.Context
import androidx.room.Room
import com.dopaminecat.data.local.AppDatabase
import com.dopaminecat.data.local.dao.CatDao
import com.dopaminecat.data.local.dao.CoinDao
import com.dopaminecat.data.local.dao.GoalDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "dopamine_cat.db",
    )
        // 마이그레이션 전략 미정의 시 기존 DB를 파괴하고 재생성.
        // 프로덕션에서는 Migration 객체로 교체할 것.
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideCatDao(db: AppDatabase): CatDao = db.catDao()

    @Provides
    fun provideGoalDao(db: AppDatabase): GoalDao = db.goalDao()

    @Provides
    fun provideCoinDao(db: AppDatabase): CoinDao = db.coinDao()
}
