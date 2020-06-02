/*
 * Created by Sławomir Przybylski
 * 19/04/20 21:10
 */

package io.slavisdev.zpi.di.ui.main.your_menu

import dagger.Subcomponent
import io.slavisdev.zpi.ui.main.your_menu.YourMenuFragment

@YourMenuFragmentModule.FragmentScope
@Subcomponent(
    modules = [
        YourMenuFragmentModule::class
    ]
)
interface YourMenuFragmentComponent {

    fun inject(fragment: YourMenuFragment): YourMenuFragment
}