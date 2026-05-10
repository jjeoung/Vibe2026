// filepath: app/src/main/kotlin/com/dopaminecat/data/local/entity/CoinTransactionEntity.kt
package com.dopaminecat.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dopaminecat.domain.model.CoinTransaction

@Entity(tableName = "coin_transaction")
data class CoinTransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val amount: Int,      // 양수: 획득 / 음수: 지출
    val reason: String,
    val createdAt: Long,
) {
    fun toDomain(): CoinTransaction = CoinTransaction(
        id = id,
        amount = amount,
        reason = reason,
        createdAt = createdAt,
    )

    companion object {
        fun fromDomain(tx: CoinTransaction): CoinTransactionEntity = CoinTransactionEntity(
            id = tx.id,
            amount = tx.amount,
            reason = tx.reason,
            createdAt = tx.createdAt,
        )
    }
}
