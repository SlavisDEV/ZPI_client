/*
 * Created by Sławomir Przybylski
 * 18/04/20 21:02
 */

package io.slavisdev.zpi.di.ui.auth.register

import dagger.Module
import dagger.Provides
import io.slavisdev.zpi.ui.auth.register.RegisterFragment
import io.slavisdev.zpi.ui.auth.register.RegisterFragmentViewAccess
import javax.inject.Scope

@Module
class RegisterFragmentModule(private val fragment: RegisterFragment) {

    @Scope
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    annotation class FragmentScope

    @Provides
    @FragmentScope
    fun providesRegisterFragment(): RegisterFragment {
        return fragment
    }

    @Provides
    @FragmentScope
    fun providesRegisterFragmentViewAccess(fragment: RegisterFragment): RegisterFragmentViewAccess {
        return fragment
    }
}