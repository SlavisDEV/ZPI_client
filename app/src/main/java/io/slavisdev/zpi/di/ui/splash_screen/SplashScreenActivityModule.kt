package io.slavisdev.zpi.di.ui.splash_screen

import dagger.Module
import dagger.Provides
import io.slavisdev.zpi.ui.splash_screen.SplashScreenActivity
import javax.inject.Scope

@Module
class SplashScreenActivityModule(private val activity: SplashScreenActivity) {

    @Scope
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    annotation class ActivityScope

    @Provides
    @ActivityScope
    fun providesSplashScreenActivity(): SplashScreenActivity {
        return activity
    }
}