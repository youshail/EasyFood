package com.youshail.easyfood.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val foodApi : FoodApi by lazy {
        Retrofit.Builder()
            .baseUrl(FoodApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FoodApi::class.java)
    }
}