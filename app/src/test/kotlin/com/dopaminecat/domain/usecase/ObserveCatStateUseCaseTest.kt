// filepath: app/src/test/kotlin/com/dopaminecat/domain/usecase/ObserveCatStateUseCaseTest.kt
package com.dopaminecat.domain.usecase

import app.cash.turbine.test
import com.dopaminecat.domain.model.Cat
import com.dopaminecat.domain.repository.CatRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ObserveCatStateUseCaseTest {

    private val catRepository: CatRepository = mockk()
    private lateinit var useCase: ObserveCatStateUseCase

    @Before
    fun setUp() {
        useCase = ObserveCatStateUseCase(catRepository)
    }

    @Test
    fun `invoke returns flow from repository unchanged`() = runTest {
        val expected = Cat(happiness = 80, trashCount = 0, lastUpdatedAt = 1_000L)
        every { catRepository.getCatStream() } returns flowOf(expected)

        useCase().test {
            assertEquals(expected, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `invoke propagates multiple emissions in order`() = runTest {
        val first = Cat(happiness = 80, trashCount = 0, lastUpdatedAt = 1_000L)
        val second = Cat(happiness = 70, trashCount = 1, lastUpdatedAt = 2_000L)
        every { catRepository.getCatStream() } returns flowOf(first, second)

        useCase().test {
            assertEquals(first, awaitItem())
            assertEquals(second, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `CatMood reflects happiness correctly`() {
        assertEquals(com.dopaminecat.domain.model.CatMood.HAPPY,     Cat(80, 0, 0).mood)
        assertEquals(com.dopaminecat.domain.model.CatMood.NEUTRAL,   Cat(50, 0, 0).mood)
        assertEquals(com.dopaminecat.domain.model.CatMood.SAD,       Cat(20, 0, 0).mood)
        assertEquals(com.dopaminecat.domain.model.CatMood.DEPRESSED, Cat(10, 0, 0).mood)
    }
}
