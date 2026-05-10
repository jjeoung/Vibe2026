// filepath: app/src/main/kotlin/com/dopaminecat/data/repository/CoinRepositoryImpl.kt
package com.dopaminecat.data.repository

import com.dopaminecat.data.local.dao.CoinDao
import com.dopaminecat.data.local.entity.CoinTransactionEntity
import com.dopaminecat.di.IoDispatcher
import com.dopaminecat.domain.model.CoinTransaction
import com.dopaminecat.domain.repository.CoinRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoinRepositoryImpl @Inject constructor(
    private val coinDao: CoinDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : CoinRepository {

    override fun getTotalCoinsStream(): Flow<Int> = coinDao.getTotalCoinsStream()

    override fun getTransactionHistoryStream(): Flow<List<CoinTransaction>> =
        coinDao.getTransactionHistoryStream().map { list -> list.map(CoinTransactionEntity::toDomain) }

    override suspend fun addReward(amount: Int, reason: String) = withContext(ioDispatcher) {
        coinDao.insert(
            CoinTransactionEntity(
                amount = amount,
                reason = reason,
                createdAt = System.currentTimeMillis(),
            )
        )
    }

    override suspend fun spend(amount: Int, reason: String) = withContext(ioDispatcher) {
        coinDao.insert(
            CoinTransactionEntity(
                amount = -amount,
                reason = reason,
                createdAt = System.currentTimeMillis(),
            )
        )
    }

    override suspend fun hasReceivedRewardToday(): Boolean = withContext(ioDispatcher) {
        val startOfDay = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
        coinDao.hasPositiveTransactionSince(startOfDay)
    }
}
