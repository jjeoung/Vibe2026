// filepath: app/src/androidTest/kotlin/com/dopaminecat/di/TestDatabaseModule.kt
package com.dopaminecat.di

import android.content.Context
import androidx.room.Room
import com.dopaminecat.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

/**
 * 인스트루먼트 테스트에서 실제 DB 대신 인메모리 Room DB를 주입한다.
 * [TestInstallIn] 이 [DatabaseModule] 을 교체하므로 테스트 코드에 별도 설정 불필요.
 */
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class],
)
object TestDatabaseModule {

    @Provides
    @Singleton
    fun provideInMemoryDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase = Room.inMemoryDatabaseBuilder(
        context,
        AppDatabase::class.java,
    )
        .allowMainThreadQueries()  // 테스트 환경에서만 허용
        .build()

    @Provides
    fun provideCatDao(db: AppDatabase) = db.catDao()

    @Provides
    fun provideGoalDao(db: AppDatabase) = db.goalDao()

    @Provides
    fun provideCoinDao(db: AppDatabase) = db.coinDao()
}
