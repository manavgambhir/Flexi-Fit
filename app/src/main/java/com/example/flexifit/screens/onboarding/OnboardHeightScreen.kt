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
fun OnboardHeightScreen(navController: NavHostController) {
    var height by remember { mutableStateOf("") }
    TextInputScreen(
        title = "Height",
        value = height,
        hint = "In cm",
        onValueChange = { height = it },
        onNextClick = {
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