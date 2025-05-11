package com.example.flexifit.screens.onboarding

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.flexifit.navigation.Routes
import com.example.flexifit.viewmodels.OnboardingViewModel

@Composable
fun OnboardGenderScreen(navController: NavHostController, onboardingViewModel: OnboardingViewModel) {
    var gender by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        OnboardingTopBar(title = "Gender", showBackButton = true, navController = navController)

        Spacer(modifier = Modifier.size(45.dp))

        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            OutlinedButton(
                onClick = {
                    gender = "Male"
                    onboardingViewModel.gender = gender
                    navController.navigate(Routes.OnboardStrength.routes)
                },
                border = BorderStroke(1.dp, Color.Black),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier.width(200.dp).height(50.dp)
            ) {
                Text("Male", fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.size(35.dp))

            OutlinedButton(
                onClick = {
                    gender = "Female"
                    onboardingViewModel.gender = gender
                    Log.d("onBoardingTest","Gender selected as $gender")
                    navController.navigate(Routes.OnboardStrength.routes)
                },
                border = BorderStroke(1.dp, Color.Black),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier.width(200.dp).height(50.dp)
            ) {
                Text("Female", fontSize = 20.sp)
            }
        }
    }



}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GenderScreenPreview(){
//    OnboardGenderScreen(
//        navController = rememberNavController(),
//        onboardingViewModel = onboardingViewModel
//    )
}