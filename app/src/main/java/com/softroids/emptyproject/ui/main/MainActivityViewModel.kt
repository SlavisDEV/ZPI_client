package com.softroids.emptyproject.ui.main

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MainActivityViewModel @Inject constructor() {
    @Inject
    protected lateinit var viewAccess: MainActivityViewAccess
}