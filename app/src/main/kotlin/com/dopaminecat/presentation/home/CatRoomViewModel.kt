// filepath: app/src/main/kotlin/com/dopaminecat/presentation/home/CatRoomViewModel.kt
package com.dopaminecat.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dopaminecat.di.IoDispatcher
import com.dopaminecat.domain.repository.CatRepository
import com.dopaminecat.domain.repository.CoinRepository
import com.dopaminecat.domain.repository.GoalRepository
import com.dopaminecat.domain.repository.UsageRepository
import com.dopaminecat.domain.usecase.ObserveCatStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatRoomViewModel @Inject constructor(
    private val catRepository: CatRepository,
    private val goalRepository: GoalRepository,
    private val coinRepository: CoinRepository,
    private val usageRepository: UsageRepository,
    private val observeCatStateUseCase: ObserveCatStateUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    /**
     * UsageStats 는 30초마다 UI 용으로 폴링.
     * 실제 고양이 상태 갱신은 서비스가 5분 주기로 담당하므로 여기서는 표시 전용.
     */
    private val usageMapFlow = flow {
        while (true) {
            runCatching {
                val activePackages = goalRepository.getAllGoalsStream().first()
                    .filter { it.isActive }
                    .map { it.packageName }
                if (activePackages.isNotEmpty()) {
                    usageRepository.getTodayUsageMinutesForPackages(activePackages)
                } else emptyMap()
            }.getOrDefault(emptyMap()).also { emit(it) }
            delay(UI_USAGE_REFRESH_MS)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyMap(),
    )

    val uiState = combine(
        observeCatStateUseCase(),
        goalRepository.getAllGoalsStream(),
        coinRepository.getTotalCoinsStream(),
        usageMapFlow,
    ) { cat, goals, coins, usageMap ->
        val progresses = goals
            .filter { it.isActive }
            .map { goal ->
                AppGoalProgress(
                    goal = goal,
                    usedMinutes = usageMap[goal.packageName] ?: 0,
                )
            }
        CatRoomUiState.Success(cat = cat, goals = progresses, totalCoins = coins)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = CatRoomUiState.Loading,
    )

    init {
        viewModelScope.launch(ioDispatcher) { catRepository.initCatIfAbsent() }
    }

    companion object {
        private const val UI_USAGE_REFRESH_MS = 30_000L
    }
}
