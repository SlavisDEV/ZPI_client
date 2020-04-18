package io.slavisdev.zpi.ui.main

import javax.inject.Inject

class MainActivityViewModel @Inject constructor() {
    @Inject
    protected lateinit var viewAccess: MainActivityViewAccess
}