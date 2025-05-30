package com.example.flexifit.screens

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.flexifit.data.models.Pose
import com.example.flexifit.data.models.YogaPoses
import com.example.flexifit.itemView.YogaItem
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun YogaScreen(navController: NavHostController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var yogaData by remember { mutableStateOf<List<Pose>>(emptyList()) }

    // Runs only once
    LaunchedEffect(Unit) {
        scope.launch {
            yogaData = loadYogaData(context)
        }
    }

    Column {
        var loadingTimedOut by remember { mutableStateOf(false) }
        LaunchedEffect(yogaData) {
            if (yogaData.isEmpty()) {
                delay(5000)
                loadingTimedOut = true
            } else {
                loadingTimedOut = false
            }
        }
        if (yogaData.isEmpty() && !loadingTimedOut) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (yogaData.isEmpty() && loadingTimedOut) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No Result Found", fontSize = 20.sp)
            }
        } else {
            LazyColumn {
                items(yogaData.size) { it ->
                    YogaItem(context,navController,yogaData[it])
                }
            }
        }
    }
}

fun loadYogaData(context: Context): List<Pose> {
    val file = context.assets.open("yoga_data.json").bufferedReader()
        .use { it.readText() }
    val data = Gson().fromJson(file, YogaPoses::class.java)
    return data.poses
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun YogaScreenPreview(){
//    YogaScreen()
}
