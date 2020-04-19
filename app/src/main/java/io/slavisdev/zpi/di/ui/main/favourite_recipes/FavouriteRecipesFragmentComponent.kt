/*
 * Created by Sławomir Przybylski
 * 19/04/20 21:12
 */

package io.slavisdev.zpi.di.ui.main.favourite_recipes

import dagger.Subcomponent
import io.slavisdev.zpi.ui.main.favourite_recipes.FavouriteRecipesFragment

@FavouriteRecipesFragmentModule.FragmentScope
@Subcomponent(
    modules = [
        FavouriteRecipesFragmentModule::class
    ]
)
interface FavouriteRecipesFragmentComponent {

    fun inject(fragment: FavouriteRecipesFragment): FavouriteRecipesFragment
}