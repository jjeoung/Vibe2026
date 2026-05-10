// filepath: app/src/test/kotlin/com/dopaminecat/domain/usecase/CheckGoalExceedUseCaseTest.kt
package com.dopaminecat.domain.usecase

import com.dopaminecat.domain.model.AppGoal
import com.dopaminecat.domain.model.Cat
import com.dopaminecat.domain.repository.CatRepository
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
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CheckGoalExceedUseCaseTest {

    private val goalRepository: GoalRepository = mockk()
    private val usageRepository: UsageRepository = mockk()
    private val catRepository: CatRepository = mockk()

    private lateinit var useCase: CheckGoalExceedUseCase

    private val defaultCat = Cat(happiness = 80, trashCount = 0, lastUpdatedAt = 0L)
    private val instagram = AppGoal("com.instagram.android", "Instagram", dailyLimitMinutes = 60, isActive = true)
    private val tiktok = AppGoal("com.zhiliaoapp.musically", "TikTok", dailyLimitMinutes = 30, isActive = true)

    @Before
    fun setUp() {
        useCase = CheckGoalExceedUseCase(goalRepository, usageRepository, catRepository)
        every { catRepository.getCatStream() } returns flowOf(defaultCat)
        coEvery { catRepository.updateCatState(any(), any()) } just Runs
    }

    // ── 활성 목표 없음 ──────────────────────────────────────────────────────

    @Test
    fun `returns unchanged cat state when no active goals`() = runTest {
        every { goalRepository.getAllGoalsStream() } returns flowOf(emptyList())

        val result = useCase()

        assertFalse(result.hasExceeded)
        assertEquals(defaultCat.happiness, result.newHappiness)
        assertEquals(defaultCat.trashCount, result.newTrashCount)
        coVerify(exactly = 0) { catRepository.updateCatState(any(), any()) }
    }

    @Test
    fun `ignores inactive goals`() = runTest {
        val inactiveGoal = instagram.copy(isActive = false)
        every { goalRepository.getAllGoalsStream() } returns flowOf(listOf(inactiveGoal))

        val result = useCase()

        assertFalse(result.hasExceeded)
        coVerify(exactly = 0) { catRepository.updateCatState(any(), any()) }
    }

    // ── 목표 미초과 ──────────────────────────────────────────────────────────

    @Test
    fun `does not update cat state when goal is within limit`() = runTest {
        every { goalRepository.getAllGoalsStream() } returns flowOf(listOf(instagram))
        coEvery {
            usageRepository.getTodayUsageMinutesForPackages(listOf("com.instagram.android"))
        } returns mapOf("com.instagram.android" to 45)  // 45 < 60

        val result = useCase()

        assertFalse(result.hasExceeded)
        assertEquals(defaultCat.happiness, result.newHappiness)
        coVerify(exactly = 0) { catRepository.updateCatState(any(), any()) }
    }

    @Test
    fun `goal exactly at limit is not considered exceeded`() = runTest {
        every { goalRepository.getAllGoalsStream() } returns flowOf(listOf(instagram))
        coEvery {
            usageRepository.getTodayUsageMinutesForPackages(listOf("com.instagram.android"))
        } returns mapOf("com.instagram.android" to 60)  // 60 == 60 → not exceeded

        val result = useCase()

        assertFalse(result.hasExceeded)
    }

    // ── 목표 초과 ───────────────────────────────────────────────────────────

    @Test
    fun `decreases happiness and adds trash for single exceeded goal`() = runTest {
        every { goalRepository.getAllGoalsStream() } returns flowOf(listOf(instagram))
        coEvery {
            usageRepository.getTodayUsageMinutesForPackages(listOf("com.instagram.android"))
        } returns mapOf("com.instagram.android" to 90)  // 90 - 60 = 30분 초과

        val result = useCase()

        assertTrue(result.hasExceeded)
        assertEquals(1, result.exceededGoals.size)
        assertEquals(90, result.exceededGoals[0].usedMinutes)
        assertEquals(30, result.exceededGoals[0].exceededByMinutes)
        // 80 - (1 × 10) = 70
        assertEquals(70, result.newHappiness)
        // 0 + (1 × 1) = 1
        assertEquals(1, result.newTrashCount)
        coVerify { catRepository.updateCatState(70, 1) }
    }

    @Test
    fun `penalizes proportionally for multiple exceeded goals`() = runTest {
        every { goalRepository.getAllGoalsStream() } returns flowOf(listOf(instagram, tiktok))
        coEvery {
            usageRepository.getTodayUsageMinutesForPackages(
                listOf("com.instagram.android", "com.zhiliaoapp.musically")
            )
        } returns mapOf(
            "com.instagram.android"    to 90,  // 초과
            "com.zhiliaoapp.musically" to 60,  // 초과
        )

        val result = useCase()

        assertEquals(2, result.exceededGoals.size)
        // 80 - (2 × 10) = 60
        assertEquals(60, result.newHappiness)
        // 0 + (2 × 1) = 2
        assertEquals(2, result.newTrashCount)
    }

    // ── 경계값 ───────────────────────────────────────────────────────────────

    @Test
    fun `happiness cannot fall below 0`() = runTest {
        val sadCat = Cat(happiness = 5, trashCount = 0, lastUpdatedAt = 0L)
        every { catRepository.getCatStream() } returns flowOf(sadCat)
        every { goalRepository.getAllGoalsStream() } returns flowOf(listOf(instagram, tiktok))
        coEvery { usageRepository.getTodayUsageMinutesForPackages(any()) } returns mapOf(
            "com.instagram.android"    to 200,
            "com.zhiliaoapp.musically" to 200,
        )

        val result = useCase()

        // 5 - (2 × 10) = -15 → coerced to 0
        assertEquals(0, result.newHappiness)
    }

    @Test
    fun `trash cannot exceed MAX_TRASH_COUNT`() = runTest {
        val messyCat = Cat(happiness = 50, trashCount = 19, lastUpdatedAt = 0L)
        every { catRepository.getCatStream() } returns flowOf(messyCat)
        every { goalRepository.getAllGoalsStream() } returns flowOf(listOf(instagram, tiktok))
        coEvery { usageRepository.getTodayUsageMinutesForPackages(any()) } returns mapOf(
            "com.instagram.android"    to 200,
            "com.zhiliaoapp.musically" to 200,
        )

        val result = useCase()

        // 19 + 2 = 21 → coerced to MAX_TRASH_COUNT(20)
        assertEquals(CheckGoalExceedUseCase.MAX_TRASH_COUNT, result.newTrashCount)
    }
}
