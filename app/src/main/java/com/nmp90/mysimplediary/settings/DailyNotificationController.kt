package com.nmp90.mysimplediary.settings

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import java.util.*

class DailyNotificationController(context: Context) {

    private val PREF_ENABLE_NOTIFICATIONS = "pref_daily_notification"
    private val PREF_DAILY_NOTIFICATION_TIME = "pref_time_daily_notification"

    private var alarmMgr: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private var pendingDailyIntent: PendingIntent


    init {
        val dailyNotificationReceiverIntent = Intent(context, DailyNotificationReceiver::class.java)
        pendingDailyIntent = PendingIntent.getBroadcast(context, 0, dailyNotificationReceiverIntent, 0)
    }


    fun setupNotifications(sharedPreferences: SharedPreferences) {
        val notificationsEnabled = sharedPreferences.getBoolean(PREF_ENABLE_NOTIFICATIONS, false)
        if (notificationsEnabled) {
            enableNotifications(sharedPreferences)
        } else {
            disableNotifications()
        }
    }

    fun enableNotifications(sharedPreferences: SharedPreferences) {
        val notificationTime = sharedPreferences.getLong(PREF_DAILY_NOTIFICATION_TIME, Date().time)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = notificationTime

        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY, pendingDailyIntent)
    }

    fun disableNotifications() {
        alarmMgr.cancel(pendingDailyIntent)
    }
}
