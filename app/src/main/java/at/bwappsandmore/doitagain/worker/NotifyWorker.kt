package at.bwappsandmore.doitagain.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import at.bwappsandmore.doitagain.R
import at.bwappsandmore.doitagain.enums.TransferNotifications

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
            .setContentText("last time done "+inputData.getString(TransferNotifications.DAYS.type)+ " days ago.")
            .setSmallIcon(R.drawable.ic_settings_backup_restore_48dp)
        notificationManager.notify(1, notification.build())
    }
}