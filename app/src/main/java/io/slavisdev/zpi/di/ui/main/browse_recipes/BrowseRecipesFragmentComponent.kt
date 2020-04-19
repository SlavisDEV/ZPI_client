/*
 * Created by Sławomir Przybylski
 * 19/04/20 21:14
 */

package io.slavisdev.zpi.di.ui.main.browse_recipes

import dagger.Subcomponent
import io.slavisdev.zpi.ui.main.browse_recipes.BrowseRecipesFragment

@BrowseRecipesFragmentModule.FragmentScope
@Subcomponent(
    modules = [
        BrowseRecipesFragmentModule::class
    ]
)
interface BrowseRecipesFragmentComponent {

    fun inject(fragment: BrowseRecipesFragment): BrowseRecipesFragment
}