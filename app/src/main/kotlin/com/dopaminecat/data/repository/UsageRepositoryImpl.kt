// filepath: app/src/main/kotlin/com/dopaminecat/data/repository/UsageRepositoryImpl.kt
package com.dopaminecat.data.repository

import android.app.usage.UsageStatsManager
import com.dopaminecat.di.IoDispatcher
import com.dopaminecat.domain.repository.UsageRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsageRepositoryImpl @Inject constructor(
    private val usageStatsManager: UsageStatsManager,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : UsageRepository {

    override suspend fun getTodayUsageMinutes(packageName: String): Int =
        withContext(ioDispatcher) {
            queryTodayUsage()[packageName] ?: 0
        }

    override suspend fun getTodayUsageMinutesForPackages(
        packageNames: List<String>,
    ): Map<String, Int> = withContext(ioDispatcher) {
        val all = queryTodayUsage()
        packageNames.associateWith { all[it] ?: 0 }
    }

    private fun queryTodayUsage(): Map<String, Int> {
        val now = System.currentTimeMillis()
        val startOfDay = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        return usageStatsManager
            .queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startOfDay, now)
            ?.associate { stats ->
                stats.packageName to (stats.totalTimeInForeground / 60_000).toInt()
            }
            ?: emptyMap()
    }
}
