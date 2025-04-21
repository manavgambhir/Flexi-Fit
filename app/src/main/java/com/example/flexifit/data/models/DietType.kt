package com.example.flexifit.data.models

import androidx.annotation.DrawableRes

data class DietType(
    // Image resource
    @DrawableRes val imageRes: Int,
    val name: String
)
