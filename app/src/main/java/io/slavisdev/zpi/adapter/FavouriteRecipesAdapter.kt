/*
 * Created by Sławomir Przybylski
 * 21/05/20 21:04
 */

package io.slavisdev.zpi.adapter

import com.softroids.profracht.adapter.BaseLayoutAdapter
import io.slavisdev.zpi.R
import io.slavisdev.zpi.RecipesQuery
import io.slavisdev.zpi.data.RecipeModel
import io.slavisdev.zpi.ui.main.browse_recipes.BrowseRecipesFragmentViewModel
import io.slavisdev.zpi.ui.main.favourite_recipes.FavouriteRecipesFragmentViewModel

class FavouriteRecipesAdapter(
    private val recipes: List<RecipeModel>,
    access: FavouriteRecipesFragmentViewModel
) : BaseLayoutAdapter(R.layout.item_favourite_recipe, access) {

    override fun getObjForPosition(position: Int): Any {
        return recipes[position]
    }

    override fun getItemCount(): Int {
        return recipes.size
    }
}