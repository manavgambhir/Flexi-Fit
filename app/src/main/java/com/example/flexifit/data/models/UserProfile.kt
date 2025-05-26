package com.example.flexifit.data.models

data class UserProfile(
    val name: String = "",
    val dob: String = "",
    val gender: String = "",
    val weight: Double = 0.0,
    val height: Double = 0.0,
    val strengthExperience: String = "",
//    val injuryInfo: String = ""
)