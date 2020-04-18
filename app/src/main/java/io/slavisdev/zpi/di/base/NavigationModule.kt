package io.slavisdev.zpi.di.base

import dagger.Module
import dagger.Provides
import io.slavisdev.zpi.navigation.Navigation
import javax.inject.Singleton

@Module
@Singleton
class NavigationModule {

    @Provides
    fun providesNavigation(): Navigation {
        return Navigation()
    }
}