// filepath: app/src/main/kotlin/com/dopaminecat/domain/repository/CatRepository.kt
package com.dopaminecat.domain.repository

import com.dopaminecat.domain.model.Cat
import kotlinx.coroutines.flow.Flow

interface CatRepository {
    /** 현재 고양이 상태를 실시간 스트림으로 제공 */
    fun getCatStream(): Flow<Cat>

    /** 행복도 및 쓰레기 수 갱신 (UsageTracking Service 가 호출) */
    suspend fun updateCatState(happiness: Int, trashCount: Int)

    /** 최초 실행 시 고양이 초기화 */
    suspend fun initCatIfAbsent()
}
