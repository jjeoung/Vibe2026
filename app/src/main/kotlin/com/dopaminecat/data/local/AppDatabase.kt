// filepath: app/src/main/kotlin/com/dopaminecat/data/local/AppDatabase.kt
package com.dopaminecat.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dopaminecat.data.local.dao.CatDao
import com.dopaminecat.data.local.dao.CoinDao
import com.dopaminecat.data.local.dao.GoalDao
import com.dopaminecat.data.local.entity.CatEntity
import com.dopaminecat.data.local.entity.CoinTransactionEntity
import com.dopaminecat.data.local.entity.GoalEntity

@Database(
    entities = [
        CatEntity::class,
        GoalEntity::class,
        CoinTransactionEntity::class,
    ],
    version = 1,
    exportSchema = true,  // schemas/ 디렉토리에 JSON 저장 → 마이그레이션 추적
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun catDao(): CatDao
    abstract fun goalDao(): GoalDao
    abstract fun coinDao(): CoinDao
}
