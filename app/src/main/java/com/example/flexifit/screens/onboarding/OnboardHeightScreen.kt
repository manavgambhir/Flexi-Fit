package com.example.flexifit.screens.onboarding

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.flexifit.navigation.Routes
import com.example.flexifit.viewmodels.OnboardingViewModel

@Composable
fun OnboardHeightScreen(navController: NavHostController, onboardingViewModel: OnboardingViewModel) {
    var height by remember { mutableStateOf("") }
    InputScreen(
        title = "Height",
        value = height,
        hint = "In cm",
        onValueChange = { height = it },
        onNextClick = {
            onboardingViewModel.height = height
            Log.d("onBoardingTest","Height entered in VM $height")
            navController.navigate(Routes.OnboardWeight.routes)
        },
        keyboardType = KeyboardType.Decimal,
        navController = navController
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnboardHtScreenPreview() {
//    OnboardHeightScreen()
}