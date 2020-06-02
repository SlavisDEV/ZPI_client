package io.slavisdev.zpi.di.ui.splash_screen

import dagger.Subcomponent
import io.slavisdev.zpi.ui.splash_screen.SplashScreenActivity

@SplashScreenActivityModule.ActivityScope
@Subcomponent(
    modules = [
        SplashScreenActivityModule::class
    ]
)
interface SplashScreenActivityComponent {

    fun inject(activity: SplashScreenActivity): SplashScreenActivity
}