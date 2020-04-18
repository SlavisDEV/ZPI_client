package io.slavisdev.zpi.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class Navigation {

    fun startLoginActivity(activity: Activity) {
//        activity.startActivity(Intent(activity, LoginActivity::class.java))
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    fun showForgotPasswordFragment(supportFragmentManager: FragmentManager, containerId: Int) {
        supportFragmentManager
            .beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//            .replace(containerId, ForgotPasswordFragment.newInstance())
//            .addToBackStack(ForgotPasswordFragment.TAG)
            .commit()
    }

    fun startLoginActivityAfterLogout(context: Context) {
//        val intent = Intent(context, LoginActivity::class.java).apply {
//            flags =
//                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
//        }
//        context.startActivity(intent)
    }

    fun startMainActivity(activity: Activity) {

    }
}