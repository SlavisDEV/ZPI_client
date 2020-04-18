/*
 * Created by Sławomir Przybylski
 * 18/04/20 21:57
 */

package io.slavisdev.zpi.di.ui.auth

import dagger.Module
import dagger.Provides
import io.slavisdev.zpi.ui.auth.AuthActivity
import io.slavisdev.zpi.ui.auth.AuthActivityViewAccess
import javax.inject.Scope

@Module
class AuthActivityModule(private val activity: AuthActivity) {

    @Scope
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    annotation class ActivityScope

    @Provides
    @ActivityScope
    fun providesAuthActivity(): AuthActivity {
        return activity
    }

    @Provides
    @ActivityScope
    fun providesAuthActivityViewAccess(activity: AuthActivity): AuthActivityViewAccess {
        return activity
    }
}