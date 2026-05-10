// filepath: app/src/main/kotlin/com/dopaminecat/domain/model/CoinTransaction.kt
package com.dopaminecat.domain.model

/**
 * 물고기 코인 거래 내역.
 * amount 가 양수이면 획득(EARN), 음수이면 지출(SPEND).
 */
data class CoinTransaction(
    val id: Long,
    val amount: Int,
    val reason: String,
    val createdAt: Long,
)
