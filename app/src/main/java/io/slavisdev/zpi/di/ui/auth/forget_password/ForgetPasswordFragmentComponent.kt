/*
 * Created by Sławomir Przybylski
 * 18/04/20 21:01
 */

package io.slavisdev.zpi.di.ui.auth.forget_password

import dagger.Subcomponent
import io.slavisdev.zpi.di.ui.auth.login.LoginFragmentModule
import io.slavisdev.zpi.ui.auth.forget_password.ForgetPasswordFragment

@LoginFragmentModule.FragmentScope
@Subcomponent(
    modules = [
        ForgetPasswordFragmentModule::class
    ]
)
interface ForgetPasswordFragmentComponent {

    fun inject(fragment: ForgetPasswordFragment): ForgetPasswordFragment
}