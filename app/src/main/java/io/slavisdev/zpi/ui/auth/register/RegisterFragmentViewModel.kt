/*
 * Created by Sławomir Przybylski
 * 18/04/20 20:58
 */

package io.slavisdev.zpi.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.slavisdev.zpi.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterFragmentViewModel @Inject constructor() {

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

    fun register() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = registerUserApiRequest()
            if (result) {
                _infoTitle.postValue(R.string.success)
                _infoMessage.postValue(R.string.account_has_been_created)
            } else {
                _infoTitle.postValue(R.string.error)
                _infoMessage.postValue(R.string.api_error)
            }
            _showInfoModal.postValue(true)
        }
    }

    private suspend fun registerUserApiRequest(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                // todo reset password
                true
            } catch (throwable: Throwable) {
                false
            }
        }
    }

    fun clearFields() {
        email.value = null
        password.value = null
    }
}