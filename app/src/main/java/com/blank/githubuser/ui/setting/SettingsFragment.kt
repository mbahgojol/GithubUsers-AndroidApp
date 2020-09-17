package com.blank.githubuser.ui.setting

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import androidx.core.content.ContextCompat
import androidx.preference.*
import androidx.transition.Fade
import com.blank.githubuser.R
import com.blank.githubuser.data.service.AlarmReceiver
import java.util.*

class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    companion object {
        fun sharedPreferences(context: Context): SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = Fade()
        exitTransition = Fade()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        val context = preferenceManager.context
        val screen = preferenceManager.createPreferenceScreen(context)

        val language = Preference(context)
            .apply {
                key = getString(R.string.language_key)
                title = getString(R.string.language_title)
                intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                summary = Locale.getDefault().displayLanguage
                icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_g_translate)
            }

        val languageCategory = PreferenceCategory(context)
            .apply {
                key = getString(R.string.language_header)
                title = getString(R.string.language_header)
            }
        screen.addPreference(languageCategory)
        languageCategory.addPreference(language)

        val reminder = SwitchPreferenceCompat(context)
            .apply {
                key = getString(R.string.reminder_key)
                summaryOff = getString(R.string.notification_summary_off)
                summaryOn = getString(R.string.notification_summary_on)
                title = getString(R.string.notification_title)
                icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_notifications)
            }

        val notificationCategory = PreferenceCategory(context)
            .apply {
                title = getString(R.string.notification_header)
            }
        screen.addPreference(notificationCategory)
        notificationCategory.addPreference(reminder)

        preferenceScreen = screen
    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        val reminderKey = getString(R.string.reminder_key)
        when (key) {
            reminderKey -> {
                val isReminder = sharedPreferences.getBoolean(key, false)
                val alarmReceiver = AlarmReceiver()
                if (isReminder) {
                    if (!alarmReceiver.isAlarmOn(requireContext())) alarmReceiver.setRepeatingAlarm(
                        requireContext()
                    )
                } else {
                    if (alarmReceiver.isAlarmOn(requireContext())) alarmReceiver.cancelAlarm(
                        requireContext()
                    )
                }
            }
        }
    }

}