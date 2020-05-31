/*
 * Created by Sławomir Przybylski
 * 19/04/20 20:57
 */

package io.slavisdev.zpi.ui.main.browse_recipes

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import io.slavisdev.zpi.AddFavouriteRecipeMutation
import io.slavisdev.zpi.R
import io.slavisdev.zpi.RecipesQuery
import io.slavisdev.zpi.data.RecipeModel
import io.slavisdev.zpi.settings.AppSettings
import kotlinx.coroutines.*
import javax.inject.Inject

class BrowseRecipesFragmentViewModel @Inject constructor() {

    @Inject
    protected lateinit var viewAccess: BrowseRecipesFragmentViewAccess

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
            val apolloResponse = fetchRecipes(_recipes.value?.size ?: 0)

            if (apolloResponse == null) {
                _infoTitle.postValue(R.string.error)
                _infoMessage.postValue(R.string.api_error)
                _showInfoModal.postValue(true)
            } else {
                _recipes.postValue(apolloResponse.allRecipes()?.map {
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

    private suspend fun fetchRecipes(alreadyHave: Int) : RecipesQuery.Data? {
        return withContext(Dispatchers.IO) {
            try {
                val query = RecipesQuery.builder()
                    .alreadyHave(alreadyHave)
                    .build()
                apolloClient.query(query).toDeferred().await().data
            } catch (throwable: Throwable) {
                null
            }
        }
    }

    fun loadMoreRecipes() {
        CoroutineScope(Dispatchers.IO).launch {
            val apolloResponse = fetchRecipes(_recipes.value?.size ?: 0)

            val newRecipes = apolloResponse?.allRecipes()?.map {
                RecipeModel(
                    it.id().toInt(),
                    it.title(),
                    it.description() ?: context.getString(R.string.unknown),
                    it.preparationTime()?.toString() ?: context.getString(R.string.unknown),
                    it.servings() ?: context.getString(R.string.unknown),
                    it.image()?.url()
                )
            }

            if (!newRecipes.isNullOrEmpty()) {
                val recipes = (_recipes.value ?: listOf()).plus(newRecipes)

                _recipes.postValue(recipes)
            }
        }
    }

    fun markAsFavourite(recipe: RecipeModel) {
        CoroutineScope(Dispatchers.IO).launch {
            if (sendFavouriteRecipeToApi(recipe.id)) {
                _infoTitle.postValue(R.string.success)
                _infoMessage.postValue(R.string.recipe_mark_as_favourite)
            } else {
                _infoTitle.postValue(R.string.error)
                _infoMessage.postValue(R.string.api_error)
            }
            _showInfoModal.postValue(true)
        }
    }

    private suspend fun sendFavouriteRecipeToApi(recipeId: Int): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val mutation = AddFavouriteRecipeMutation.builder()
                    .recipeId(recipeId)
                    .userId(appSettings.getUserId())
                    .build()
                apolloClient.mutate(mutation).toDeferred().await().data
                    ?.saveUserRecipe()?.ok() == true
            } catch (throwable: Throwable) {
                false
            }
        }
    }

    fun onRecipeClicked(recipeId: Int) {
        viewAccess.onRecipeClicked(recipeId)
    }
}