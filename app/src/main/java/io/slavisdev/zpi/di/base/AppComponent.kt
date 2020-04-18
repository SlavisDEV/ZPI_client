package io.slavisdev.zpi.di.base

import io.slavisdev.zpi.di.ui.main.MainActivityComponent
import io.slavisdev.zpi.di.ui.main.MainActivityModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApiModule::class,
        AppModule::class
    ]
)
interface AppComponent {

    fun plus(module: MainActivityModule): MainActivityComponent
}