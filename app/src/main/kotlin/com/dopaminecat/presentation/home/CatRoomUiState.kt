// filepath: app/src/main/kotlin/com/dopaminecat/presentation/home/CatRoomUiState.kt
package com.dopaminecat.presentation.home

import com.dopaminecat.domain.model.AppGoal
import com.dopaminecat.domain.model.Cat

sealed class CatRoomUiState {
    data object Loading : CatRoomUiState()

    data class Success(
        val cat: Cat,
        val goals: List<AppGoalProgress>,
        val totalCoins: Int,
    ) : CatRoomUiState()
}

/**
 * 목표 + 오늘의 사용량을 합산한 UI 전용 모델.
 * [progress] 는 0f~1f 로 clamp 되어 프로그레스바에 바로 사용 가능.
 */
data class AppGoalProgress(
    val goal: AppGoal,
    val usedMinutes: Int,
) {
    val limitMinutes: Int  get() = goal.dailyLimitMinutes
    val progress: Float    get() = (usedMinutes.toFloat() / limitMinutes).coerceIn(0f, 1f)
    val isExceeded: Boolean get() = usedMinutes > limitMinutes
}
