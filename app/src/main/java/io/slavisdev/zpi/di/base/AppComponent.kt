package io.slavisdev.zpi.di.base

import io.slavisdev.zpi.di.ui.main.MainActivityComponent
import io.slavisdev.zpi.di.ui.main.MainActivityModule
import dagger.Component
import io.slavisdev.zpi.di.ui.splash_screen.SplashScreenActivityModule
import io.slavisdev.zpi.di.ui.splash_screen.SplashScreenActivityComponent
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApiModule::class,
        AppModule::class,
        NavigationModule::class
    ]
)
interface AppComponent {

    fun plus(module: MainActivityModule): MainActivityComponent

    fun plus(module: SplashScreenActivityModule): SplashScreenActivityComponent
}