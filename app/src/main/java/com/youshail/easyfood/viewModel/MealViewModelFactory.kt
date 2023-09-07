package com.youshail.easyfood.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.youshail.easyfood.data.local.db.MealsDatabase
import com.youshail.easyfood.data.remote.Repository

class MealViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MealDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}