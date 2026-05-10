// filepath: app/src/main/kotlin/com/dopaminecat/domain/repository/UsageRepository.kt
package com.dopaminecat.domain.repository

interface UsageRepository {
    /**
     * 오늘 00:00 ~ 현재까지 해당 앱의 사용 시간(분)을 반환.
     * UsageStatsManager 는 5분 단위로 갱신되므로 호출 간격도 이에 맞춘다.
     */
    suspend fun getTodayUsageMinutes(packageName: String): Int

    /** 목표가 설정된 모든 패키지의 오늘 사용량 일괄 조회 */
    suspend fun getTodayUsageMinutesForPackages(packageNames: List<String>): Map<String, Int>
}
