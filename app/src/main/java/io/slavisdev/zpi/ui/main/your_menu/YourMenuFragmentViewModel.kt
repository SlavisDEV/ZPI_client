/*
 * Created by Sławomir Przybylski
 * 19/04/20 20:55
 */

package io.slavisdev.zpi.ui.main.your_menu

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

class YourMenuFragmentViewModel @Inject constructor() {

    @Inject
    protected lateinit var viewAccess: YourMenuFragmentViewAccess

    @Inject
    protected lateinit var apolloClient: ApolloClient

    @Inject
    protected lateinit var appSettings: AppSettings

    @Inject
    protected lateinit var context: Context

    private val _infoTitle = MutableLiveData<Int>()
    val infoTitle: LiveData<Int>
        get() = _infoTitle
    private val _infoMessage = MutableLiveData<Int>()
    val infoMessage: LiveData<Int>
        get() = _infoMessage
    private val _showInfoModal = MutableLiveData<Boolean>()
    val showInfoModal: LiveData<Boolean>
        get() = _showInfoModal

    private val _ingredients = MutableLiveData<List<AllIngredientsQuery.AllIngredient>>()
    val ingredients: LiveData<List<AllIngredientsQuery.AllIngredient>>
        get() = _ingredients
    private val _avoidIngredients = MutableLiveData<List<String>>()
    val avoidIngredients: LiveData<List<String>>
        get() = _avoidIngredients
    private val _recipes = MutableLiveData<List<RecipeModel>>()
    val recipes: LiveData<List<RecipeModel>>
        get() = _recipes

    private var selectedIngredients: ArrayList<UserIngredientsQuery.DislikedIngredient>? = null

    fun setup() {
        viewAccess.showLoadingScreen()
        CoroutineScope(Dispatchers.IO).launch {
            val ingredients = getAllIngredients()
            val selectedIngredients = getSelectedIngredients()
            this@YourMenuFragmentViewModel.selectedIngredients = ArrayList(selectedIngredients?.user()?.dislikedIngredients()?.toList() ?: listOf())

            if (ingredients == null) {
                _infoTitle.postValue(R.string.error)
                _infoMessage.postValue(R.string.api_error)
                _showInfoModal.postValue(true)
            }

            _recipes.postValue(prepareRecipes())

            _ingredients.postValue(ingredients!!.allIngredients())
            _avoidIngredients.postValue(selectedIngredients?.user()?.dislikedIngredients()?.map {
                it.name()
            })
            viewAccess.hideLoadingScreen()
        }
    }

    private suspend fun getSelectedIngredients(): UserIngredientsQuery.Data? {
        return withContext(Dispatchers.IO) {
            try {
                val query = UserIngredientsQuery.builder()
                    .userId(appSettings.getUserId())
                    .build()
                apolloClient.query(query).toDeferred().await().data
            } catch (throwable: Throwable) {
                null
            }
        }
    }

    private suspend fun getAllIngredients(): AllIngredientsQuery.Data? {
        return withContext(Dispatchers.IO) {
            try {
                apolloClient.query(AllIngredientsQuery.builder().build()).toDeferred().await().data
            } catch (throwable: Throwable) {
                null
            }
        }
    }

    private suspend fun prepareRecipes(): List<RecipeModel>? {
        val recipes = fetchRecipes(0)
        return recipes?.allRecipes()?.map {
            RecipeModel(
                it.id().toInt(),
                it.title(),
                it.description() ?: context.getString(R.string.unknown),
                it.preparationTime()?.toString() ?: context.getString(R.string.unknown),
                it.servings() ?: context.getString(R.string.unknown),
                it.image()?.url()
            )
        }
    }

    private suspend fun fetchRecipes(alreadyHave: Int) : RecipesByIngredientsQuery.Data? {
        return withContext(Dispatchers.IO) {
            try {
                val query = if (selectedIngredients.isNullOrEmpty()) {
                    RecipesByIngredientsQuery.builder()
                        .alreadyHave(alreadyHave)
                        .build()
                } else {
                    RecipesByIngredientsQuery.builder()
                        .alreadyHave(alreadyHave)
                        .avoidIngredients(selectedIngredients!!.map {
                            it.id().toInt()
                        })
                        .build()
                }
                apolloClient.query(query).toDeferred().await().data
            } catch (throwable: Throwable) {
                null
            }
        }
    }

    fun addIngredient(ingredient: AllIngredientsQuery.AllIngredient) {
        CoroutineScope(Dispatchers.IO).launch {
            if (sendIngredientToApi(ingredient.id().toInt())) {
                selectedIngredients!!.add(UserIngredientsQuery.DislikedIngredient("", ingredient.id(), ingredient.name()))
                _avoidIngredients.postValue((_avoidIngredients.value ?: listOf()).plus(ingredient.name()))
                _recipes.postValue(prepareRecipes())
            }
        }
    }

    private suspend fun sendIngredientToApi(ingredientId: Int): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val mutation = AvoidIngredientMutation
                    .builder()
                    .ingredientId(ingredientId)
                    .userId(appSettings.getUserId())
                    .build()
                apolloClient.mutate(mutation).toDeferred().await()
                    .data?.addDislikedIngredient()?.ok() == true
            } catch (throwable: Throwable) {
                false
            }
        }
    }

    fun removeIngredient(ingredientName: String) {
        val ingredient = selectedIngredients?.find {
            it.name() == ingredientName
        }

        if (ingredient != null) {
            CoroutineScope(Dispatchers.IO).launch {
                if (removeIngredientToApi(ingredient.id().toInt())) {
                    selectedIngredients!!.remove(ingredient)
                    _avoidIngredients.postValue(selectedIngredients!!.map {
                        it.name()
                    })
                    _recipes.postValue(prepareRecipes())
                }
            }
        }
    }

    private suspend fun removeIngredientToApi(ingredientId: Int): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val mutation = RemoveFromAvoidIngredientMutation
                    .builder()
                    .ingredientId(ingredientId)
                    .userId(appSettings.getUserId())
                    .build()
                apolloClient.mutate(mutation).toDeferred().await()
                    .data?.removeDislikedIngredient()?.ok() == true
            } catch (throwable: Throwable) {
                false
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

    fun onRecipeClicked(recipeId: Int) {
        viewAccess.onRecipeClicked(recipeId)
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
}