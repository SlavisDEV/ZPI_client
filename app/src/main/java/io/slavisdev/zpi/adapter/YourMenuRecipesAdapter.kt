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
import io.slavisdev.zpi.ui.main.your_menu.YourMenuFragmentViewModel

class YourMenuRecipesAdapter(
    private val recipes: List<RecipeModel>,
    access: YourMenuFragmentViewModel
) : BaseLayoutAdapter(R.layout.item_your_menu_recipe, access) {

    override fun getObjForPosition(position: Int): Any {
        return recipes[position]
    }

    override fun getItemCount(): Int {
        return recipes.size
    }
}