package com.nmp90.mysimplediary

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.nmp90.mysimplediary.settings.DailyNotificationReceiver

class MySimpleDiaryApp : Application() {

    private val CHANNEL: String = DailyNotificationReceiver.CHANNEL_ID

    companion object {
        lateinit var instance: MySimpleDiaryApp
    }

    init{
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel(this)
    }

    private fun createNotificationChannel(context: Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.notification_channel_name)
            val description = getString(R.string.notification_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL, name, importance)
            channel.description = description
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

}