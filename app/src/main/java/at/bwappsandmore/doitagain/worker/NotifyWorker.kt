package at.bwappsandmore.doitagain.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import at.bwappsandmore.doitagain.R
import at.bwappsandmore.doitagain.enums.TransferNotifications
import at.bwappsandmore.doitagain.ui.MainActivity

class NotifyWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    companion object {
        const val CHANNEL_ID = "DoItAgainChannel"
        const val CHANNEL_NAME = "Notification"
    }

    private val mContext = context

    override fun doWork(): Result {
        triggerNotification()
        return Result.success()
    }

    private fun triggerNotification() {
        val notificationManager =
            mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(mContext, CHANNEL_ID)
            .setContentTitle(inputData.getString(TransferNotifications.TITLE.type))
            .setContentText("last time done " + inputData.getString(TransferNotifications.DAYS.type) + " days ago.")
            .setSmallIcon(R.drawable.ic_settings_backup_restore_48dp)
            .setAutoCancel(true)

        val notificationIntent = Intent(mContext, MainActivity::class.java).apply {
            putExtra("EntityID", inputData.getInt("EntityID", 0))
        }

        val pendingIntent = PendingIntent.getActivity(
            mContext,
            1,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        notification.setContentIntent(pendingIntent)

        notificationManager.notify(1, notification.build())
    }
}