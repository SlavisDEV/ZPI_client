/*
 * Created by Sławomir Przybylski
 * 19/04/20 21:12
 */

package io.slavisdev.zpi.di.ui.main.browse_recipes

import dagger.Module
import dagger.Provides
import io.slavisdev.zpi.ui.main.browse_recipes.BrowseRecipesFragment
import io.slavisdev.zpi.ui.main.browse_recipes.BrowseRecipesFragmentViewAccess
import javax.inject.Scope

@Module
class BrowseRecipesFragmentModule(private val fragment: BrowseRecipesFragment) {

    @Scope
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    annotation class FragmentScope

    @Provides
    @FragmentScope
    fun providesBrowseRecipesFragment(): BrowseRecipesFragment {
        return fragment
    }

    @Provides
    @FragmentScope
    fun providesBrowseRecipesFragmentViewAccess(fragment: BrowseRecipesFragment): BrowseRecipesFragmentViewAccess {
        return fragment
    }
}