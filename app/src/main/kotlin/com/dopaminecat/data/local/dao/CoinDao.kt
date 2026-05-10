// filepath: app/src/main/kotlin/com/dopaminecat/data/local/dao/CoinDao.kt
package com.dopaminecat.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dopaminecat.data.local.entity.CoinTransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {

    @Query("SELECT COALESCE(SUM(amount), 0) FROM coin_transaction")
    fun getTotalCoinsStream(): Flow<Int>

    @Query("SELECT * FROM coin_transaction ORDER BY createdAt DESC")
    fun getTransactionHistoryStream(): Flow<List<CoinTransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(tx: CoinTransactionEntity)

    /** 오늘 날짜(00:00 epoch)를 기준으로 EARN 내역이 있으면 true */
    @Query("""
        SELECT COUNT(*) > 0
        FROM coin_transaction
        WHERE amount > 0
          AND createdAt >= :todayStartEpoch
    """)
    suspend fun hasPositiveTransactionSince(todayStartEpoch: Long): Boolean
}
