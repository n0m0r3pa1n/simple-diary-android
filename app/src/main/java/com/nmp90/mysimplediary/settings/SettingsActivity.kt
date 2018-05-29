package com.nmp90.mysimplediary.settings

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceActivity
import android.preference.PreferenceManager
import com.nmp90.mysimplediary.R
import java.util.*



class SettingsActivity : PreferenceActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    val PREF_ENABLE_NOTIFICATIONS = "pref_daily_notification"
    val PREF_DAILY_NOTIFICATION_TIME = "pref_time_daily_notification"

    private lateinit var alarmMgr: AlarmManager
    private lateinit var pendingDailyIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preferences);

        alarmMgr = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val dailyNotificationReceiverIntent = Intent(this, DailyNotificationReceiver::class.java)
        pendingDailyIntent = PendingIntent.getBroadcast(this, 0, dailyNotificationReceiverIntent, 0)
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == PREF_ENABLE_NOTIFICATIONS) {
            setupNotifications(sharedPreferences)
        } else {
            disableNotifications();
            val notificationTime = sharedPreferences.getLong(PREF_DAILY_NOTIFICATION_TIME, Date().time)
            enableNotifications(notificationTime)
        }
    }

    private fun setupNotifications(sharedPreferences: SharedPreferences) {
        val notificationsEnabled = sharedPreferences.getBoolean(PREF_ENABLE_NOTIFICATIONS, false)
        if (notificationsEnabled) {
            val notificationTime = sharedPreferences.getLong(PREF_DAILY_NOTIFICATION_TIME, Date().time)
            enableNotifications(notificationTime)
        } else {
            disableNotifications()
        }
    }

    private fun enableNotifications(notificationTime: Long) {

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = notificationTime

        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY, pendingDailyIntent)
    }

    private fun disableNotifications() {
        alarmMgr.cancel(pendingDailyIntent)
    }
}
