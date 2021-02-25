package com.example.justcleantask.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.justcleantask.R
import com.example.justcleantask.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var  navController : NavController
    lateinit var viewBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav) as NavHostFragment
        navController = navHostFragment.navController

        viewBinding.navView.setupWithNavController(navController)

    }
}