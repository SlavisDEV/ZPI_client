/*
 * Created by Sławomir Przybylski
 * 26/05/20 09:38
 */

package io.slavisdev.zpi.di.ui.recipe

import dagger.Module
import dagger.Provides
import io.slavisdev.zpi.ui.recpie.RecipeFragment
import io.slavisdev.zpi.ui.recpie.RecipeFragmentViewAccess
import javax.inject.Scope

@Module
class RecipeFragmentModule(private val fragment: RecipeFragment) {

    @Scope
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    annotation class FragmentScope

    @Provides
    @FragmentScope
    fun providesRecipeFragment(): RecipeFragment {
        return fragment
    }

    @Provides
    @FragmentScope
    fun providesRecipeFragmentViewAccess(fragment: RecipeFragment): RecipeFragmentViewAccess {
        return fragment
    }
}