package com.blank.githubuser.ui.favorite.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.blank.githubuser.R
import com.blank.githubuser.data.local.db.DbHelper
import com.blank.githubuser.data.local.pref.SettingPref
import com.blank.githubuser.data.model.User
import com.bumptech.glide.Glide
import io.reactivex.disposables.Disposable


class FavoriteFactory(
    private val context: Context,
    private val dbHelper: DbHelper,
    private val intent: Intent,
    private val pref: SettingPref
) : RemoteViewsService.RemoteViewsFactory {
    private val mWidgetItems = mutableListOf<User>()
    private lateinit var dispose: Disposable

    override fun onCreate() {
        val appWidgetId = intent.getIntExtra(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        )
        pref.appWidgetId = appWidgetId
        initData()
    }

    private fun initData() {
        mWidgetItems.clear()
        dispose = dbHelper.loadAll()
            .subscribe {
                mWidgetItems.addAll(it)
            }
    }

    override fun onDataSetChanged() {
        initData()
    }

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(context.packageName, R.layout.item_user_widget)
        try {
            val bitmap: Bitmap = Glide.with(context)
                .asBitmap()
                .load(mWidgetItems[position].avatarUrl)
                .submit(512, 512)
                .get()
            rv.setImageViewBitmap(R.id.imageView, bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val fillInIntent = Intent(context, javaClass)
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv
    }

    override fun getCount(): Int = mWidgetItems.size

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {
        dispose.dispose()
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long = 0

    override fun hasStableIds(): Boolean = false
}