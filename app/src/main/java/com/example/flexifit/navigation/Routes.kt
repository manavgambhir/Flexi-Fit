package com.example.flexifit.navigation

sealed class Routes(val routes:String) {
    object Gym:Routes("gym")
    object GymPlan:Routes("gym_plan/{bodyPart}")
    object ExerciseDetailGym:Routes("exercise_details_gym/{exData}")

    object Yoga:Routes("yoga")
    object ExerciseDetailYoga:Routes("exercise_detail_yoga/{yogaPose}")


    object Diet:Routes("diet")
    object MealPlan:Routes("meal_plan/{mealData}")

    object BottomNav:Routes("bottomNav")
    object Splash:Routes("splash")

    object SignIn:Routes("sign_in")
    object OnboardName:Routes("onboard_name")
    object OnboardDOB:Routes("onboard_dob")
    object OnboardHeight:Routes("onboard_height")
    object OnboardWeight:Routes("onboard_weight")
    object OnboardGender:Routes("onboard_gender")
    object OnboardStrength:Routes("onboard_strength")


    //    object WorkoutPlan:Routes("workout_plan")
//    object UserProfile:Routes("profile")

//    object OtherUserProfile:Routes("other_user_profile/{data}")
}