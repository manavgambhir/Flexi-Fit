package com.example.flexifit.data.api

import com.example.flexifit.MealQueryInterceptor
import com.google.gson.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MealClient {
    val gson: Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()

    // To add interceptor
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(MealQueryInterceptor())
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.edamam.com")
        .client(okHttpClient)   // Set the custom OkHttpClient containing the interceptor
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val api: MealService = retrofit.create(MealService::class.java)
}