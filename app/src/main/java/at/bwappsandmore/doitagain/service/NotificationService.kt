package at.bwappsandmore.doitagain.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat.*
import at.bwappsandmore.doitagain.R
import at.bwappsandmore.doitagain.ui.MainActivity

class NotificationService : IntentService("NotificationService") {

    companion object {
        const val CHANNEL_ID = "DoItAgainChannel"
        const val CHANNEL_NAME = "Notification"
        const val CHANNEL_DESC = "DoItAgain"
    }

    private lateinit var mNotification: Notification

    override fun onHandleIntent(intent: Intent?) {
        createChannel()

        var timestamp: Long = 0
        if (intent != null && intent.extras != null)
            timestamp = intent.extras!!.getLong("timestamp")

        if (timestamp > 0) {
            val notifyIntent = Intent(this, MainActivity::class.java).apply {
                putExtra("title", "DoItAgain Notification")
                putExtra("message", "DoItAgain Reminder")
                putExtra("notification", true)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }

            val pendingIntent = PendingIntent.getActivity(
                this.applicationContext,
                0,
                notifyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mNotification = Builder(this, CHANNEL_ID)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_settings_backup_restore_48dp)
                    .setLargeIcon(
                        BitmapFactory.decodeResource(
                            applicationContext.resources,
                            R.drawable.ic_settings_backup_restore_48dp
                        )
                    )
                    .setAutoCancel(true)
                    .setPriority(PRIORITY_LOW)
                    .setContentTitle("DoItAgain Notification")
                    .setStyle(BigTextStyle().bigText("DoItAgain Reminder"))
                    .setContentText("DoItAgain Reminder")
                    .build()
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(1000, mNotification)
        }

    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val context = this.applicationContext

            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                enableVibration(true)
                setShowBadge(true)
                enableLights(true)
                lightColor = Color.parseColor("#ff0099cc")
                description = CHANNEL_DESC
                lockscreenVisibility = Notification.VISIBILITY_SECRET
            }

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}