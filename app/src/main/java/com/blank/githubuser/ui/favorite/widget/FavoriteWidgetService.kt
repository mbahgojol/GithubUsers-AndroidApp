package com.blank.githubuser.ui.favorite.widget

import android.content.Intent
import android.widget.RemoteViewsService
import com.blank.githubuser.data.local.db.DbHelper
import com.blank.githubuser.data.local.pref.SettingPref
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteWidgetService : RemoteViewsService() {
    @Inject
    lateinit var dbHelper: DbHelper

    @Inject
    lateinit var settingPref: SettingPref

    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory =
        FavoriteFactory(this, dbHelper, intent, settingPref)
}