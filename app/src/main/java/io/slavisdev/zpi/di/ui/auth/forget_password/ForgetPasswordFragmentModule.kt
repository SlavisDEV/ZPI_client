/*
 * Created by Sławomir Przybylski
 * 18/04/20 20:58
 */

package io.slavisdev.zpi.di.ui.auth.forget_password

import dagger.Module
import dagger.Provides
import io.slavisdev.zpi.ui.auth.forget_password.ForgetPasswordFragment
import io.slavisdev.zpi.ui.auth.forget_password.ForgetPasswordFragmentViewAccess
import javax.inject.Scope

@Module
class ForgetPasswordFragmentModule(private val fragment: ForgetPasswordFragment) {

    @Scope
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    annotation class FragmentScope

    @Provides
    @FragmentScope
    fun providesForgetPasswordFragment(): ForgetPasswordFragment {
        return fragment
    }

    @Provides
    @FragmentScope
    fun providesForgetPasswordFragmentViewAccess(fragment: ForgetPasswordFragment): ForgetPasswordFragmentViewAccess {
        return fragment
    }
}