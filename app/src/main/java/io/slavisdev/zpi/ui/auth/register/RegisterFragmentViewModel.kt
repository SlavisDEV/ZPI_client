/*
 * Created by Sławomir Przybylski
 * 18/04/20 20:58
 */

package io.slavisdev.zpi.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.toDeferred
import io.slavisdev.zpi.R
import io.slavisdev.zpi.RegisterMutation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterFragmentViewModel @Inject constructor() {

    @Inject
    protected lateinit var apolloClient: ApolloClient

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _infoTitle = MutableLiveData<Int>()
    val infoTitle: LiveData<Int>
        get() = _infoTitle
    private val _infoMessage = MutableLiveData<Int>()
    val infoMessage: LiveData<Int>
        get() = _infoMessage
    private val _closeScreen = MutableLiveData<Boolean>()
    val closeScreen: LiveData<Boolean>
        get() = _closeScreen
    private val _showInfoModal = MutableLiveData<Boolean>()
    val showInfoModal: LiveData<Boolean>
        get() = _showInfoModal

    fun register() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = registerUserApiRequest()
            if (result?.createUser()?.ok() == true) {
                _infoTitle.postValue(R.string.success)
                _infoMessage.postValue(R.string.account_has_been_created)
                _closeScreen.postValue(true)
            } else {
                _infoTitle.postValue(R.string.error)
                _infoMessage.postValue(R.string.api_error)
                _closeScreen.postValue(false)
            }
            _showInfoModal.postValue(true)
        }
    }

    private suspend fun registerUserApiRequest(): RegisterMutation.Data? {
        return withContext(Dispatchers.IO) {
            try {
                val registerMutation = RegisterMutation.builder()
                    .email(email.value)
                    .password(password.value)
                    .build()
                apolloClient.mutate(registerMutation).toDeferred().await().data
            } catch (throwable: Throwable) {
                null
            }
        }
    }

    fun clearFields() {
        email.value = null
        password.value = null
    }
}