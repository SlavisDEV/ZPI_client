/*
 * Created by Sławomir Przybylski
 * 19/04/20 20:59
 */

package io.slavisdev.zpi.ui.main.favourite_recipes

interface FavouriteRecipesFragmentViewAccess {

    fun showLoadingScreen()

    fun hideLoadingScreen()

    fun onRecipeClicked(recipeId: Int)
}