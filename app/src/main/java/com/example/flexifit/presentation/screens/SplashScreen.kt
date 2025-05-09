package com.example.flexifit.presentation.screens

import android.window.SplashScreen
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.flexifit.navigation.Routes
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController){
    Text("Splash Screen")

    LaunchedEffect(true) {
        delay(2000)
        navController.navigate(Routes.BottomNav.routes){
            popUpTo(0) { inclusive = true } // Clear splash from backstack
            launchSingleTop = true
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview(){
//    SplashScreen()
}