package com.blank.githubuser.ui.favorite.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkBuilder
import com.blank.githubuser.R
import com.blank.githubuser.data.local.pref.SettingPref
import com.blank.githubuser.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteWidget : AppWidgetProvider() {
    companion object {
        const val REFRESh_TO_FAVORITE = "android.appwidget.action.APPWIDGET_UPDATE"
    }

    @Inject
    lateinit var pref: SettingPref

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        appWidgetIds.forEach { appWidgetId ->
            updateAppWidget(
                context,
                appWidgetManager,
                appWidgetId
            )
        }
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        val intent = Intent(context, FavoriteWidgetService::class.java)
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()

        val views = RemoteViews(context.packageName, R.layout.favorite_widget)
        views.setRemoteAdapter(R.id.stack_view, intent)
        views.setEmptyView(R.id.stack_view, R.id.empty_view)

        val favoriteIntent = NavDeepLinkBuilder(context)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.nav_github)
            .setDestination(R.id.favoriteFragment)
            .createPendingIntent()

        views.setPendingIntentTemplate(R.id.stack_view, favoriteIntent)

        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action != null) {
            when (intent.action) {
                REFRESh_TO_FAVORITE -> {
                    val appWidgetId = pref.appWidgetId
                    val manager = AppWidgetManager.getInstance(context)
                    manager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.stack_view)
                }
            }
        }
    }

    override fun onEnabled(context: Context) {}
    override fun onDisabled(context: Context) {}
}

fun Context.refresWidgetFavoriteItem() {
    Intent(FavoriteWidget.REFRESh_TO_FAVORITE).apply {
        sendBroadcast(this)
    }
}