/*
 * Created by Sławomir Przybylski
 * 18/04/20 20:54
 */

package io.slavisdev.zpi.ui.auth.forget_password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.slavisdev.zpi.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ForgetPasswordFragmentViewModel @Inject constructor() {

    val email = MutableLiveData<String>()

    private val _infoTitle = MutableLiveData<Int>()
    val infoTitle: LiveData<Int>
        get() = _infoTitle
    private val _infoMessage = MutableLiveData<Int>()
    val infoMessage: LiveData<Int>
        get() = _infoMessage
    private val _showInfoModal = MutableLiveData<Boolean>()
    val showInfoModal: LiveData<Boolean>
        get() = _showInfoModal

    fun resetPassword() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = resetPasswordApiRequest()
            if (result) {
                _infoTitle.postValue(R.string.success)
                _infoMessage.postValue(R.string.password_has_been_reset)
            } else {
                _infoTitle.postValue(R.string.error)
                _infoMessage.postValue(R.string.api_error)
            }
            _showInfoModal.postValue(true)
        }
    }

    private suspend fun resetPasswordApiRequest(): Boolean {
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
    }
}