package com.example.flexifit.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.flexifit.data.models.MealPlanData
import com.example.flexifit.screens.*
import com.google.gson.Gson

@Composable
fun NavGraph(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = Routes.Splash.routes){
        composable(Routes.Splash.routes){
            SplashScreen(navHostController)
        }

        composable(Routes.Diet.routes){
            DietScreen(navHostController)
        }

        composable(Routes.MealPlan.routes){
            val json = it.arguments?.getString("mealData")
            val decodeJson = Uri.decode(json)
            val mealPlanData = Gson().fromJson(decodeJson,MealPlanData::class.java)
            MealPlanScreen(navHostController,mealPlanData)
        }

        composable(Routes.Gym.routes){
            GymScreen(navHostController)
        }

        composable(Routes.Yoga.routes){
            YogaScreen(navHostController)
        }

        composable(Routes.BottomNav.routes){
            BottomNav(navHostController)
        }
//
//        composable(Routes.OtherUserProfile.routes){
//            val data = it.arguments?.getString("data")
//            OtherUserProfile(navController, data!!)
//        }

    }
}


