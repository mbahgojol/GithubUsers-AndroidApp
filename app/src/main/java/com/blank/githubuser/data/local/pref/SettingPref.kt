package com.blank.githubuser.data.local.pref

import android.content.Context
import com.blank.githubuser.R
import com.blank.githubuser.ui.setting.SettingsFragment
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SettingPref @Inject constructor(@ApplicationContext private val context: Context) {
    private val pref = SettingsFragment.sharedPreferences(context)

    fun getDailyReminder(): Boolean =
        pref.getBoolean(context.resources.getString(R.string.reminder_key), false)
}