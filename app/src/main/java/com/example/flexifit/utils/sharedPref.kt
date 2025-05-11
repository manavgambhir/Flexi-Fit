package com.example.flexifit.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.example.flexifit.data.models.UserProfile
import com.google.gson.Gson

object sharedPref {
    fun saveUserProfileLocally(context: Context, profile: UserProfile){
        val sharedPreferences = context.getSharedPreferences("user_profiles", MODE_PRIVATE)
        val json = Gson().toJson(profile)
        sharedPreferences.edit().putString("profile", json).apply()
    }

    fun getUserProfile(context: Context): UserProfile? {
        val sharedPreferences = context.getSharedPreferences("user_profiles", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("profile", null)
        return if (json != null) Gson().fromJson(json, UserProfile::class.java) else null
    }

    fun clearUserProfile(context: Context) {
        val sharedPreferences = context.getSharedPreferences("user_profiles", Context.MODE_PRIVATE)
        sharedPreferences.edit().remove("profile").apply()
    }
}