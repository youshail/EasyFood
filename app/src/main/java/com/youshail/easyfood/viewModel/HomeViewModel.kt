package com.youshail.easyfood.viewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.youshail.easyfood.data.remote.RetrofitInstance
import com.youshail.easyfood.data.remote.dto.Category
import com.youshail.easyfood.data.remote.dto.CategoryList
import com.youshail.easyfood.data.remote.dto.Meal
import com.youshail.easyfood.data.remote.dto.MealByCategory
import com.youshail.easyfood.data.remote.dto.MealCategory
import com.youshail.easyfood.data.remote.dto.RandomMealResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val randomMealLiveData = MutableLiveData<Meal>()
    private val popularMealsLiveData = MutableLiveData<List<MealCategory>>()
    private val categoryListLiveData = MutableLiveData<List<Category>>()

    fun getRandomMeal() {

        RetrofitInstance.foodApi.getRandomMeal().enqueue(
            object : Callback<RandomMealResponse> {
                override fun onResponse(
                    call: Call<RandomMealResponse>,
                    response: Response<RandomMealResponse>
                ) {
                    if (response.body() != null) {
                        val randomMeal = response.body()!!.meals[0]
                        randomMealLiveData.value = randomMeal
                    } else {
                        return
                    }
                }

                override fun onFailure(call: Call<RandomMealResponse>, t: Throwable) {
                    Log.d("HomeFragment", t.message.toString())
                }

            }
        )
    }

    fun observeRandomLiveData(): LiveData<Meal>{
        return randomMealLiveData
    }

    fun getPopularMeals(category: String){
        RetrofitInstance.foodApi.getMealByCategory(category).enqueue(object : Callback<MealByCategory>{
            override fun onResponse(
                call: Call<MealByCategory>,
                response: Response<MealByCategory>
            ) {
                if (response.body()!=null){
                    popularMealsLiveData.value = response.body()!!.meals
                }else
                    return
            }

            override fun onFailure(call: Call<MealByCategory>, t: Throwable) {
                Log.e(ContentValues.TAG, t.message.toString())
            }

        })
    }
    fun observePopularMealsLiveData(): LiveData<List<MealCategory>>{
        return popularMealsLiveData
    }

    fun getCategories(){

        RetrofitInstance.foodApi.getCategories().enqueue(object : Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body().let { categoryList ->
                    categoryListLiveData.postValue(categoryList?.categories)
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.e(ContentValues.TAG, t.message.toString())
            }

        })
    }

    fun observeCategoryListLiveData(): LiveData<List<Category>>{
        return categoryListLiveData
    }


}