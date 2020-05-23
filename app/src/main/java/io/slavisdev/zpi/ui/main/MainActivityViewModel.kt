package io.slavisdev.zpi.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel @Inject constructor() {
    @Inject
    protected lateinit var viewAccess: MainActivityViewAccess

    private val _executeRequest = MutableLiveData<Boolean>()
    val executeRequest: LiveData<Boolean>
        get() = _executeRequest

    fun showLoadingScreen() {
        _executeRequest.value = true
    }

    fun hideLoadingScreen() {
        CoroutineScope(Dispatchers.IO).launch {
            delay(1500)
            _executeRequest.postValue(false)
        }
    }
}