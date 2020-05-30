/*
 * Created by Sławomir Przybylski
 * 26/05/20 09:39
 */

package io.slavisdev.zpi.di.ui.recipe

import dagger.Subcomponent
import io.slavisdev.zpi.ui.recpie.RecipeFragment

@RecipeFragmentModule.FragmentScope
@Subcomponent(
    modules = [
        RecipeFragmentModule::class
    ]
)
interface RecipeFragmentComponent {

    fun inject(fragment: RecipeFragment): RecipeFragment
}