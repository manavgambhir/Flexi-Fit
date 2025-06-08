package com.example.flexifit.screens.onboarding

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
fun OnboardWeightScreen(navController: NavHostController, onboardingViewModel: OnboardingViewModel) {
    var weight by remember { mutableStateOf("") }
    InputScreen(
        title = "Weight",
        value = weight,
        hint = "In Kg",
        onValueChange = { weight = it },
        onNextClick = {
            onboardingViewModel.weight = weight
            navController.navigate(Routes.OnboardGender.routes)
        },
        keyboardType = KeyboardType.Decimal,
        navController = navController
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnboardWtScreenPreview() {
//    OnboardWeightScreen()
}