package com.example.flexifit.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.flexifit.data.models.DietType
import com.example.flexifit.R
import com.example.flexifit.itemView.DietTypeItem

val DietTypeList = listOf(
    DietType(R.drawable.gluton_free, "Gluten Free"),
    DietType(R.drawable.gluton_free, "Keto-Friendly"),
    DietType(R.drawable.gluton_free, "Vegan"),
    DietType(R.drawable.gluton_free, "Vegetarian"),
    DietType(R.drawable.gluton_free, "Peanut Free"),
    DietType(R.drawable.gluton_free, "Fish Free"),
    DietType(R.drawable.gluton_free, "Sugar Free"),
    DietType(R.drawable.gluton_free, "Dairy Free"),
    DietType(R.drawable.gluton_free, "Egg Free"),
    DietType(R.drawable.gluton_free, "Immuno-Supportive"),
    DietType(R.drawable.gluton_free, "Kidney Friendly"),
    DietType(R.drawable.gluton_free, "Low Sugar"),
    DietType(R.drawable.gluton_free, "Mustard Free"),
    DietType(R.drawable.gluton_free, "No Oil Added"),
    DietType(R.drawable.gluton_free, "Shellfish Free"),
)

@Composable
//navController: NavHostController
fun DietScreen(navController: NavHostController){
    Box(modifier = Modifier.fillMaxSize()) {
        DietTypeList()
    }
}

@Composable
fun DietTypeList(){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ) {
        items(DietTypeList){ it->
            DietTypeItem(it)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DietScreenPreview(){
//    DietScreen()
}