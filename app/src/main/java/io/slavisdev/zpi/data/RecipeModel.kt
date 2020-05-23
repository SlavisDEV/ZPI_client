/*
 * Created by Sławomir Przybylski
 * 22/05/20 22:14
 */

package io.slavisdev.zpi.data

class RecipeModel(
    val id: Int,
    val title: String?,
    val description: String?,
    val preparationTime: String?,
    val servings: String?,
    val imageUrl: String?
)