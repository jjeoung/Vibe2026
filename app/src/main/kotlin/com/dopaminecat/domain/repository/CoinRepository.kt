// filepath: app/src/main/kotlin/com/dopaminecat/domain/repository/CoinRepository.kt
package com.dopaminecat.domain.repository

import com.dopaminecat.domain.model.CoinTransaction
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    /** 현재 보유 코인 합계를 실시간 스트림으로 제공 */
    fun getTotalCoinsStream(): Flow<Int>

    /** 자정 보상 Worker 가 호출 — 목표 달성 앱당 보상 지급 */
    suspend fun addReward(amount: Int, reason: String)

    /** 상점 구매 등 코인 차감 */
    suspend fun spend(amount: Int, reason: String)

    /** 오늘 이미 보상을 받았는지 확인 (중복 지급 방지) */
    suspend fun hasReceivedRewardToday(): Boolean

    fun getTransactionHistoryStream(): Flow<List<CoinTransaction>>
}
