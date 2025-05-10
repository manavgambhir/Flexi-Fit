package com.example.flexifit.data.models

data class UserProfile(
    val uid: String = "",
    val name: String = "",
    val dob: String = "Unknown",
    val gender: String = "",
    val weight: Float = 0f,
    val height: Float = 0f,
    val strengthTrainingExperience: String = "",
    val injury: String = ""
)