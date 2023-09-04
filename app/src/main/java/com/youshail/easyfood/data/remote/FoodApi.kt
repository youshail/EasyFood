package com.youshail.easyfood.data.remote


import com.youshail.easyfood.data.remote.dto.CategoryList
import com.youshail.easyfood.data.remote.dto.MealByCategory
import com.youshail.easyfood.data.remote.dto.RandomMealResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodApi {

    @GET("random.php")
    fun getRandomMeal(): Call<RandomMealResponse>

    @GET("lookup.php?")
    fun getMealDetails(@Query("i") id:String) : Call<RandomMealResponse>


    @GET("filter.php")
    fun getMealByCategory(@Query("c") id: String) : Call<MealByCategory>

    @GET("categories.php")
    fun getCategories(): Call<CategoryList>

    companion object {
        const val BASE_URL="https://www.themealdb.com/api/json/v1/1/"
    }
}