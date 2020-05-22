package io.slavisdev.zpi.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import io.slavisdev.zpi.R
import io.slavisdev.zpi.databinding.ActivityMainBinding
import io.slavisdev.zpi.di.base.App
import io.slavisdev.zpi.di.ui.main.MainActivityModule
import javax.inject.Inject

class MainActivity : AppCompatActivity(),
    MainActivityViewAccess {

    private lateinit var binding: ActivityMainBinding

    @Inject
    protected lateinit var model: MainActivityViewModel

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.get(this)
            .getAppComponent()
            .plus(MainActivityModule(this))
            .inject(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            lifecycleOwner = this@MainActivity
            model = this@MainActivity.model
            viewAccess = this@MainActivity
        }

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        navController = Navigation.findNavController(this, R.id.main_hostFragment)
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            navController.navigate(it.itemId)
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            navController,
            null
        )
    }
}
