package com.softroids.emptyproject.di.ui.main

import com.softroids.emptyproject.ui.main.MainActivity
import dagger.Subcomponent

@MainActivityModule.ActivityScope
@Subcomponent(
    modules = [MainActivityModule::class]
)
interface MainActivityComponent {

    fun inject(activity: MainActivity): MainActivity
}