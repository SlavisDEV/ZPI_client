/*
 * Created by Sławomir Przybylski
 * 18/04/20 21:03
 */

package io.slavisdev.zpi.di.ui.auth.register

import dagger.Subcomponent
import io.slavisdev.zpi.ui.auth.register.RegisterFragment

@RegisterFragmentModule.FragmentScope
@Subcomponent(
    modules = [
        RegisterFragmentModule::class
    ]
)
interface RegisterFragmentComponent {

    fun inject(fragment: RegisterFragment): RegisterFragment
}