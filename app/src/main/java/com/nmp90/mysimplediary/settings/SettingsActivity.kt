package com.nmp90.mysimplediary.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceActivity
import android.preference.PreferenceManager
import com.nmp90.mysimplediary.R



class SettingsActivity : PreferenceActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    val PREF_ENABLE_NOTIFICATIONS = "pref_daily_notification"

    private lateinit var dailyNotificationController: DailyNotificationController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preferences);
        dailyNotificationController = DailyNotificationController(this)

        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == PREF_ENABLE_NOTIFICATIONS) {
            dailyNotificationController.setupNotifications(sharedPreferences)
        } else {
            dailyNotificationController.disableNotifications();
            dailyNotificationController.enableNotifications(sharedPreferences)
        }
    }

}
