package com.nmp90.mysimplediary.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceActivity
import android.preference.PreferenceManager
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import com.nmp90.mysimplediary.R
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.appcompat.widget.Toolbar


class SettingsActivity : PreferenceActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private val PREF_ENABLE_NOTIFICATIONS = "pref_daily_notification"

    private lateinit var dailyNotificationController: DailyNotificationController
    private var delegate: AppCompatDelegate? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        getDelegate().installViewFactory();
        getDelegate().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setSupportActionBar(findViewById(R.id.toolbar));
        getDelegate().supportActionBar?.setDisplayHomeAsUpEnabled(true)


        addPreferencesFromResource(R.xml.preferences);

        dailyNotificationController = DailyNotificationController(this)

        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        dailyNotificationController.setupNotifications(sharedPreferences)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        getDelegate().onPostCreate(savedInstanceState)
    }

    override fun setContentView(@LayoutRes layoutResID: Int) {
        getDelegate().setContentView(layoutResID)
    }

    override fun onPostResume() {
        super.onPostResume()
        getDelegate().onPostResume()
    }

    override fun onStop() {
        super.onStop()
        getDelegate().onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        getDelegate().onDestroy()
    }

    private fun setSupportActionBar(@Nullable toolbar: Toolbar) {
        getDelegate().setSupportActionBar(toolbar)
    }

    private fun getDelegate(): AppCompatDelegate {
        if (delegate == null) {
            delegate = AppCompatDelegate.create(this, null)
        }

        return delegate!!
    }

}
