package io.slavisdev.zpi.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import io.slavisdev.zpi.R
import io.slavisdev.zpi.ui.auth.AuthActivity
import io.slavisdev.zpi.ui.auth.login.LoginFragmentDirections
import kotlinx.android.synthetic.main.activity_auth.*

class Navigation {

    fun startAuthActivity(activity: Activity) {
        activity.startActivity(Intent(activity, AuthActivity::class.java))
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    fun showForgotPasswordFragment(activity: AuthActivity) {
        val action = LoginFragmentDirections.actionLoginFragmentToForgetPasswordFragment()
        Navigation.findNavController(activity, R.id.auth_hostFragment).navigate(action)
    }

    fun showRegisterFragment(activity: AuthActivity) {
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        Navigation.findNavController(activity, R.id.auth_hostFragment).navigate(action)
    }

    fun startAuthActivityAfterLogout(context: Context) {
        val intent = Intent(context, AuthActivity::class.java).apply {
            flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        context.startActivity(intent)
    }

    fun startMainActivity(activity: Activity) {

    }
}