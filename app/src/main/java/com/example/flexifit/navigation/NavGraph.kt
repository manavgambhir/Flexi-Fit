package com.example.flexifit.navigation

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.flexifit.data.models.ExerciseData
import com.example.flexifit.data.models.MealPlanData
import com.example.flexifit.data.models.Pose
import com.example.flexifit.data.models.UserProfile
import com.example.flexifit.screens.BottomNav
import com.example.flexifit.screens.CameraScreen
import com.example.flexifit.screens.DietScreen
import com.example.flexifit.screens.ExerciseDetailScreen
import com.example.flexifit.screens.GymPlanScreen
import com.example.flexifit.screens.GymScreen
import com.example.flexifit.screens.MealPlanScreen
import com.example.flexifit.screens.onboarding.SignInScreen
import com.example.flexifit.screens.SplashScreen
import com.example.flexifit.screens.UserAccountScreen
import com.example.flexifit.screens.YogaScreen
import com.example.flexifit.screens.onboarding.OnboardDOBScreen
import com.example.flexifit.screens.onboarding.OnboardGenderScreen
import com.example.flexifit.screens.onboarding.OnboardHeightScreen
import com.example.flexifit.screens.onboarding.OnboardNameScreen
import com.example.flexifit.screens.onboarding.OnboardStrengthTScreen
import com.example.flexifit.screens.onboarding.OnboardWeightScreen
import com.example.flexifit.viewmodels.OnboardingViewModel
import com.google.gson.Gson

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(navHostController: NavHostController) {
    val onboardingViewModel:OnboardingViewModel = viewModel()
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

        composable(Routes.SignIn.routes){
            SignInScreen(navHostController,onboardingViewModel)
        }

        composable(Routes.OnboardName.routes){
            OnboardNameScreen(navHostController,onboardingViewModel)
        }

        composable(Routes.OnboardDOB.routes){
            OnboardDOBScreen(navHostController,onboardingViewModel)
        }

        composable(Routes.OnboardHeight.routes){
            OnboardHeightScreen(navHostController,onboardingViewModel)
        }

        composable(Routes.OnboardWeight.routes){
            OnboardWeightScreen(navHostController,onboardingViewModel)
        }

        composable(Routes.OnboardGender.routes){
            OnboardGenderScreen(navHostController,onboardingViewModel)
        }

        composable(Routes.OnboardStrength.routes){
            OnboardStrengthTScreen(navHostController,onboardingViewModel)
        }

        composable(Routes.GymPlan.routes){
            val bodyPart = it.arguments?.getString("bodyPart")
            GymPlanScreen(navHostController,bodyPart)
        }

        composable(Routes.ExerciseDetailGym.routes){
            val json = it.arguments?.getString("exData")
            val decodeJson = Uri.decode(json)
            val exercise = Gson().fromJson(decodeJson, ExerciseData::class.java)
            ExerciseDetailScreen(navHostController,exercise)
        }

        composable(Routes.ExerciseDetailYoga.routes){
            val json = it.arguments?.getString("yogaPose")
            val decodeJson = Uri.decode(json)
            val yoga = Gson().fromJson(decodeJson,Pose::class.java)
            ExerciseDetailScreen(navHostController,yoga)
        }

        composable(Routes.UserAccount.routes){
            val json = it.arguments?.getString("userData")
            val decodeJson = Uri.decode(json)
            val userDetails = Gson().fromJson(decodeJson,UserProfile::class.java)
            UserAccountScreen(navHostController,userDetails)
        }

        composable(Routes.Camera.routes){
            val exerciseData = it.arguments?.getString("exerciseData")
            CameraScreen(navHostController,exerciseData)
        }


//        composable(Routes.OtherUserProfile.routes){
//            val data = it.arguments?.getString("data")
//            OtherUserProfile(navController, data!!)
//        }

    }
}


