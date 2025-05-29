package com.example.flexifit.screens

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.flexifit.R
import com.example.flexifit.data.models.AllData
import com.example.flexifit.data.models.Data
import com.example.flexifit.itemView.ExerciseItem
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GymPlanScreen(navController: NavHostController, bodyPart: String?){
    Column(modifier = Modifier.fillMaxWidth()) {
        TopAppBar(title = { Text(text = "$bodyPart Workout Plan",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )}, navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = "Back"
                )
            }
        })

        if(bodyPart==null){
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        else{
            GymPlanContent(bodyPart)
        }
    }
}

@Composable
fun GymPlanContent(bodyPart: String) {
    val scrollState = rememberScrollState()
    var selectedChip by remember { mutableStateOf("Stretches") }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var fullData by remember { mutableStateOf<List<Data>>(emptyList()) }
    var exerciseData by remember { mutableStateOf<List<Data>>(emptyList()) }

    // Runs only once
    LaunchedEffect(bodyPart) {
        scope.launch {
            fullData = loadData(context,bodyPart)
            exerciseData = filterData(fullData, selectedChip)
        }
    }

    Column(modifier = Modifier.padding(10.dp)) {

        Row(modifier = Modifier.fillMaxWidth()
            .horizontalScroll(scrollState),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            FilterChip(
                selected = selectedChip == "Stretches",
                onClick = {
                    selectedChip = "Stretches"
                    exerciseData = filterData(fullData, selectedChip)
                },
                label = { Text(text = "Stretches", modifier = Modifier.padding(10.dp)) },
                shape = RoundedCornerShape(20.dp),
                border = if(selectedChip!="Stretches") BorderStroke(2.dp, Color.Gray) else null,
                colors = FilterChipDefaults.filterChipColors().copy(
                    containerColor = Color.LightGray.copy(0.7f),
                    selectedContainerColor = Color.Blue,
                    selectedLabelColor = Color.White
                )
            )

            FilterChip(
                selected = selectedChip == "Bodyweight",
                onClick = {
                    selectedChip = "Bodyweight"
                    exerciseData = filterData(fullData, selectedChip)
                },
                label = { Text(text = "Bodyweight", modifier = Modifier.padding(10.dp)) },
                shape = RoundedCornerShape(20.dp),
                border = if(selectedChip!="Bodyweight") BorderStroke(2.dp, Color.Gray) else null,
                colors = FilterChipDefaults.filterChipColors().copy(
                    containerColor = Color.LightGray.copy(0.7f),
                    selectedContainerColor = Color.Blue,
                    selectedLabelColor = Color.White,
                )
            )

            FilterChip(
                selected = selectedChip == "Barbell",
                onClick = {
                    selectedChip = "Barbell"
                    exerciseData = filterData(fullData, selectedChip)
                },
                label = { Text(text = "Barbell", modifier = Modifier.padding(10.dp)) },
                shape = RoundedCornerShape(20.dp),
                border = if(selectedChip!="Barbell") BorderStroke(2.dp, Color.Gray) else null,
                colors = FilterChipDefaults.filterChipColors().copy(
                    containerColor = Color.LightGray.copy(0.7f),
                    selectedContainerColor = Color.Blue,
                    selectedLabelColor = Color.White,
                )
            )

            FilterChip(
                selected = selectedChip == "Dumbbells",
                onClick = {
                    selectedChip = "Dumbbells"
                    exerciseData = filterData(fullData, selectedChip)
                },
                label = { Text(text = "Dumbbells", modifier = Modifier.padding(10.dp)) },
                shape = RoundedCornerShape(20.dp),
                border = if(selectedChip!="Dumbbells") BorderStroke(2.dp, Color.Gray) else null,
                colors = FilterChipDefaults.filterChipColors().copy(
                    containerColor = Color.LightGray.copy(0.7f),
                    selectedContainerColor = Color.Blue,
                    selectedLabelColor = Color.White,
                )
            )

            FilterChip(
                selected = selectedChip == "Kettlebells",
                onClick = {
                    selectedChip = "Kettlebells"
                    exerciseData = filterData(fullData, selectedChip)
                },
                label = { Text(text = "Kettlebells", modifier = Modifier.padding(10.dp)) },
                shape = RoundedCornerShape(20.dp),
                border = if(selectedChip!="Kettlebells") BorderStroke(2.dp, Color.Gray) else null,
                colors = FilterChipDefaults.filterChipColors().copy(
                    containerColor = Color.LightGray.copy(0.7f),
                    selectedContainerColor = Color.Blue,
                    selectedLabelColor = Color.White,
                )
            )
        }

        var loadingTimedOut by remember { mutableStateOf(false) }
        LaunchedEffect(exerciseData) {
            if (exerciseData.isEmpty()) {
                delay(5000)
                loadingTimedOut = true
            } else {
                loadingTimedOut = false
            }
        }
        if(exerciseData.isEmpty() && !loadingTimedOut){
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        else if (exerciseData.isEmpty() && loadingTimedOut) {
            // Show no result message
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No Exercise Found", fontSize = 20.sp)
            }
        }
        else {
            LazyColumn{
                items(exerciseData.size) { it ->
                    ExerciseItem(context,exerciseData[it].title,exerciseData[it].difficulty,exerciseData[it].video_tutorials[0])
                }
            }
        }
    }
}

private fun loadData(context: Context, bodyPart:String): List<Data>{
    val text = context.assets.open("gym_data.json").bufferedReader().use { it.readText() }
    val allData = Gson().fromJson(text, AllData::class.java)
    return allData.data.filter {
        it.muscle == bodyPart
    }
}

private fun filterData(data:List<Data>, category: String): List<Data> {
    return data.filter {
        it.category == category
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PlanScreenPreview(){
    GymPlanScreen(rememberNavController(), "Chest")
}