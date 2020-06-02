/*
 * Created by Sławomir Przybylski
 * 16/05/20 22:50
 */

package io.slavisdev.zpi.net

import io.slavisdev.zpi.settings.AppSettings
import okhttp3.Interceptor
import okhttp3.Response

private const val TOTAL_RETRIES = 3

class RepeaterInterceptor(
    private val appSettings: AppSettings
) : Interceptor {
    private var counter = 0

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val initialResponse = chain.proceed(originalRequest)

        return when {
            initialResponse.code > 400 -> {
                if (appSettings.getUserLoggedIn()) {
                    var response = initialResponse
                    while (counter < TOTAL_RETRIES && !initialResponse.isSuccessful) {
                        counter += 1
                        response = chain.proceed(originalRequest)
                    }
                    response
                } else {
                    initialResponse
                }
            }
            else -> {
                initialResponse
            }
        }
    }
}