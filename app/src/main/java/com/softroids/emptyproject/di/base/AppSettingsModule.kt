package com.softroids.emptyproject.di.base

import android.content.Context
import com.softroids.emptyproject.settings.AppSettings
import com.softroids.emptyproject.settings.AppSettingsImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
@Singleton
class AppSettingsModule {

    @Provides
    fun provideAppSettings(context: Context): AppSettings {
        return AppSettingsImpl(context)
    }
}