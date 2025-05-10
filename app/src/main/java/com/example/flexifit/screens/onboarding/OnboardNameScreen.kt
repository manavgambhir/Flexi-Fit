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

@Composable
fun OnboardNameScreen(navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    TextInputScreen(
        title = "Name",
        value = name,
        onValueChange = { name = it },
        onNextClick = {
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