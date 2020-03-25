package com.softroids.emptyproject.di.base

import android.app.Application
import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
@Singleton
class AppModule(private val application: Application) {

    @Provides
    fun provideContext(): Context {
        return application.applicationContext
    }

    @Provides
    fun provideResources(context: Context): Resources {
        return context.resources
    }

}