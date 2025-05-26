package com.example.flexifit.data.models

data class MealPlanData(
    val health: String,
    val calories:Double,
    val bfastIngredient: String,
    val bfastChapati: Int,
    val bfastRice: Boolean,
    val lunchIngredient: String,
    val lunchChapati: Int,
    val lunchRice: Boolean,
    val dinnerIngredient: String,
    val dinnerChapati: Int,
    val dinnerRice: Boolean
)
