package io.slavisdev.zpi.di.ui.main

import io.slavisdev.zpi.ui.main.MainActivity
import io.slavisdev.zpi.ui.main.MainActivityViewAccess
import dagger.Module
import dagger.Provides
import javax.inject.Scope

@Module
class MainActivityModule(private val activity: MainActivity) {

    @Scope
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    annotation class ActivityScope

    @Provides
    @ActivityScope
    fun providesMainActivity(): MainActivity {
        return activity
    }

    @Provides
    @ActivityScope
    fun providesMainActivityViewAccess(activity: MainActivity): MainActivityViewAccess {
        return activity
    }

}