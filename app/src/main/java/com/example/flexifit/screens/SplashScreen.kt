package com.example.flexifit.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.flexifit.R
import com.example.flexifit.navigation.Routes
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Image(
            painter = painterResource(R.drawable.flexifit_ic),
            contentDescription = "FlexiFit Icon"
        )
    }

    LaunchedEffect(true) {
        delay(2000)
        // Gets the current user
        val currentUser = FirebaseAuth.getInstance().currentUser
        if(currentUser!=null){
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