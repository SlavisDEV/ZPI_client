package io.slavisdev.zpi.ui.splash_screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.slavisdev.zpi.di.base.App
import io.slavisdev.zpi.di.ui.splash_screen.SplashScreenActivityModule
import io.slavisdev.zpi.navigation.Navigation
import io.slavisdev.zpi.settings.AppSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SPLASH_SCREEN_TIME = 1500L

class SplashScreenActivity : AppCompatActivity() {

    @Inject
    protected lateinit var navigation: Navigation

    @Inject
    protected lateinit var appSettings: AppSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.get(this)
            .getAppComponent()
            .plus(SplashScreenActivityModule(this))
            .inject(this)

        closeSplashScreen()
    }

    private fun closeSplashScreen() {
        CoroutineScope(Dispatchers.Main).launch {
            delay(SPLASH_SCREEN_TIME)
            val isUserLoggedIn = appSettings.getUserLoggedIn()
            if (isUserLoggedIn) {
                navigation.startMainActivity(this@SplashScreenActivity)
            } else {
                navigation.startAuthActivity(this@SplashScreenActivity)
            }
            finish()
        }
    }
}
