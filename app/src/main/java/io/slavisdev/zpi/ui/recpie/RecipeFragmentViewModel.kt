/*
 * Created by Sławomir Przybylski
 * 26/05/20 09:38
 */

package io.slavisdev.zpi.ui.recpie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.toDeferred
import io.slavisdev.zpi.AddFavouriteRecipeMutation
import io.slavisdev.zpi.R
import io.slavisdev.zpi.RecipeDetailsQuery
import io.slavisdev.zpi.data.RecipeModel
import io.slavisdev.zpi.settings.AppSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeFragmentViewModel @Inject constructor() {

    @Inject
    protected lateinit var viewAccess: RecipeFragmentViewAccess

    @Inject
    protected lateinit var apolloClient: ApolloClient

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

    private val _title = MutableLiveData<String>()
    val title: LiveData<String>
        get() = _title
    private val _description = MutableLiveData<String>()
    val description: LiveData<String>
        get() = _description
    private val _imageUrl = MutableLiveData<String>()
    val imageUrl: LiveData<String>
        get() = _imageUrl
    private val _servings = MutableLiveData<String>()
    val servings: LiveData<String>
        get() = _servings
    private val _preparationTime = MutableLiveData<String>()
    val preparationTime: LiveData<String>
        get() = _preparationTime
    private val _ingredients = MutableLiveData<List<String>>()
    val ingredients: LiveData<List<String>>
        get() = _ingredients
    private val _methods = MutableLiveData<List<String>>()
    val methods: LiveData<List<String>>
        get() = _methods

    private var recipeId: Int = -1

    fun setup(recipeId: Int?) {

        if (recipeId == null) {
            _infoTitle.value = R.string.error
            _infoMessage.value = R.string.api_error
            _showInfoModal.value = true
            return
        }

        viewAccess.showLoadingScreen()
        CoroutineScope(Dispatchers.IO).launch {
            val recipe = getRecipeDetails(recipeId)
            if (recipe == null) {
                _infoTitle.value = R.string.error
                _infoMessage.value = R.string.api_error
                _showInfoModal.value = true
                return@launch
            }

            this@RecipeFragmentViewModel.recipeId = recipeId
            _title.postValue(recipe.recipe()?.title())
            _description.postValue(recipe.recipe()?.description())
            _imageUrl.postValue(recipe.recipe()?.image()?.url())
            _servings.postValue(recipe.recipe()?.servings())
            _preparationTime.postValue(recipe.recipe()?.preparationTime()?.toString())

            val ingredients = arrayListOf<String>()
            recipe.recipe()?.ingredientssegmentSet()?.forEach {
                ingredients.addAll(
                    it.mealingredientSet().map { ingredient ->
                        ingredient.ingredientAndAmountText()
                    }
                )
            }
            _ingredients.postValue(ingredients)

            val methods = recipe.recipe()?.preparationstepSet()?.map {
                it.stepText()
            }
            _methods.postValue(methods)

            viewAccess.hideLoadingScreen()
        }
    }

    private suspend fun getRecipeDetails(recipeId: Int): RecipeDetailsQuery.Data? {
        return withContext(Dispatchers.IO) {
            try {
                val query = RecipeDetailsQuery
                    .builder()
                    .id(recipeId)
                    .build()
                apolloClient.query(query).toDeferred().await().data
            } catch (throwable: Throwable) {
                null
            }
        }
    }

    fun markAsFavourite() {
        CoroutineScope(Dispatchers.IO).launch {
            if (sendFavouriteRecipeToApi(recipeId)) {
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