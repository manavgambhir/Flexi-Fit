package com.example.flexifit.screens

import android.window.SplashScreen
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.flexifit.navigation.Routes
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController){
    Text("Splash Screen")

    LaunchedEffect(true) {
        delay(2000)
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            navController.navigate(Routes.BottomNav.routes) {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        }
        else{
            navController.navigate(Routes.SignIn.routes){
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview(){
//    SplashScreen()
}