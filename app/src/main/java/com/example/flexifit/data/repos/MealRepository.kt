package com.example.flexifit.data.repos

import com.example.flexifit.data.api.MealClient
import com.example.flexifit.data.models.MealModel

class MealRepository {
    private val api = MealClient.api

    suspend fun getMealPlan(
        q: String,
        health: String,
        mealType: String,
        calories: String
    ) = api.getMealPlan(q,health,mealType,calories)
}