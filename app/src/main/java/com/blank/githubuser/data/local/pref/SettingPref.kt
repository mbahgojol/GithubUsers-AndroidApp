package com.blank.githubuser.data.local.pref

import android.appwidget.AppWidgetManager
import android.content.Context
import com.blank.githubuser.ui.setting.SettingsFragment
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SettingPref @Inject constructor(@ApplicationContext private val context: Context) {
    private val pref = SettingsFragment.sharedPreferences(context)

    var appWidgetId: Int
        set(value) {
            pref.edit()
                .putInt(AppWidgetManager.EXTRA_APPWIDGET_ID, value)
                .apply()
        }
        get() = pref.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        )
}