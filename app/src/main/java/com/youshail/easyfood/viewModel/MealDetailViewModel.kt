package com.youshail.easyfood.viewModel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youshail.easyfood.data.local.MealInfo
import com.youshail.easyfood.data.local.db.MealsDatabase
import com.youshail.easyfood.data.remote.Repository
import com.youshail.easyfood.data.remote.RetrofitInstance
import com.youshail.easyfood.data.remote.dto.Meal
import com.youshail.easyfood.data.remote.dto.RandomMealResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealDetailViewModel(
    private val repository: Repository
): ViewModel() {
    private var mealDetailLiveData = MutableLiveData<Meal>()

    fun getMealDetail(id: String){
        RetrofitInstance.foodApi.getMealDetails(id).enqueue(object: Callback<RandomMealResponse>{
            override fun onResponse(
                call: Call<RandomMealResponse>,
                response: Response<RandomMealResponse>
            ) {
                if(response.body() != null){
                  mealDetailLiveData.value = response.body()!!.meals[0]
                }else
                    return
            }

            override fun onFailure(call: Call<RandomMealResponse>, t: Throwable) {
                Log.d(TAG,t.message.toString())
            }

        })
    }

    fun observeMailDetailLiveData(): LiveData<Meal>{
        return mealDetailLiveData
    }

    fun insertMeal(mealInfo: MealInfo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(mealInfo)
        }
    }


}