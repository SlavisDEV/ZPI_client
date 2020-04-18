package io.slavisdev.zpi.di.ui.main

import io.slavisdev.zpi.ui.main.MainActivity
import dagger.Subcomponent

@MainActivityModule.ActivityScope
@Subcomponent(
    modules = [MainActivityModule::class]
)
interface MainActivityComponent {

    fun inject(activity: MainActivity): MainActivity
}