/*
 * Created by Sławomir Przybylski
 * 26/05/20 10:26
 */

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

class IngredientsAdapter(
    private val ingredients: List<String>
) : BaseLayoutAdapter(R.layout.item_ingredient, null) {

    override fun getObjForPosition(position: Int): Any {
        return ingredients[position]
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }
}