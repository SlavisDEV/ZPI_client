/*
 * Created by Sławomir Przybylski
 * 18/04/20 21:00
 */

package io.slavisdev.zpi.di.ui.auth.login

import dagger.Module
import dagger.Provides
import io.slavisdev.zpi.ui.auth.login.LoginFragment
import io.slavisdev.zpi.ui.auth.login.LoginFragmentViewAccess
import javax.inject.Scope

@Module
class LoginFragmentModule(private val fragment: LoginFragment) {

    @Scope
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    annotation class FragmentScope

    @Provides
    @FragmentScope
    fun providesLoginFragment(): LoginFragment {
        return fragment
    }

    @Provides
    @FragmentScope
    fun providesLoginFragmentViewAccess(fragment: LoginFragment): LoginFragmentViewAccess {
        return fragment
    }
}