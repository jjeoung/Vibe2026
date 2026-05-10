// filepath: app/src/main/kotlin/com/dopaminecat/data/local/dao/CatDao.kt
package com.dopaminecat.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dopaminecat.data.local.entity.CatEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CatDao {

    @Query("SELECT * FROM cat WHERE id = 1")
    fun getCatStream(): Flow<CatEntity?>

    @Query("SELECT * FROM cat WHERE id = 1")
    suspend fun getCat(): CatEntity?

    // REPLACE: id=1 행이 이미 있으면 교체, 없으면 삽입
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(cat: CatEntity)
}
