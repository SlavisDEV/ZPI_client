/*
 * Created by Sławomir Przybylski
 * 18/04/20 21:59
 */

package io.slavisdev.zpi.di.ui.auth

import dagger.Subcomponent
import io.slavisdev.zpi.ui.auth.AuthActivity

@AuthActivityModule.ActivityScope
@Subcomponent(
    modules = [
        AuthActivityModule::class
    ]
)
interface AuthActivityComponent {

    fun inject(activity: AuthActivity): AuthActivity
}