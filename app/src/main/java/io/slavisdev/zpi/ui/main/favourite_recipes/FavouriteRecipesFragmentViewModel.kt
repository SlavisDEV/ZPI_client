/*
 * Created by Sławomir Przybylski
 * 19/04/20 20:59
 */

package io.slavisdev.zpi.ui.main.favourite_recipes

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.toDeferred
import io.slavisdev.zpi.*
import io.slavisdev.zpi.data.RecipeModel
import io.slavisdev.zpi.settings.AppSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavouriteRecipesFragmentViewModel @Inject constructor() {

    @Inject
    protected lateinit var viewAccess: FavouriteRecipesFragmentViewAccess

    @Inject
    protected lateinit var apolloClient: ApolloClient

    @Inject
    protected lateinit var context: Context

    @Inject
    protected lateinit var appSettings: AppSettings

    private val _infoTitle = MutableLiveData<Int>()
    val infoTitle: LiveData<Int>
        get() = _infoTitle
    private val _infoMessage = MutableLiveData<Int>()
    val infoMessage: LiveData<Int>
        get() = _infoMessage
    private val _showInfoModal = MutableLiveData<Boolean>()
    val showInfoModal: LiveData<Boolean>
        get() = _showInfoModal

    private val _recipes = MutableLiveData<List<RecipeModel>>()
    val recipes: LiveData<List<RecipeModel>>
        get() = _recipes

    fun setup() {
        viewAccess.showLoadingScreen()
        CoroutineScope(Dispatchers.IO).launch {
            val apolloResponse = fetchFavouriteRecipes()

            if (apolloResponse == null) {
                _infoTitle.postValue(R.string.error)
                _infoMessage.postValue(R.string.api_error)
                _showInfoModal.postValue(true)
            } else {
                _recipes.postValue(apolloResponse.user()?.savedRecipes()?.map {
                    RecipeModel(
                        it.id().toInt(),
                        it.title(),
                        it.description() ?: context.getString(R.string.unknown),
                        it.preparationTime()?.toString() ?: context.getString(R.string.unknown),
                        it.servings() ?: context.getString(R.string.unknown),
                        it.image()?.url()
                    )
                })

                viewAccess.hideLoadingScreen()
            }
        }
    }

    private suspend fun fetchFavouriteRecipes() : FavouriteRecipesQuery.Data? {
        return withContext(Dispatchers.IO) {
            try {
                val query = FavouriteRecipesQuery.builder()
                    .userId(appSettings.getUserId())
                    .build()
                apolloClient.query(query).toDeferred().await().data
            } catch (throwable: Throwable) {
                null
            }
        }
    }

    fun removeFromFavourites(recipe: RecipeModel) {
        CoroutineScope(Dispatchers.IO).launch {
            if (sendRemoveFromFavouriteToApi(recipe.id)) {
                _infoTitle.postValue(R.string.success)
                _infoMessage.postValue(R.string.recipe_removed_from_favourites)
            } else {
                _infoTitle.postValue(R.string.error)
                _infoMessage.postValue(R.string.api_error)
            }
            _showInfoModal.postValue(true)
            val newRecipes = ArrayList(_recipes.value ?: listOf()).apply {
                remove(recipe)
            }
            _recipes.postValue(newRecipes)
        }
    }

    private suspend fun sendRemoveFromFavouriteToApi(recipeId: Int): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val mutation = RemoveFavouriteRecipeMutation.builder()
                    .recipeId(recipeId)
                    .userId(appSettings.getUserId())
                    .build()
                apolloClient.mutate(mutation).toDeferred().await().data
                    ?.removeUserRecipe()?.ok() == true
            } catch (throwable: Throwable) {
                false
            }
        }
    }

    fun onRecipeClicked(recipeId: Int) {
        viewAccess.onRecipeClicked(recipeId)
    }
}