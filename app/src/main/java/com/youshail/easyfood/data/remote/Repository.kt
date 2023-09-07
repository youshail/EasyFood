package com.youshail.easyfood.data.remote

import androidx.lifecycle.LiveData
import com.youshail.easyfood.data.local.MealInfo
import com.youshail.easyfood.data.local.dao.MealDao

class Repository(private val mealDao: MealDao) {


    fun insert(mealInfo: MealInfo){
        mealDao.insertFavorite(mealInfo)
    }

    val mealList : LiveData<List<MealInfo>> =  mealDao.getAllSavedMeals()
     fun delete(mealInfo: MealInfo){
        mealDao.deleteMeal(mealInfo)
    }
}