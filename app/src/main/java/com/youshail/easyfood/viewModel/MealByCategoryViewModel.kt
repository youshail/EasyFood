package com.youshail.easyfood.viewModel

import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.youshail.easyfood.data.remote.RetrofitInstance
import com.youshail.easyfood.data.remote.dto.MealByCategory
import com.youshail.easyfood.data.remote.dto.MealCategory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MealByCategoryViewModel : ViewModel() {

    private val mealCategoryLiveData = MutableLiveData<List<MealCategory>>()


    fun getMealByCategory(category : String){
        RetrofitInstance.foodApi.getMealByCategory(category).enqueue(object : Callback<MealByCategory>{
            override fun onResponse(
                call: Call<MealByCategory>,
                response: Response<MealByCategory>
            ) {
                response.body()?.let { mealByCategory ->
                    mealCategoryLiveData.postValue(mealByCategory.meals)
                }
            }

            override fun onFailure(call: Call<MealByCategory>, t: Throwable) {
                Log.e("MealByCategoryViewModel",t.message.toString())
            }

        })
    }

    fun observeMealCategoryLiveData(): LiveData<List<MealCategory>>{
        return mealCategoryLiveData
    }
}