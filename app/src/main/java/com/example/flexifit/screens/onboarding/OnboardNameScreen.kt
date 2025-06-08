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
fun OnboardNameScreen(navController: NavHostController, onboardingViewModel: OnboardingViewModel) {
    var name by remember { mutableStateOf("") }
    InputScreen(
        title = "Name",
        value = name,
        onValueChange = { name = it },
        onNextClick = {
            onboardingViewModel.name = name
            navController.navigate(Routes.OnboardDOB.routes)
        },
        keyboardType = KeyboardType.Text,
        navController = navController
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnboardNameScreenPreview() {
//    OnboardNameScreen()
}