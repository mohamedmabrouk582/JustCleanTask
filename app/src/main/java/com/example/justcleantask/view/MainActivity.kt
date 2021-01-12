package com.example.justcleantask.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
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
        navController= findNavController(R.id.main_nav)
        viewBinding.navView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                 R.id.post_item ->{
                     navController.popBackStack()
                     true
                 }
                R.id.fav_item ->{
                    val id = navController.currentDestination?.id
                    if (id == R.id.postDetailsFragment){
                        navController.navigate(R.id.action_postDetailsFragment_to_favoritesFragment)
                    } else if (id==R.id.favoritesFragment){}else {
                        navController.navigate(R.id.action_postsFragment_to_favoritesFragment)
                    }
                    true
                }
                else -> false
            }
        }
    }
}