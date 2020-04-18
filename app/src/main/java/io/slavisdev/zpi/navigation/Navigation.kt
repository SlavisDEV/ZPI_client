package io.slavisdev.zpi.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import io.slavisdev.zpi.ui.auth.AuthActivity

class Navigation {

    fun startAuthActivity(activity: Activity) {
        activity.startActivity(Intent(activity, AuthActivity::class.java))
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    fun showForgotPasswordFragment(supportFragmentManager: FragmentManager, containerId: Int) {

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