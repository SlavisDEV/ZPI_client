/*
 * Created by Sławomir Przybylski
 * 19/04/20 20:56
 */

package io.slavisdev.zpi.ui.main.your_menu

import io.slavisdev.zpi.data.RecipeModel

interface YourMenuFragmentViewAccess {

    fun showLoadingScreen()

    fun hideLoadingScreen()

    fun onRecipeClicked(recipeId: Int)
}