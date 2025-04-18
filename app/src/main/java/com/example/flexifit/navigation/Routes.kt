package com.example.flexifit.navigation

sealed class Routes(val routes:String) {
    object Gym:Routes("gym")
    object Yoga:Routes("yoga")
    object Diet:Routes("diet")
    object Plan:Routes("workout_plan")
    object BottomNav:Routes("bottomNav")
    object Splash:Routes("splash")

//    object UserProfile:Routes("profile")

//    object Login:Routes("login")
//    object Register:Routes("register")
//    object OtherUserProfile:Routes("other_user_profile/{data}")
}