package com.dopaminecat.presentation.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dopaminecat.BuildConfig
import com.dopaminecat.domain.model.Cat
import com.dopaminecat.domain.model.CatMood
import kotlin.math.roundToInt

// ── 진입점 ──────────────────────────────────────────────────────────────────

@Composable
fun CatRoomScreen(
    uiState: CatRoomUiState,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onManualSettle: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        is CatRoomUiState.Loading -> LoadingContent(modifier.fillMaxSize())
        is CatRoomUiState.Success -> RoomContent(uiState, snackbarHostState, onManualSettle, modifier)
    }
}

// ── Loading ──────────────────────────────────────────────────────────────────

@Composable
private fun LoadingContent(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

// ── Success ──────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RoomContent(
    state: CatRoomUiState.Success,
    snackbarHostState: SnackbarHostState,
    onManualSettle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val cat = state.cat

    val bgColors by animateColorAsState(
        targetValue = roomBackgroundStart(cat.mood),
        animationSpec = tween(durationMillis = 1200),
        label = "bg_color",
    )

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            if (BuildConfig.DEBUG) {
                FloatingActionButton(
                    onClick = onManualSettle,
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer,
                ) {
                    Text("⚡", fontSize = 20.sp)
                }
            }
        },
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "🐟 ${state.totalCoins} 코인",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                ),
            )
        },
        containerColor = Color.Transparent,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(bgColors, roomBackgroundEnd(cat.mood))
                    )
                )
                .padding(paddingValues),
        ) {
            // 고양이 + 쓰레기 방
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.55f),
            ) {
                TrashLayer(trashCount = cat.trashCount)
                CatSection(
                    cat = cat,
                    modifier = Modifier.align(Alignment.Center),
                )
            }

            // 행복도 바
            HappinessBar(
                happiness = cat.happiness,
                mood = cat.mood,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
            )

            // 목표 진행 카드
            GoalProgressCard(
                goals = state.goals,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.45f)
                    .padding(horizontal = 16.dp, vertical = 4.dp),
            )
        }
    }
}

// ── Cat Section ───────────────────────────────────────────────────────────────

@Composable
private fun CatSection(cat: Cat, modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "cat_anim")
    val bounceY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = if (cat.mood == CatMood.HAPPY) -14f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(700, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "bounce",
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = cat.mood.toEmoji(),
            fontSize = 100.sp,
            modifier = Modifier.offset { IntOffset(0, bounceY.roundToInt()) },
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = cat.mood.toKoreanLabel(),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
        )
    }
}

// ── Trash Layer ───────────────────────────────────────────────────────────────

@Composable
private fun TrashLayer(trashCount: Int) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        // 1. 단위를 빼고 순수 숫자(Float)로 먼저 꺼냅니다.
        val wValue = maxWidth.value
        val hValue = maxHeight.value

        // 2. Java 랜덤 대신 더 안전한 Kotlin 랜덤을 사용합니다.
        val positions = remember(trashCount) {
            List(trashCount) { i ->
                val r = kotlin.random.Random(i * 31 + 7)
                Pair(r.nextFloat() * 0.80f + 0.05f, r.nextFloat() * 0.65f + 0.05f)
            }
        }

        // 3. 괄호 분해(Destructuring)를 쓰지 않고 직접 꺼내서 씁니다.
        positions.forEachIndexed { i, pos ->
            Text(
                text = TRASH_EMOJIS[i % TRASH_EMOJIS.size],
                fontSize = 22.sp,
                modifier = Modifier.offset(
                    x = (wValue * pos.first).dp,
                    y = (hValue * pos.second).dp
                )
            )
        }
    }
}

private val TRASH_EMOJIS = listOf("🗑️", "🍬", "📱", "🎮", "🍕", "💊", "🧃", "🍟", "🥤", "📦")

// ── Happiness Bar ─────────────────────────────────────────────────────────────

@Composable
private fun HappinessBar(
    happiness: Int,
    mood: CatMood,
    modifier: Modifier = Modifier,
) {
    val barColor by animateColorAsState(
        targetValue = when (mood) {
            CatMood.HAPPY     -> Color(0xFF4CAF50)
            CatMood.NEUTRAL   -> Color(0xFFFF9800)
            CatMood.SAD       -> Color(0xFFF44336)
            CatMood.DEPRESSED -> Color(0xFF9C27B0)
        },
        animationSpec = tween(800),
        label = "bar_color",
    )
    val animatedProgress by animateFloatAsState(
        targetValue = happiness / 100f,
        animationSpec = tween(1000),
        label = "happiness_progress",
    )

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text("❤️ 행복도", style = MaterialTheme.typography.labelMedium)
            Text("$happiness / 100", style = MaterialTheme.typography.labelMedium)
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            color = barColor,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
            strokeCap = StrokeCap.Round,
        )
    }
}

// ── Goal Progress ─────────────────────────────────────────────────────────────

@Composable
private fun GoalProgressCard(
    goals: List<AppGoalProgress>,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        if (goals.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "목표를 설정하면\n고양이가 함께 지켜봐요 🐱",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(items = goals, key = { it.goal.packageName }) { gp ->
                    GoalProgressRow(gp)
                }
            }
        }
    }
}

@Composable
private fun GoalProgressRow(gp: AppGoalProgress) {
    val animatedProgress by animateFloatAsState(
        targetValue = gp.progress,
        animationSpec = tween(600),
        label = "goal_${gp.goal.packageName}",
    )
    val barColor = if (gp.isExceeded) MaterialTheme.colorScheme.error
    else MaterialTheme.colorScheme.primary

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = gp.goal.appName,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = buildString {
                    append("${gp.usedMinutes}분 / ${gp.limitMinutes}분")
                    if (gp.isExceeded) append("  🚨")
                },
                style = MaterialTheme.typography.bodySmall,
                color = if (gp.isExceeded) MaterialTheme.colorScheme.error
                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp),
            color = barColor,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
            strokeCap = StrokeCap.Round,
        )
    }
}

// ── Helpers ───────────────────────────────────────────────────────────────────

private fun CatMood.toEmoji() = when (this) {
    CatMood.HAPPY     -> "😸"
    CatMood.NEUTRAL   -> "🐱"
    CatMood.SAD       -> "😿"
    CatMood.DEPRESSED -> "😾"
}

private fun CatMood.toKoreanLabel() = when (this) {
    CatMood.HAPPY     -> "아주 행복해요! 🎉"
    CatMood.NEUTRAL   -> "그럭저럭이에요"
    CatMood.SAD       -> "슬퍼하고 있어요..."
    CatMood.DEPRESSED -> "많이 힘들어요 😭"
}

private fun roomBackgroundStart(mood: CatMood) = when (mood) {
    CatMood.HAPPY     -> Color(0xFFFFF9C4)
    CatMood.NEUTRAL   -> Color(0xFFF5F5F5)
    CatMood.SAD       -> Color(0xFFE0E0E0)
    CatMood.DEPRESSED -> Color(0xFF9E9E9E)
}

private fun roomBackgroundEnd(mood: CatMood) = when (mood) {
    CatMood.HAPPY     -> Color(0xFFFFE0B2)
    CatMood.NEUTRAL   -> Color(0xFFEEEEEE)
    CatMood.SAD       -> Color(0xFFBDBDBD)
    CatMood.DEPRESSED -> Color(0xFF616161)
}