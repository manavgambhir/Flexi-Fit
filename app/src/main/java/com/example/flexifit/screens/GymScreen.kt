package com.example.flexifit.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.flexifit.R
import com.example.flexifit.navigation.Routes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
//navController: NavHostController
fun GymScreen(navController: NavHostController){
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { 2 })
    val tabs = listOf("FRONT", "BACK")
    Column(modifier = Modifier.fillMaxSize()) {
        TabRow( selectedTabIndex = pagerState.currentPage,
            modifier = Modifier.fillMaxWidth()
        ){
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = pagerState.currentPage == index,
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Color.Gray,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page->
            var imageRes by remember { mutableIntStateOf(R.drawable.front_body) }
            imageRes = when(page){
                0-> R.drawable.front_body
                1-> R.drawable.back_body
                else -> { R.drawable.front_body }
            }

            BoxWithConstraints(modifier = Modifier.fillMaxSize().padding(24.dp)) {
                val interactionSource = remember { MutableInteractionSource() }
                val density = LocalDensity.current
                val imageRatio = 3f / 4f
                val availableWidth = constraints.maxWidth.toFloat()
                val imageHeight = availableWidth / imageRatio
                val imageWidth = availableWidth

                val verticalPadding = (constraints.maxHeight.toFloat() - imageHeight) / 2

                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "Tab Image",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .aspectRatio(imageRatio)
                        .fillMaxWidth()
                )

                with(density) {
                    @Composable
                    fun overlayBox(x: Float, y: Float, w: Float, h: Float, color: Color, muscleName:String, onClick: () -> Unit) {
                        Box(
                            modifier = Modifier
                                .absoluteOffset(
                                    x = (x * imageWidth).toDp(),
                                    y = (verticalPadding + y * imageHeight).toDp()
                                )
                                .size(
                                    width = (w * imageWidth).toDp(),
                                    height = (h * imageHeight).toDp()
                                )
                                .background(color)
                                .clickable(interactionSource = interactionSource, indication = null) {
                                    onClick()
                                    val route = Routes.GymPlan.routes.replace("{bodyPart}",muscleName)
                                    navController.navigate(route)
                                }
                        )
                    }

                    if(page==0){
                        overlayBox(0.393f, 0.20f, 0.21f, 0.09f, muscleName = "Chest", color = Color.Transparent) {
                            // Handle chest click
                            imageRes = R.drawable.chest
                        }
                        overlayBox(0.425f, 0.29f, 0.15f, 0.2f, muscleName = "Abdominals", color = Color.Transparent) {
                            // Handle abs click
                            imageRes = R.drawable.abs
                        }
                        overlayBox(0.30f, 0.184f, 0.1f, 0.08f, muscleName = "Shoulders", color = Color.Transparent) {
                            // Left shoulder
                            imageRes = R.drawable.left_shoulder
                        }
                        overlayBox(0.6f, 0.184f, 0.1f, 0.08f, muscleName = "Shoulders", color = Color.Transparent) {
                            // Right shoulder
                            imageRes = R.drawable.right_shoulder
                        }
                        overlayBox(0.35f, 0.49f, 0.12f, 0.22f, muscleName = "Hamstrings", color = Color.Transparent) {
                            // Left leg
                            imageRes = R.drawable.left_quad
                        }
                        overlayBox(0.53f, 0.49f, 0.12f, 0.22f, muscleName = "Hamstrings", color = Color.Transparent) {
                            // Right leg
                            imageRes = R.drawable.right_quad
                        }
                        overlayBox(0.38f, 0.15f, 0.08f, 0.05f, muscleName = "Traps", color = Color.Transparent) {
                            // Left Trap
                            imageRes = R.drawable.traps
                        }
                        overlayBox(0.54f, 0.15f, 0.08f, 0.05f, muscleName = "Traps", color = Color.Transparent) {
                            // Right Trap
                            imageRes = R.drawable.traps
                        }
                        overlayBox(0.26f, 0.26f, 0.11f, 0.1f, muscleName = "Biceps", color = Color.Transparent) {
                            // Left Bicep
                            imageRes = R.drawable.left_bicep
                        }
                        overlayBox(0.63f, 0.26f, 0.11f, 0.1f, muscleName = "Biceps", color = Color.Transparent) {
                            // Right Bicep
                            imageRes = R.drawable.right_bicep
                        }
                        overlayBox(0.17f, 0.32f, 0.12f, 0.13f, muscleName = "Forearms", color = Color.Transparent) {
                            // Left forearm
                            imageRes = R.drawable.forearm_left
                        }
                        overlayBox(0.71f, 0.32f, 0.12f, 0.13f, muscleName = "Forearms", color = Color.Transparent) {
                            // Right forearm
                            imageRes = R.drawable.forearm_right
                        }
                        scope.launch {
                            delay(200)
                            imageRes = R.drawable.front_body
                        }
                    }
                    else{
                        overlayBox(0.30f, 0.184f, 0.13f, 0.07f, muscleName = "Shoulders", color = Color.Transparent) {
                            // Left shoulder
                            imageRes = R.drawable.left_shoulder_back
                        }
                        overlayBox(0.57f, 0.184f, 0.13f, 0.07f, muscleName = "Shoulders", color = Color.Transparent) {
                            // Right shoulder
                            imageRes = R.drawable.right_shoulder_back
                        }
                        overlayBox(0.26f, 0.25f, 0.11f, 0.1f, muscleName = "Triceps", color = Color.Transparent) {
                            // Left Tricep
                            imageRes = R.drawable.tricep_left
                        }
                        overlayBox(0.63f, 0.25f, 0.11f, 0.1f, muscleName = "Triceps", color = Color.Transparent) {
                            // Right Tricep
                            imageRes = R.drawable.tricep_right
                        }
                        overlayBox(0.17f, 0.33f, 0.12f, 0.13f, muscleName = "Forearms", color = Color.Transparent) {
                            // Left forearm
                            imageRes = R.drawable.forearm_left_back
                        }
                        overlayBox(0.71f, 0.33f, 0.12f, 0.13f, muscleName = "Forearms", color = Color.Transparent) {
                            // Right forearm
                            imageRes = R.drawable.forearm_right_back
                        }
                        overlayBox(0.44f, 0.21f, 0.12f, 0.11f,  muscleName = "Traps_middle", color = Color.Transparent) {
                            // Mid back
                            imageRes = R.drawable.traps_mid_back
                        }
                        overlayBox(0.36f, 0.25f, 0.12f, 0.19f, muscleName = "Lats", color = Color.Transparent) {
                            // Left back
                            imageRes = R.drawable.left_lat
                        }
                        overlayBox(0.52f, 0.25f, 0.12f, 0.19f, muscleName = "Lats", color = Color.Transparent) {
                            // Right back
                            imageRes = R.drawable.right_lat
                        }
                        overlayBox(0.4f, 0.43f, 0.2f, 0.11f, muscleName = "Glutes", color = Color.Transparent) {
                            // Glutes
                            imageRes = R.drawable.hips
                        }
                        overlayBox(0.46f, 0.31f, 0.08f, 0.12f, muscleName = "Lowerback", color = Color.Transparent) {
                            // Mid lower back
                            imageRes = R.drawable.lower_back
                        }
                        overlayBox(0.35f, 0.53f, 0.12f, 0.18f, muscleName = "Hamstrings", color = Color.Transparent) {
                            // Left leg
                            imageRes = R.drawable.left_glute
                        }
                        overlayBox(0.53f, 0.53f, 0.12f, 0.18f, muscleName = "Hamstrings", color = Color.Transparent) {
                            // Right leg
                            imageRes = R.drawable.right_glute
                        }
                        overlayBox(0.34f, 0.74f, 0.1f, 0.11f, muscleName = "Calves", color = Color.Transparent) {
                            // Left leg
                            imageRes = R.drawable.left_calve_back
                        }
                        overlayBox(0.56f, 0.74f, 0.1f, 0.11f, muscleName = "Calves", color = Color.Transparent) {
                            // Right leg
                            imageRes = R.drawable.right_calve_back
                        }
                        scope.launch {
                            delay(200)
                            imageRes = R.drawable.back_body
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GymScreenPreview(){
    GymScreen(rememberNavController())
}