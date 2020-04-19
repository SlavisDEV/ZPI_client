package io.slavisdev.zpi.ui.auth

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI
import io.slavisdev.zpi.R

class AuthActivity : AppCompatActivity(R.layout.activity_auth) {

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            findNavController(
                this,
                R.id.auth_hostFragment
            ),
            null
        )
    }
}
