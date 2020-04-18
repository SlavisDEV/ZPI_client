package io.slavisdev.zpi.di.base

import android.content.Context
import io.slavisdev.zpi.settings.AppSettings
import io.slavisdev.zpi.settings.AppSettingsImpl
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