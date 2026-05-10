// filepath: app/src/main/kotlin/com/dopaminecat/domain/model/AppGoal.kt
package com.dopaminecat.domain.model

/**
 * 사용자가 설정한 앱별 일일 사용 목표.
 *
 * @param packageName     추적 대상 앱의 패키지명 (PK)
 * @param appName         사용자에게 표시할 앱 이름
 * @param dailyLimitMinutes 하루 허용 사용 시간 (분)
 * @param isActive        토글로 일시 비활성화 가능
 */
data class AppGoal(
    val packageName: String,
    val appName: String,
    val dailyLimitMinutes: Int,
    val isActive: Boolean = true,
)
