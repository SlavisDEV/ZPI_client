/*
 * Created by Sławomir Przybylski
 * 18/04/20 20:58
 */

package io.slavisdev.zpi.ui.auth.register

import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class RegisterFragmentViewModel @Inject constructor() {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    fun register() {

    }
}