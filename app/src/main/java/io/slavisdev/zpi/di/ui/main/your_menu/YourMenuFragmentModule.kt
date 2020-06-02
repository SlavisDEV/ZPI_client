/*
 * Created by Sławomir Przybylski
 * 19/04/20 21:06
 */

package io.slavisdev.zpi.di.ui.main.your_menu

import dagger.Module
import dagger.Provides
import io.slavisdev.zpi.ui.main.your_menu.YourMenuFragment
import io.slavisdev.zpi.ui.main.your_menu.YourMenuFragmentViewAccess
import javax.inject.Scope

@Module
class YourMenuFragmentModule(private val fragment: YourMenuFragment) {

    @Scope
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    annotation class FragmentScope

    @Provides
    @FragmentScope
    fun providesYourMenuFragment(): YourMenuFragment {
        return fragment
    }

    @Provides
    @FragmentScope
    fun providesYourFragmentViewAccess(fragment: YourMenuFragment): YourMenuFragmentViewAccess {
        return fragment
    }
}