package io.slavisdev.zpi.settings

interface AppSettings {

    fun saveAccessToken(accessToken: String)
    fun getAccessToken(): String?
    fun saveUserId(userId: Int)
    fun getUserId(): Int
    fun saveUserLoggedIn(isLogged: Boolean)
    fun getUserLoggedIn(): Boolean
}