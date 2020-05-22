/*
 * Created by Sławomir Przybylski
 * 18/04/20 20:56
 */

package io.slavisdev.zpi.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.slavisdev.zpi.R
import io.slavisdev.zpi.data.TokenModel
import io.slavisdev.zpi.net.Api
import io.slavisdev.zpi.settings.AppSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginFragmentViewModel @Inject constructor() {

    @Inject
    protected lateinit var viewAccess: LoginFragmentViewAccess

    @Inject
    protected lateinit var appSettings: AppSettings

    @Inject
    protected lateinit var api: Api

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _infoTitle = MutableLiveData<Int>()
    val infoTitle: LiveData<Int>
        get() = _infoTitle
    private val _infoMessage = MutableLiveData<Int>()
    val infoMessage: LiveData<Int>
        get() = _infoMessage
    private val _showInfoModal = MutableLiveData<Boolean>()
    val showInfoModal: LiveData<Boolean>
        get() = _showInfoModal

    fun login() {
        if (validateFields()) {
            CoroutineScope(Dispatchers.IO).launch {
                val result = loginUserApiRequest()
                if (result != null) {
                    appSettings.saveAccessToken(result.token)
                    appSettings.saveUserLoggedIn(true)
                    viewAccess.showMainActivity()
                } else {
                    _infoTitle.postValue(R.string.error)
                    _infoMessage.postValue(R.string.api_error)
                    _showInfoModal.postValue(true)
                }
            }
        } else {
            _infoTitle.value = R.string.error
            _infoMessage.value = R.string.wrong_credentials
            _showInfoModal.value = true
        }
    }

    private fun validateFields(): Boolean {
        return !(email.value.isNullOrBlank() || password.value.isNullOrBlank())
    }


    private suspend fun loginUserApiRequest(): TokenModel? {
        return withContext(Dispatchers.IO) {
            try {
                api.getToken(
                    email.value ?: "",
                    password.value ?: ""
                ).await()
            } catch (throwable: Throwable) {
                null
            }
        }
    }

    fun clearFields() {
        email.value = null
    }
}