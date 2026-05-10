// filepath: app/src/main/kotlin/com/dopaminecat/data/local/entity/GoalEntity.kt
package com.dopaminecat.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dopaminecat.domain.model.AppGoal

@Entity(tableName = "goal")
data class GoalEntity(
    @PrimaryKey
    val packageName: String,
    val appName: String,
    val dailyLimitMinutes: Int,
    val isActive: Boolean,
) {
    fun toDomain(): AppGoal = AppGoal(
        packageName = packageName,
        appName = appName,
        dailyLimitMinutes = dailyLimitMinutes,
        isActive = isActive,
    )

    companion object {
        fun fromDomain(goal: AppGoal): GoalEntity = GoalEntity(
            packageName = goal.packageName,
            appName = goal.appName,
            dailyLimitMinutes = goal.dailyLimitMinutes,
            isActive = goal.isActive,
        )
    }
}
