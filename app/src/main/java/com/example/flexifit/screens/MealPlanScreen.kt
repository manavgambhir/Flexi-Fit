package com.example.flexifit.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealPlanScreen(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TopAppBar(title = { Text(text = "Full Day Meal Plan"
                                 ,fontSize = 24.sp,
                                 fontWeight = FontWeight.Bold)
        }, actions = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Back"
                )
            }
        })

    }
}

@Composable
fun MealPlanContent() {
    var bfastCheck by remember { mutableStateOf(false) }
    var lunchCheck by remember { mutableStateOf(false) }
    var dinnerCheck by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(scrollState)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(2.dp, Color.Black)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row() {
                    Checkbox(
                        modifier = Modifier.size(22.dp),
                        checked = bfastCheck,
                        onCheckedChange = {
                            bfastCheck = !bfastCheck
                        }
                    )

                    Spacer(modifier = Modifier.size(10.dp))

                    Text(text = "BREAKFAST", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.size(10.dp))

                Text(
                    text = "There's nothing like starting the day with a healthy, filling breakfast",
                    textAlign = TextAlign.Center
                )

                LazyColumn {
                    // TODO: Create Item for the list and populate content for it
                }
            }
        }

        Spacer(modifier = Modifier.size(20.dp))

        Card(modifier = Modifier.fillMaxWidth(), border = BorderStroke(2.dp, Color.Black)) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row() {
                    Checkbox(
                        modifier = Modifier.size(22.dp),
                        checked = lunchCheck,
                        onCheckedChange = { lunchCheck = !lunchCheck }
                    )

                    Spacer(modifier = Modifier.size(10.dp))

                    Text(text = "LUNCH", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.size(10.dp))

                Text(
                    text = "Lunch, the sacred middle ground between morning hustle and afternoon grind.",
                    textAlign = TextAlign.Center
                )

                LazyColumn {
                    // TODO: Create Item for the list and populate content for it
                }
            }
        }

        Spacer(modifier = Modifier.size(20.dp))

        Card(modifier = Modifier.fillMaxWidth(), border = BorderStroke(2.dp, Color.Black)) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row() {
                    Checkbox(
                        modifier = Modifier.size(22.dp),
                        checked = dinnerCheck,
                        onCheckedChange = { dinnerCheck = !dinnerCheck }
                    )

                    Spacer(modifier = Modifier.size(10.dp))

                    Text(text = "DINNER", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.size(10.dp))

                Text(
                    text = "The best memories are made around the dinner table",
                    textAlign = TextAlign.Center
                )

                LazyColumn {
                    // TODO: Create Item for the list and populate content for it
                }
            }
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MealPlanScreenPreview() {
    val novHostController = rememberNavController()
    MealPlanScreen(novHostController)
}