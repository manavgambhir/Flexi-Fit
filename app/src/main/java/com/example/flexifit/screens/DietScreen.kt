package com.example.flexifit.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController

@Composable
fun DietScreen(navController: NavHostController){
    Text("Diet Screen")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DietScreenPreview(){
//    DietScreen()
}