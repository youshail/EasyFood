package com.youshail.easyfood.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.youshail.easyfood.data.local.MealInfo

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(meal: MealInfo)

    @Delete
    fun deleteMeal(meal:MealInfo)

   @Query("SELECT * FROM meal_info order by mealId asc")
     fun getAllSavedMeals(): LiveData<List<MealInfo>>
    /*
       @Query("SELECT * FROM meal_information WHERE mealId =:id")
        fun getMealById(id:String):MealInfo

       @Query("DELETE FROM meal_information WHERE mealId =:id")
        fun deleteMealById(id:String)*/


}