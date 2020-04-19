/*
 * Created by Sławomir Przybylski
 * 18/04/20 20:56
 */

package io.slavisdev.zpi.ui.auth.login

import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class LoginFragmentViewModel @Inject constructor() {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    fun login() {

    }
}