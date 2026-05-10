// filepath: app/src/main/kotlin/com/dopaminecat/data/local/dao/GoalDao.kt
package com.dopaminecat.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dopaminecat.data.local.entity.GoalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {

    @Query("SELECT * FROM goal ORDER BY appName ASC")
    fun getAllGoalsStream(): Flow<List<GoalEntity>>

    @Query("SELECT * FROM goal WHERE packageName = :packageName")
    suspend fun getGoalByPackage(packageName: String): GoalEntity?

    @Query("SELECT packageName FROM goal WHERE isActive = 1")
    suspend fun getActivePackageNames(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(goal: GoalEntity)

    @Query("DELETE FROM goal WHERE packageName = :packageName")
    suspend fun deleteByPackage(packageName: String)
}
