package io.slavisdev.zpi.di.base

import io.slavisdev.zpi.di.ui.main.MainActivityComponent
import io.slavisdev.zpi.di.ui.main.MainActivityModule
import dagger.Component
import io.slavisdev.zpi.di.ui.auth.forget_password.ForgetPasswordFragmentComponent
import io.slavisdev.zpi.di.ui.auth.forget_password.ForgetPasswordFragmentModule
import io.slavisdev.zpi.di.ui.auth.login.LoginFragmentComponent
import io.slavisdev.zpi.di.ui.auth.login.LoginFragmentModule
import io.slavisdev.zpi.di.ui.auth.register.RegisterFragmentComponent
import io.slavisdev.zpi.di.ui.auth.register.RegisterFragmentModule
import io.slavisdev.zpi.di.ui.main.browse_recipes.BrowseRecipesFragmentComponent
import io.slavisdev.zpi.di.ui.main.browse_recipes.BrowseRecipesFragmentModule
import io.slavisdev.zpi.di.ui.main.favourite_recipes.FavouriteRecipesFragmentComponent
import io.slavisdev.zpi.di.ui.main.favourite_recipes.FavouriteRecipesFragmentModule
import io.slavisdev.zpi.di.ui.main.your_menu.YourMenuFragmentComponent
import io.slavisdev.zpi.di.ui.main.your_menu.YourMenuFragmentModule
import io.slavisdev.zpi.di.ui.splash_screen.SplashScreenActivityModule
import io.slavisdev.zpi.di.ui.splash_screen.SplashScreenActivityComponent
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppSettingsModule::class,
        AppModule::class,
        NavigationModule::class
    ]
)
interface AppComponent {

    fun plus(module: MainActivityModule): MainActivityComponent

    fun plus(module: SplashScreenActivityModule): SplashScreenActivityComponent

    fun plus(module: LoginFragmentModule): LoginFragmentComponent

    fun plus(module: ForgetPasswordFragmentModule): ForgetPasswordFragmentComponent

    fun plus(module: RegisterFragmentModule): RegisterFragmentComponent

    fun plus(module: YourMenuFragmentModule): YourMenuFragmentComponent

    fun plus(module: FavouriteRecipesFragmentModule): FavouriteRecipesFragmentComponent

    fun plus(module: BrowseRecipesFragmentModule): BrowseRecipesFragmentComponent
}