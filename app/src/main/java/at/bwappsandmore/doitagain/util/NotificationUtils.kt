package at.bwappsandmore.doitagain.util

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import at.bwappsandmore.doitagain.receiver.AlarmReceiver
import org.joda.time.DateTime

object NotificationUtils {
    fun setNotification(activity: Activity) {
        val alarmManager = activity.getSystemService(Activity.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(activity.applicationContext, AlarmReceiver::class.java).apply {
            putExtra("reason", "notification")
            putExtra("timestamp", DateTime.now().millis)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            activity,
            0,
            alarmIntent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )
        alarmManager.set(AlarmManager.RTC_WAKEUP, 0, pendingIntent)
        Log.d("setNotification","alarmManager.set")
    }
}