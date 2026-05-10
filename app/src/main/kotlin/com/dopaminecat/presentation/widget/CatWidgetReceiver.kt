// filepath: app/src/main/kotlin/com/dopaminecat/presentation/widget/CatWidgetReceiver.kt
package com.dopaminecat.presentation.widget
import androidx.glance.appwidget.updateAll
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * Glance 위젯의 진입점.
 * - [AppWidgetManager.ACTION_APPWIDGET_UPDATE] : 시스템 자동 갱신 (30분마다)
 * - [ACTION_UPDATE_WIDGET]                    : 서비스·Worker 에서 고양이 상태 변경 후 수동 갱신
 */
class CatWidgetReceiver : GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget = CatWidget()

    private val receiverScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        if (intent.action == ACTION_UPDATE_WIDGET) {
            // 서비스·Worker → 브로드캐스트 → 여기서 위젯 갱신
            receiverScope.launch {
                glanceAppWidget.updateAll(context)
            }
        }
    }

    companion object {
        const val ACTION_UPDATE_WIDGET = "com.dopaminecat.ACTION_UPDATE_WIDGET"
    }
}
