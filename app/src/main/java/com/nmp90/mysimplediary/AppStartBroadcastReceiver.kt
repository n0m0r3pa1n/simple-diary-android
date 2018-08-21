package com.nmp90.mysimplediary

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import com.nmp90.mysimplediary.settings.DailyNotificationController

class AppStartBroadcastReceiver : BroadcastReceiver() {

    private lateinit var dailyNotificationController: DailyNotificationController

    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
            NotificationChannelCreator().createNotificationChannel(context)
            dailyNotificationController = DailyNotificationController(context)
            dailyNotificationController.setupNotifications(PreferenceManager.getDefaultSharedPreferences(context))
        }
    }

}