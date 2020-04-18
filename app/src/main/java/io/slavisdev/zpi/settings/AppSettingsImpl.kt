package io.slavisdev.zpi.settings

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

private const val SETTINGS_NAME = "app_settings"
private const val SETTINGS_ACCESS_TOKEN = "settings_access_token"
private const val SETTINGS_USER_ID = "settings_user_id"
private const val SETTINGS_USER_LOGGED_IN = "settings_user_logged_in"

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

    private fun saveToSettings(key: String, value: Boolean) {
        val settings =
            appContext.getSharedPreferences(SETTINGS_NAME, Activity.MODE_PRIVATE)
        val preferencesEditor = settings.edit()
        preferencesEditor.putBoolean(key, value)
        preferencesEditor.apply()
    }

    private fun loadIntFromSettings(key: String): Int {
        val settings =
            appContext.getSharedPreferences(SETTINGS_NAME, Activity.MODE_PRIVATE)
        return settings.getInt(key, -1)
    }

    private fun loadBooleanFromSettings(key: String): Boolean {
        val settings =
            appContext.getSharedPreferences(SETTINGS_NAME, Activity.MODE_PRIVATE)
        return settings.getBoolean(key, false)
    }

    private fun loadFromSettings(key: String): String? {
        val settings =
            appContext.getSharedPreferences(SETTINGS_NAME, Activity.MODE_PRIVATE)
        return settings.getString(key, null)
    }

    override fun saveAccessToken(accessToken: String) {
        saveToSettings(SETTINGS_ACCESS_TOKEN, accessToken)
    }

    override fun getAccessToken(): String? {
        return loadFromSettings(SETTINGS_ACCESS_TOKEN)
    }

    override fun saveUserId(userId: Int) {
        saveToSettings(SETTINGS_USER_ID, userId)
    }

    override fun getUserId(): Int {
        return loadIntFromSettings(SETTINGS_USER_ID)
    }

    override fun saveUserLoggedIn(isLogged: Boolean) {
        saveToSettings(SETTINGS_USER_LOGGED_IN, isLogged)
    }

    override fun getUserLoggedIn(): Boolean {
        return loadBooleanFromSettings(SETTINGS_USER_LOGGED_IN)
    }
}