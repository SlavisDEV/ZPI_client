/*
 * Created by Sławomir Przybylski
 * 26/05/20 09:38
 */

package io.slavisdev.zpi.ui.recpie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.toDeferred
import io.slavisdev.zpi.R
import io.slavisdev.zpi.RecipeDetailsQuery
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

    private val _executeRequest = MutableLiveData<Boolean>()
    val executeRequest: LiveData<Boolean>
        get() = _executeRequest
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

            _title.postValue(recipe.recipe()?.title())
            _description.postValue(recipe.recipe()?.description())
            _imageUrl.postValue(recipe.recipe()?.imageSet()?.firstOrNull()?.url())
            _servings.postValue(recipe.recipe()?.servings())
            _preparationTime.postValue(recipe.recipe()?.preparationTime()?.toString())
            _ingredients.postValue(recipe.recipe()?.ingredients()?.map {
                it.name()
            })

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
}