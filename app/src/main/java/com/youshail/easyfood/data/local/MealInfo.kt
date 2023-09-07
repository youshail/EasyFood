package com.youshail.easyfood.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "meal_info")
data class MealInfo(
    @PrimaryKey
    val mealId: String,
    val mealName: String?,
    val mealCountry: String?,
    val mealCategory:String?,
    val mealInstruction:String?,
    val mealThumb:String?,
    val mealYoutubeLink:String?
)