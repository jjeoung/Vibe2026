// filepath: app/src/main/kotlin/com/dopaminecat/domain/model/ExceededGoal.kt
package com.dopaminecat.domain.model

/**
 * CheckGoalExceedUseCase 와 ClaimMidnightRewardUseCase 가 공유하는 도메인 결과 모델.
 * 초과된 앱 하나의 스냅샷을 표현한다.
 */
data class ExceededGoal(
    val goal: AppGoal,
    val usedMinutes: Int,
    val exceededByMinutes: Int,
)
