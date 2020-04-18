package io.slavisdev.zpi.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.databinding.DataBindingUtil
import io.slavisdev.zpi.R
import io.slavisdev.zpi.databinding.ActivityAuthBinding
import io.slavisdev.zpi.di.base.App
import io.slavisdev.zpi.di.ui.auth.AuthActivityModule
import io.slavisdev.zpi.navigation.Navigation
import javax.inject.Inject

class AuthActivity : AppCompatActivity(), AuthActivityViewAccess {

    @Inject
    protected lateinit var navigation: Navigation

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        App.get(this)
            .getAppComponent()
            .plus(AuthActivityModule(this))
            .inject(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth)
        binding.apply {
            lifecycleOwner = this@AuthActivity
            viewAccess = this@AuthActivity
        }
    }

    override fun showRegisterScreen() {
        TODO("Not yet implemented")
    }

    override fun showForgetPasswordScreen() {
        TODO("Not yet implemented")
    }
}
