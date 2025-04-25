package com.example.flexifit.data.api


import com.example.flexifit.data.models.MealModel
import retrofit2.Response
import retrofit2.http.*

interface MealService {
    @GET("/api/recipes/v2")
    suspend fun getMealPlan(
        @Query("q") q:String,
//        @Query("diet") diet:String,
        @Query("health") health:String,
        @Query("mealType") mealType:String,
        @Query("calories") calories:String,
    ): Response<MealModel>
}