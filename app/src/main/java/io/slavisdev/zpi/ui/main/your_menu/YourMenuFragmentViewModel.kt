/*
 * Created by Sławomir Przybylski
 * 19/04/20 20:55
 */

package io.slavisdev.zpi.ui.main.your_menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.toDeferred
import io.slavisdev.zpi.AllIngredientsQuery
import io.slavisdev.zpi.AvoidIngredientMutation
import io.slavisdev.zpi.R
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

    fun setup() {
        viewAccess.showLoadingScreen()
        CoroutineScope(Dispatchers.IO).launch {
            val ingredients = getAllIngredients()

            if (ingredients == null) {
                _infoTitle.postValue(R.string.error)
                _infoMessage.postValue(R.string.api_error)
                _showInfoModal.postValue(true)
            }

            _ingredients.postValue(ingredients!!.allIngredients())
            viewAccess.hideLoadingScreen()
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

    fun addIngredient(ingredient: AllIngredientsQuery.AllIngredient) {
        CoroutineScope(Dispatchers.IO).launch {
            if (sendIngredientToApi(ingredient.id().toInt())) {
                _avoidIngredients.postValue((_avoidIngredients.value ?: listOf()).plus(ingredient.name()))
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
}