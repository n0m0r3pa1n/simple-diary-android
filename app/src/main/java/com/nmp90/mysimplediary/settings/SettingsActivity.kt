package com.nmp90.mysimplediary.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceActivity
import com.nmp90.mysimplediary.R

class SettingsActivity : PreferenceActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    val PREF_ENABLE_NOTIFICATIONS = "pref_daily_notification"
    val PREF_DAILY_NOTIFICATION_TIME = "pref_time_daily_notification"

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String) {
        if (key == PREF_ENABLE_NOTIFICATIONS) {

        } else if (key == PREF_DAILY_NOTIFICATION_TIME) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preferences);
    }



}
