// filepath: app/src/main/kotlin/com/dopaminecat/data/service/NotificationHelper.kt
package com.dopaminecat.data.service

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.dopaminecat.R
import com.dopaminecat.domain.model.ExceededGoal
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationHelper @Inject constructor(
    @ApplicationContext private val context: Context,
    private val notificationManager: NotificationManagerCompat,
) {
    companion object {
        const val CHANNEL_TRACKING  = "dopamine_cat_tracking"
        const val CHANNEL_ALERT     = "dopamine_cat_alert"
        const val CHANNEL_REWARD    = "dopamine_cat_reward"

        const val NOTIFICATION_ID_FOREGROUND = 1001
        const val NOTIFICATION_ID_ALERT      = 1002
        const val NOTIFICATION_ID_REWARD     = 1003
    }

    fun createNotificationChannels() {
        notificationManager.createNotificationChannelsCompat(
            listOf(
                NotificationChannelCompat.Builder(
                    CHANNEL_TRACKING,
                    NotificationManagerCompat.IMPORTANCE_LOW,
                )
                    .setName("스크린타임 추적")
                    .setDescription("고양이가 스크린타임을 모니터링하고 있어요")
                    .setShowBadge(false)
                    .build(),

                NotificationChannelCompat.Builder(
                    CHANNEL_ALERT,
                    NotificationManagerCompat.IMPORTANCE_HIGH,
                )
                    .setName("목표 초과 알림")
                    .setDescription("사용 목표를 초과했을 때 알려드려요")
                    .build(),

                NotificationChannelCompat.Builder(
                    CHANNEL_REWARD,
                    NotificationManagerCompat.IMPORTANCE_DEFAULT,
                )
                    .setName("보상 알림")
                    .setDescription("물고기 코인 보상을 받았을 때 알려드려요")
                    .build(),
            )
        )
    }

    /** UsageTrackingService 포그라운드 고정 알림 */
    fun buildForegroundNotification(): Notification =
        NotificationCompat.Builder(context, CHANNEL_TRACKING)
            // TODO: UI 단계에서 ic_cat_notification 벡터 드로어블로 교체
            .setSmallIcon(android.R.drawable.ic_popup_reminder)
            .setContentTitle("도파민 캣이 지켜보고 있어요 🐱")
            .setContentText("스크린타임을 모니터링 중입니다")
            .setOngoing(true)
            .setSilent(true)
            .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
            .build()

    /** 목표 초과 시 알림 — POST_NOTIFICATIONS 권한 필요 (API 33+) */
    fun showGoalExceededNotification(exceeded: List<ExceededGoal>) {
        if (!notificationManager.areNotificationsEnabled()) return

        val appNames = exceeded.joinToString(", ") { it.goal.appName }
        val notification = NotificationCompat.Builder(context, CHANNEL_ALERT)
            .setSmallIcon(android.R.drawable.ic_popup_reminder)
            .setContentTitle("고양이가 슬퍼하고 있어요 😿")
            .setContentText("$appNames 사용 목표를 초과했어요")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(exceeded.joinToString("\n") {
                        "· ${it.goal.appName}: +${it.exceededByMinutes}분 초과"
                    })
            )
            .setAutoCancel(true)
            .build()

        notificationManager.notify(NOTIFICATION_ID_ALERT, notification)
    }

    /** 자정 보상 지급 알림 */
    fun showRewardNotification(coinsAwarded: Int) {
        if (!notificationManager.areNotificationsEnabled()) return

        val notification = NotificationCompat.Builder(context, CHANNEL_REWARD)
            .setSmallIcon(android.R.drawable.ic_popup_reminder)
            .setContentTitle("🐟 물고기 코인 획득!")
            .setContentText("모든 목표를 달성해서 ${coinsAwarded}개의 코인을 받았어요!")
            .setAutoCancel(true)
            .build()

        notificationManager.notify(NOTIFICATION_ID_REWARD, notification)
    }
}
