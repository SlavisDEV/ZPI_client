/*
 * Created by Sławomir Przybylski
 * 18/04/20 21:02
 */

package io.slavisdev.zpi.di.ui.auth.login

import dagger.Subcomponent
import io.slavisdev.zpi.ui.auth.login.LoginFragment

@LoginFragmentModule.FragmentScope
@Subcomponent(
    modules = [
        LoginFragmentModule::class
    ]
)
interface LoginFragmentComponent {

    fun inject(fragment: LoginFragment): LoginFragment
}