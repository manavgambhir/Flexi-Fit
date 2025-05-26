package com.example.flexifit.screens.onboarding

import android.content.Context
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.flexifit.navigation.Routes
import com.example.flexifit.utils.sharedPref
import com.example.flexifit.viewmodels.OnboardingViewModel

@Composable
fun OnboardStrengthTScreen(
    navController: NavHostController,
    onboardingViewModel: OnboardingViewModel
) {
    Column(modifier = Modifier.fillMaxSize()) {
        val context = LocalContext.current
        var strengthTrainingLevel by remember { mutableStateOf("") }
        OnboardingTopBar(title = "Experience with \n\nstrength training?", showBackButton = true, navController = navController)

        Spacer(modifier = Modifier.size(45.dp))

        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            OutlinedButton(
                onClick = {
                    strengthTrainingLevel = "Beginner"
                    saveData(context, strengthTrainingLevel, onboardingViewModel, navController)
                },
                border = BorderStroke(1.dp, Color.Black),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp)
            ) {
                Text("Beginner", fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.size(35.dp))

            OutlinedButton(
                onClick = {
                    strengthTrainingLevel = "Intermediate"
                    saveData(context, strengthTrainingLevel, onboardingViewModel, navController)
                },
                border = BorderStroke(1.dp, Color.Black),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp)
            ) {
                Text("Intermediate", fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.size(35.dp))

            OutlinedButton(
                onClick = {
                    strengthTrainingLevel = "Advanced"
                    saveData(context, strengthTrainingLevel, onboardingViewModel, navController)
                },
                border = BorderStroke(1.dp, Color.Black),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp)
            ) {
                Text("Advanced", fontSize = 20.sp)
            }
        }
    }
}

fun saveData(context: Context, strengthTrainingLevel: String, onboardingViewModel: OnboardingViewModel, navController: NavHostController) {
    onboardingViewModel.strengthExperience = strengthTrainingLevel

    onboardingViewModel.toUserProfile()?.let { profile->
        sharedPref.saveUserProfileLocally(context, profile)
    }

    onboardingViewModel.saveProfileToFirestore(
        onSuccess = {
            navController.navigate(Routes.BottomNav.routes) {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        },
        onFailure = { error ->
            Log.e("FirestoreError", "Error saving profile: $error")
        }
    )

    navigateToHome(navController)
}

fun navigateToHome(navController:NavHostController){
    navController.navigate(Routes.BottomNav.routes) {
        popUpTo(0) { inclusive = true }
        launchSingleTop = true
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnboardStScreenPreview() {
//    OnboardStrengthTScreen(rememberNavController(), onboardingViewModel)
}