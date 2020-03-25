package com.softroids.emptyproject.di.base

import android.app.Application
import android.content.Context

class App : Application() {

    private lateinit var appComponent: AppComponent

    @Suppress("DEPRECATION")
    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    fun getAppComponent(): AppComponent {
        return appComponent
    }

    companion object {
        fun get(context: Context): App {
            return context.applicationContext as App
        }
    }
}