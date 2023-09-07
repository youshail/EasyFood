package com.youshail.easyfood.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.youshail.easyfood.R
import com.youshail.easyfood.data.local.db.MealsDatabase
import com.youshail.easyfood.data.remote.Repository
import com.youshail.easyfood.viewModel.HomeViewModel
import com.youshail.easyfood.viewModel.HomeViewModelFactory

class MainActivity : AppCompatActivity() {

    val homeViewModel : HomeViewModel by lazy{
        val mealsDatabase = MealsDatabase.getDatabase(this)
        val repository = Repository(mealsDatabase.mealDao())
        val homeViewModelFactory = HomeViewModelFactory(repository)
        ViewModelProvider(this,homeViewModelFactory)[HomeViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.btn_nav)
        val navController =  Navigation.findNavController(this, R.id.host_fragment)

        NavigationUI.setupWithNavController(bottomNavigation,navController)

    }
}


