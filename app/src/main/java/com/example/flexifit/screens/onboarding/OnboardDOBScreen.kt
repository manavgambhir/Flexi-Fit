package com.example.flexifit.screens.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.flexifit.navigation.Routes

@Composable
fun OnboardDOBScreen(navController: NavHostController) {
    var dob by remember { mutableStateOf("") }
    InputScreen(
        title = "Date of Birth",
        value = dob,
        hint = "In DD-MM-YYYY format",
        onValueChange = { dob = it },
        onNextClick = {
            navController.navigate(Routes.OnboardHeight.routes)
        },
        keyboardType = KeyboardType.Decimal,
        navController = navController
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnboarDOBScreenPreview() {
//    OnboardDOBScreen()
}