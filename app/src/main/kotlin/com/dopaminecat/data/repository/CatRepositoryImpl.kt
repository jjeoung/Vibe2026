// filepath: app/src/main/kotlin/com/dopaminecat/data/repository/CatRepositoryImpl.kt
package com.dopaminecat.data.repository

import com.dopaminecat.data.local.dao.CatDao
import com.dopaminecat.data.local.entity.CatEntity
import com.dopaminecat.di.IoDispatcher
import com.dopaminecat.domain.model.Cat
import com.dopaminecat.domain.repository.CatRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatRepositoryImpl @Inject constructor(
    private val catDao: CatDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : CatRepository {

    override fun getCatStream(): Flow<Cat> =
        catDao.getCatStream().map { entity ->
            entity?.toDomain() ?: CatEntity.initial().toDomain()
        }

    override suspend fun updateCatState(happiness: Int, trashCount: Int) =
        withContext(ioDispatcher) {
            val clamped = happiness.coerceIn(0, 100)
            catDao.upsert(
                CatEntity(
                    happiness = clamped,
                    trashCount = trashCount.coerceAtLeast(0),
                    lastUpdatedAt = System.currentTimeMillis(),
                )
            )
        }

    override suspend fun initCatIfAbsent() = withContext(ioDispatcher) {
        if (catDao.getCat() == null) {
            catDao.upsert(CatEntity.initial())
        }
    }
}
