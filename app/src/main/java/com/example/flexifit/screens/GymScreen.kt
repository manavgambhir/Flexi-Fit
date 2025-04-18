package com.example.flexifit.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController

@Composable
fun GymScreen(navController: NavHostController){
    Text("Gym Screen")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GymScreenPreview(){
//    GymScreen()
}