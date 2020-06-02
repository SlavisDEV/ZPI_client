/*
 * Created by Sławomir Przybylski
 * 19/04/20 21:11
 */

package io.slavisdev.zpi.di.ui.main.favourite_recipes

import dagger.Module
import dagger.Provides
import io.slavisdev.zpi.ui.main.favourite_recipes.FavouriteRecipesFragment
import io.slavisdev.zpi.ui.main.favourite_recipes.FavouriteRecipesFragmentViewAccess
import javax.inject.Scope

@Module
class FavouriteRecipesFragmentModule(private val fragment: FavouriteRecipesFragment) {

    @Scope
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    annotation class FragmentScope

    @Provides
    @FragmentScope
    fun providesFavouriteRecipesFragment(): FavouriteRecipesFragment {
        return fragment
    }

    @Provides
    @FragmentScope
    fun providesFavouriteRecipesFragmentViewAccess(fragment: FavouriteRecipesFragment): FavouriteRecipesFragmentViewAccess {
        return fragment
    }
}