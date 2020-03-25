package com.softroids.emptyproject.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.softroids.emptyproject.R
import com.softroids.emptyproject.databinding.ActivityMainBinding
import com.softroids.emptyproject.di.base.App
import com.softroids.emptyproject.di.ui.main.MainActivityModule
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainActivityViewAccess {

    private lateinit var binding: ActivityMainBinding

    @Inject
    protected lateinit var model: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
    }
}
