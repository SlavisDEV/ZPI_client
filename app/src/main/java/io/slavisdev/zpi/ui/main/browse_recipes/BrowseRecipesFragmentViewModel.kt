/*
 * Created by Sławomir Przybylski
 * 19/04/20 20:57
 */

package io.slavisdev.zpi.ui.main.browse_recipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import io.slavisdev.zpi.R
import io.slavisdev.zpi.RecipesQuery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BrowseRecipesFragmentViewModel @Inject constructor() {

    @Inject
    protected lateinit var apolloClient: ApolloClient

    private val _infoTitle = MutableLiveData<Int>()
    val infoTitle: LiveData<Int>
        get() = _infoTitle
    private val _infoMessage = MutableLiveData<Int>()
    val infoMessage: LiveData<Int>
        get() = _infoMessage
    private val _showInfoModal = MutableLiveData<Boolean>()
    val showInfoModal: LiveData<Boolean>
        get() = _showInfoModal

    private val _recipes = MutableLiveData<List<RecipesQuery.AllRecipe?>>()
    val recipes: LiveData<List<RecipesQuery.AllRecipe?>>
        get() = _recipes

    fun setup() {
        CoroutineScope(Dispatchers.IO).launch {
            val apolloResponse = fetchRecipes()

            if (apolloResponse == null) {
                _infoTitle.postValue(R.string.error)
                _infoMessage.postValue(R.string.api_error)
                _showInfoModal.postValue(true)
            } else {
                _recipes.postValue(apolloResponse.allRecipes())
            }
        }
    }

    private suspend fun fetchRecipes() : RecipesQuery.Data? {
        return withContext(Dispatchers.IO) {
            apolloClient.query(RecipesQuery()).toDeferred().await().data
        }
    }
}