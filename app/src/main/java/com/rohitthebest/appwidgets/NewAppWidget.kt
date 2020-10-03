package com.rohitthebest.appwidgets

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import java.text.DateFormat
import java.util.*


/**
 * Implementation of App Widget functionality.
 */

private val mSharedPrefFile: String? = "com.rohitthebest.appwidgets"
private const val COUNT_KEY = "count"

class NewAppWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}


internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {

    val prefs = context.getSharedPreferences(
        mSharedPrefFile, 0
    )
    var count = prefs.getInt(COUNT_KEY + appWidgetId, 0)
    count++

    val dateString: String = DateFormat.getTimeInstance(DateFormat.SHORT).format(Date())

    val widgetText = context.getString(R.string.appwidget_text)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.new_app_widget)

    views.setTextViewText(appWidgetId, widgetText)

    views.setTextViewText(
        R.id.appwidget_update,
        context.resources.getString(R.string.date_count_format, count, dateString)
    )

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)

    val prefEditor = prefs.edit()
    prefEditor.putInt(COUNT_KEY + appWidgetId, count)
    prefEditor.apply()
}