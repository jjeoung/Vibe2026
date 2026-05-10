// filepath: app/src/test/kotlin/com/dopaminecat/domain/usecase/ClaimMidnightRewardUseCaseTest.kt
package com.dopaminecat.domain.usecase

import com.dopaminecat.domain.model.AppGoal
import com.dopaminecat.domain.model.Cat
import com.dopaminecat.domain.repository.CatRepository
import com.dopaminecat.domain.repository.CoinRepository
import com.dopaminecat.domain.repository.GoalRepository
import com.dopaminecat.domain.repository.UsageRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.Runs
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ClaimMidnightRewardUseCaseTest {

    private val goalRepository: GoalRepository = mockk()
    private val usageRepository: UsageRepository = mockk()
    private val catRepository: CatRepository = mockk()
    private val coinRepository: CoinRepository = mockk()

    private lateinit var useCase: ClaimMidnightRewardUseCase

    private val defaultCat = Cat(happiness = 70, trashCount = 3, lastUpdatedAt = 0L)
    private val instagram = AppGoal("com.instagram.android", "Instagram", dailyLimitMinutes = 60, isActive = true)
    private val tiktok = AppGoal("com.zhiliaoapp.musically", "TikTok", dailyLimitMinutes = 30, isActive = true)

    @Before
    fun setUp() {
        useCase = ClaimMidnightRewardUseCase(
            goalRepository, usageRepository, catRepository, coinRepository
        )
        every { catRepository.getCatStream() } returns flowOf(defaultCat)
        coEvery { catRepository.updateCatState(any(), any()) } just Runs
        coEvery { coinRepository.addReward(any(), any()) } just Runs
    }

    // ── 조기 반환 케이스 ─────────────────────────────────────────────────────

    @Test
    fun `returns AlreadyClaimed without touching DB when reward already given today`() = runTest {
        coEvery { coinRepository.hasReceivedRewardToday() } returns true

        val result = useCase()

        assertTrue(result is RewardResult.AlreadyClaimed)
        coVerify(exactly = 0) { coinRepository.addReward(any(), any()) }
        coVerify(exactly = 0) { catRepository.updateCatState(any(), any()) }
    }

    @Test
    fun `returns NoActiveGoals when no goal is registered`() = runTest {
        coEvery { coinRepository.hasReceivedRewardToday() } returns false
        every { goalRepository.getAllGoalsStream() } returns flowOf(emptyList())

        val result = useCase()

        assertTrue(result is RewardResult.NoActiveGoals)
        coVerify(exactly = 0) { coinRepository.addReward(any(), any()) }
    }

    @Test
    fun `inactive goals are excluded from evaluation`() = runTest {
        coEvery { coinRepository.hasReceivedRewardToday() } returns false
        every { goalRepository.getAllGoalsStream() } returns flowOf(
            listOf(instagram.copy(isActive = false))
        )

        val result = useCase()

        assertTrue(result is RewardResult.NoActiveGoals)
    }

    // ── 목표 초과 → 보상 없음 ────────────────────────────────────────────────

    @Test
    fun `returns GoalsExceeded and gives no coins when any goal exceeded`() = runTest {
        coEvery { coinRepository.hasReceivedRewardToday() } returns false
        every { goalRepository.getAllGoalsStream() } returns flowOf(listOf(instagram))
        coEvery {
            usageRepository.getTodayUsageMinutesForPackages(listOf("com.instagram.android"))
        } returns mapOf("com.instagram.android" to 90)  // 초과

        val result = useCase()

        assertTrue(result is RewardResult.GoalsExceeded)
        assertEquals(1, (result as RewardResult.GoalsExceeded).exceededGoals.size)
        coVerify(exactly = 0) { coinRepository.addReward(any(), any()) }
        coVerify(exactly = 0) { catRepository.updateCatState(any(), any()) }
    }

    @Test
    fun `GoalsExceeded contains correct exceeded details`() = runTest {
        coEvery { coinRepository.hasReceivedRewardToday() } returns false
        every { goalRepository.getAllGoalsStream() } returns flowOf(listOf(instagram))
        coEvery { usageRepository.getTodayUsageMinutesForPackages(any()) } returns
            mapOf("com.instagram.android" to 80)

        val result = useCase() as RewardResult.GoalsExceeded

        assertEquals(80, result.exceededGoals[0].usedMinutes)
        assertEquals(20, result.exceededGoals[0].exceededByMinutes)
    }

    // ── 목표 달성 → 보상 지급 ────────────────────────────────────────────────

    @Test
    fun `rewards correct coins for single met goal`() = runTest {
        coEvery { coinRepository.hasReceivedRewardToday() } returns false
        every { goalRepository.getAllGoalsStream() } returns flowOf(listOf(instagram))
        coEvery {
            usageRepository.getTodayUsageMinutesForPackages(listOf("com.instagram.android"))
        } returns mapOf("com.instagram.android" to 45)  // 45 < 60 → 달성

        val result = useCase() as RewardResult.Rewarded

        // 1 goal × 3 coins = 3
        assertEquals(3, result.coinsAwarded)
        coVerify { coinRepository.addReward(3, any()) }
    }

    @Test
    fun `rewards scale with number of met goals`() = runTest {
        coEvery { coinRepository.hasReceivedRewardToday() } returns false
        every { goalRepository.getAllGoalsStream() } returns flowOf(listOf(instagram, tiktok))
        coEvery { usageRepository.getTodayUsageMinutesForPackages(any()) } returns mapOf(
            "com.instagram.android"    to 30,  // 달성
            "com.zhiliaoapp.musically" to 20,  // 달성
        )

        val result = useCase() as RewardResult.Rewarded

        // 2 goals × 3 coins = 6
        assertEquals(6, result.coinsAwarded)
    }

    @Test
    fun `increases happiness and removes trash on reward`() = runTest {
        coEvery { coinRepository.hasReceivedRewardToday() } returns false
        every { goalRepository.getAllGoalsStream() } returns flowOf(listOf(instagram))
        coEvery { usageRepository.getTodayUsageMinutesForPackages(any()) } returns
            mapOf("com.instagram.android" to 45)

        val result = useCase() as RewardResult.Rewarded

        // happiness: 70 + 15 = 85
        assertEquals(15, result.happinessGained)
        // trash: 3 - 5 = -2 → coerced to 0
        assertEquals(3, result.trashRemoved)
        coVerify { catRepository.updateCatState(85, 0) }
    }

    @Test
    fun `happiness cannot exceed 100 on reward`() = runTest {
        val happyCat = Cat(happiness = 95, trashCount = 0, lastUpdatedAt = 0L)
        every { catRepository.getCatStream() } returns flowOf(happyCat)
        coEvery { coinRepository.hasReceivedRewardToday() } returns false
        every { goalRepository.getAllGoalsStream() } returns flowOf(listOf(instagram))
        coEvery { usageRepository.getTodayUsageMinutesForPackages(any()) } returns
            mapOf("com.instagram.android" to 45)

        useCase()

        // 95 + 15 = 110 → coerced to 100
        coVerify { catRepository.updateCatState(100, 0) }
    }
}
