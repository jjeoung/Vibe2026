// filepath: app/src/main/kotlin/com/dopaminecat/presentation/widget/CatWidget.kt
package com.dopaminecat.presentation.widget

import android.content.Context
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.wrapContentHeight
import androidx.glance.material3.ColorProviders
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.dopaminecat.domain.model.Cat
import com.dopaminecat.domain.model.CatMood
import com.dopaminecat.presentation.MainActivity
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.first

class CatWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val entryPoint = EntryPointAccessors.fromApplication<CatWidgetEntryPoint>(
            context.applicationContext,
        )
        val cat    = entryPoint.catRepository().getCatStream().first()
        val coins  = entryPoint.coinRepository().getTotalCoinsStream().first()

        provideContent {
            GlanceTheme {
                WidgetContent(
                    cat = cat,
                    totalCoins = coins,
                    modifier = GlanceModifier
                        .fillMaxSize()
                        .clickable(actionStartActivity<MainActivity>()),
                )
            }
        }
    }
}

@Composable
private fun WidgetContent(
    cat: Cat,
    totalCoins: Int,
    modifier: GlanceModifier = GlanceModifier,
) {
    Column(
        modifier = modifier.padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // 코인 표시 (상단 좌측)
        Row(
            modifier = GlanceModifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = "🐟 $totalCoins",
                style = TextStyle(
                    color = GlanceTheme.colors.onSurface,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                ),
            )
        }

        Spacer(modifier = GlanceModifier.height(6.dp))

        // 고양이 이모지
        Text(
            text = cat.mood.toEmoji(),
            style = TextStyle(fontSize = 44.sp),
        )

        Spacer(modifier = GlanceModifier.height(4.dp))

        // 행복도 텍스트 바 (Unicode 블록)
        val filled = (cat.happiness / 10).coerceIn(0, 10)
        Text(
            text = "${"█".repeat(filled)}${"░".repeat(10 - filled)}",
            style = TextStyle(
                color = GlanceTheme.colors.primary,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
            ),
        )

        Spacer(modifier = GlanceModifier.height(2.dp))

        // 기분 텍스트
        Text(
            text = cat.mood.toKoreanShort(),
            style = TextStyle(
                color = GlanceTheme.colors.onSurface,
                fontSize = 11.sp,
            ),
        )
    }
}

private fun CatMood.toEmoji() = when (this) {
    CatMood.HAPPY     -> "😸"
    CatMood.NEUTRAL   -> "🐱"
    CatMood.SAD       -> "😿"
    CatMood.DEPRESSED -> "😾"
}

private fun CatMood.toKoreanShort() = when (this) {
    CatMood.HAPPY     -> "행복해요 🎉"
    CatMood.NEUTRAL   -> "보통이에요"
    CatMood.SAD       -> "슬퍼요..."
    CatMood.DEPRESSED -> "많이 힘들어요"
}

// Glance 내부에서 @Composable 어노테이션을 직접 사용하기 위한 타입 별칭
private typealias Composable = androidx.compose.runtime.Composable
