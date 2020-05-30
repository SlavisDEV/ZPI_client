/*
 * Created by Sławomir Przybylski
 * 19/04/20 20:57
 */

package io.slavisdev.zpi.ui.main.browse_recipes

interface BrowseRecipesFragmentViewAccess {

    fun showLoadingScreen()

    fun hideLoadingScreen()

    fun onRecipeClicked(recipeId: Int)
}