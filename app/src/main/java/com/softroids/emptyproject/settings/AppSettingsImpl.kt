package com.softroids.emptyproject.settings

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

private const val SETTINGS_NAME = "app_settings"

class AppSettingsImpl(context: Context) : AppSettings {

    private val appContext = context.applicationContext


    private fun saveToSettings(key: String, value: String) {
        val settings: SharedPreferences =
            appContext.getSharedPreferences(SETTINGS_NAME, Activity.MODE_PRIVATE)
        val preferencesEditor = settings.edit()
        preferencesEditor.putString(key, value)
        preferencesEditor.apply()
    }

    private fun saveToSettings(key: String, value: Int) {
        val settings =
            appContext.getSharedPreferences(SETTINGS_NAME, Activity.MODE_PRIVATE)
        val preferencesEditor = settings.edit()
        preferencesEditor.putInt(key, value)
        preferencesEditor.apply()
    }

    private fun loadIntFromSettings(key: String): Int {
        val settings =
            appContext.getSharedPreferences(SETTINGS_NAME, Activity.MODE_PRIVATE)
        return settings.getInt(key, -1)
    }

    private fun loadFromSettings(key: String): String? {
        val settings =
            appContext.getSharedPreferences(SETTINGS_NAME, Activity.MODE_PRIVATE)
        return settings.getString(key, null)
    }
}