// filepath: app/src/main/kotlin/com/dopaminecat/domain/model/Cat.kt
package com.dopaminecat.domain.model

/**
 * 고양이의 순수 도메인 모델. UI와 DB 모두 이 클래스를 기준으로 변환한다.
 *
 * @param happiness  0(최악) ~ 100(최고). 목표 초과 시 감소, 달성 시 증가.
 * @param trashCount 방 안의 쓰레기 이모지 수. happiness 가 낮을수록 쌓임.
 * @param lastUpdatedAt 마지막 상태 갱신 시각 (epoch millis)
 */
data class Cat(
    val happiness: Int,
    val trashCount: Int,
    val lastUpdatedAt: Long,
) {
    val mood: CatMood get() = when {
        happiness >= 80 -> CatMood.HAPPY
        happiness >= 50 -> CatMood.NEUTRAL
        happiness >= 20 -> CatMood.SAD
        else            -> CatMood.DEPRESSED
    }
}

enum class CatMood { HAPPY, NEUTRAL, SAD, DEPRESSED }
