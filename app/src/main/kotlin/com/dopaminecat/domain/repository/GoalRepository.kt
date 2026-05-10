// filepath: app/src/main/kotlin/com/dopaminecat/domain/repository/GoalRepository.kt
package com.dopaminecat.domain.repository

import com.dopaminecat.domain.model.AppGoal
import kotlinx.coroutines.flow.Flow

interface GoalRepository {
    /** 등록된 모든 목표를 실시간 스트림으로 제공 */
    fun getAllGoalsStream(): Flow<List<AppGoal>>

    /** 특정 패키지의 목표 조회 (초과 여부 판단에 사용) */
    suspend fun getGoalByPackage(packageName: String): AppGoal?

    suspend fun upsertGoal(goal: AppGoal)

    suspend fun deleteGoal(packageName: String)
}
