/*
 * Created by Sławomir Przybylski
 * 18/04/20 20:54
 */

package io.slavisdev.zpi.ui.auth.forget_password

import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class ForgetPasswordFragmentViewModel @Inject constructor() {

    val email = MutableLiveData<String>()

    fun resetPassword() {

    }
}