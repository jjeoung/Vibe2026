// filepath: app/src/main/kotlin/com/dopaminecat/data/local/entity/CatEntity.kt
package com.dopaminecat.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dopaminecat.domain.model.Cat

@Entity(tableName = "cat")
data class CatEntity(
    @PrimaryKey
    val id: Int = 1,           // 항상 단일 행
    val happiness: Int,
    val trashCount: Int,
    val lastUpdatedAt: Long,
) {
    fun toDomain(): Cat = Cat(
        happiness = happiness,
        trashCount = trashCount,
        lastUpdatedAt = lastUpdatedAt,
    )

    companion object {
        fun fromDomain(cat: Cat): CatEntity = CatEntity(
            happiness = cat.happiness,
            trashCount = cat.trashCount,
            lastUpdatedAt = cat.lastUpdatedAt,
        )

        fun initial(): CatEntity = CatEntity(
            happiness = 80,
            trashCount = 0,
            lastUpdatedAt = System.currentTimeMillis(),
        )
    }
}
