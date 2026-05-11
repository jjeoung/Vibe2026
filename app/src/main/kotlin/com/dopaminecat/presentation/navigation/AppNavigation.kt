// filepath: app/src/main/kotlin/com/dopaminecat/presentation/navigation/AppNavigation.kt
package com.dopaminecat.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dopaminecat.presentation.home.CatRoomScreen
import com.dopaminecat.presentation.home.CatRoomViewModel
import com.dopaminecat.presentation.permission.UsagePermissionScreen

private sealed class Screen(val route: String) {
    data object Permission : Screen("permission")
    data object CatRoom    : Screen("cat_room")
}

@Composable
fun AppNavigation(
    hasPermission: Boolean,
    onRequestPermission: () -> Unit,
) {
    val navController = rememberNavController()
    val startDestination = if (hasPermission) Screen.CatRoom.route else Screen.Permission.route

    // 권한이 허용되면 CatRoom 으로 이동하고 Permission 화면을 백스택에서 제거
    LaunchedEffect(hasPermission) {
        if (hasPermission) {
            navController.navigate(Screen.CatRoom.route) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(Screen.Permission.route) {
            UsagePermissionScreen(onGrantPermission = onRequestPermission)
        }

        composable(Screen.CatRoom.route) {
            val viewModel: CatRoomViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val snackbarHostState = remember { androidx.compose.material3.SnackbarHostState() }

            androidx.compose.runtime.LaunchedEffect(viewModel) {
                viewModel.settlementResult.collect { result ->
                    val msg = when (result) {
                        is com.dopaminecat.domain.usecase.RewardResult.Rewarded -> "🎉 코인 +${result.coinsAwarded} · 행복도 +${result.happinessGained}"
                        com.dopaminecat.domain.usecase.RewardResult.AlreadyClaimed -> "오늘은 이미 보상을 받았어요"
                        com.dopaminecat.domain.usecase.RewardResult.NoActiveGoals -> "활성 목표가 없어요"
                        is com.dopaminecat.domain.usecase.RewardResult.GoalsExceeded -> "🚨 초과한 목표 ${result.exceededGoals.size}개 — 코인 미지급"
                    }
                    snackbarHostState.showSnackbar(msg)
                }
            }

            CatRoomScreen(
                uiState = uiState,
                snackbarHostState = snackbarHostState,
                onManualSettle = viewModel::triggerManualSettlement,
            )
        }
    }
}
