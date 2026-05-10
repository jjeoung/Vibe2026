// filepath: app/src/main/kotlin/com/dopaminecat/data/repository/GoalRepositoryImpl.kt
package com.dopaminecat.data.repository

import com.dopaminecat.data.local.dao.GoalDao
import com.dopaminecat.data.local.entity.GoalEntity
import com.dopaminecat.di.IoDispatcher
import com.dopaminecat.domain.model.AppGoal
import com.dopaminecat.domain.repository.GoalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoalRepositoryImpl @Inject constructor(
    private val goalDao: GoalDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : GoalRepository {

    override fun getAllGoalsStream(): Flow<List<AppGoal>> =
        goalDao.getAllGoalsStream().map { list -> list.map(GoalEntity::toDomain) }

    override suspend fun getGoalByPackage(packageName: String): AppGoal? =
        withContext(ioDispatcher) {
            goalDao.getGoalByPackage(packageName)?.toDomain()
        }

    override suspend fun upsertGoal(goal: AppGoal) = withContext(ioDispatcher) {
        goalDao.upsert(GoalEntity.fromDomain(goal))
    }

    override suspend fun deleteGoal(packageName: String) = withContext(ioDispatcher) {
        goalDao.deleteByPackage(packageName)
    }
}
