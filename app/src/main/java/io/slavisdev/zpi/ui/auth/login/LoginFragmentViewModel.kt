/*
 * Created by Sławomir Przybylski
 * 18/04/20 20:56
 */

package io.slavisdev.zpi.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.slavisdev.zpi.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginFragmentViewModel @Inject constructor() {

    @Inject
    protected lateinit var viewAccess: LoginFragmentViewAccess

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
        CoroutineScope(Dispatchers.IO).launch {
            val result = loginUserApiRequest()
            when(result) {
                1 -> viewAccess.showMainActivity()
                2 -> {
                    _infoTitle.postValue(R.string.error)
                    _infoMessage.postValue(R.string.wrong_credentials)
                    _showInfoModal.postValue(true)
                }
                3 -> {
                    _infoTitle.postValue(R.string.error)
                    _infoMessage.postValue(R.string.api_error)
                    _showInfoModal.postValue(true)
                }
            }
        }
    }

    /**
     * return
     * 1 - success
     * 2 - wrong credentials
     * 3 - api error
     */
    private suspend fun loginUserApiRequest(): Int {
        return withContext(Dispatchers.IO) {
            try {
                // todo reset password
                1
            } catch (throwable: Throwable) {
                2
            }
        }
    }

    fun clearFields() {
        email.value = null
    }
}