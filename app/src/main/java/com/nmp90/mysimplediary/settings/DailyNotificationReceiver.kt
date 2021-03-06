package com.nmp90.mysimplediary.settings

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.nmp90.mysimplediary.MainActivity
import com.nmp90.mysimplediary.R




class DailyNotificationReceiver : BroadcastReceiver() {

    companion object {
        val CHANNEL_ID: String = "com.nmp90.mysimplediary.DAILY_NOTIFICATION"
    }

    override fun onReceive(context: Context, p1: Intent?) {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_diary_notification)
                .setContentTitle(context.getString(R.string.title_daily_notification))
                .setContentText(context.getString(R.string.text_daily_notification))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
        val notificationManager = NotificationManagerCompat.from(context)

        notificationManager.notify(0, mBuilder.build())
    }

}
