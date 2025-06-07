package com.example.flexifit.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.flexifit.navigation.Routes
import kotlinx.coroutines.delay
import java.util.Locale

@Composable
fun CameraScreen(navController: NavHostController) {
    var text by remember { mutableStateOf("") }
    var isVisible by remember { mutableStateOf(false) }
    var trigger by remember { mutableStateOf(false) }

    var timerText by remember { mutableStateOf("00:00:00") }
    var isTimerRunning by remember { mutableStateOf(false) }

    LaunchedEffect(trigger) {
        if (trigger) {
            isVisible = true
            val texts = listOf("3", "2", "1", "Let's Go")
            for (it in texts) {
                text = it
                delay(1000)
            }
            isVisible = false
            isTimerRunning = true
        }
    }

    LaunchedEffect(isTimerRunning) {
        if(isTimerRunning){
            var seconds = 0
            while(isTimerRunning){
                val hours = seconds / 3600
                val minutes = (seconds % 3600) / 60
                val secs = seconds % 60
                // %-> format specify | 0-> pads number with leading 0
                // 2-> total width 2 | d-> formats it as an integer
                timerText = String.format(Locale.ENGLISH,"%02d:%02d:%02d", hours, minutes, secs)
                delay(1000L)
                seconds++
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black).border(BorderStroke(7.dp, Color.Green))){
        if (isVisible) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.7f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = text, fontSize = 35.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }
        }

        Column(modifier = Modifier.fillMaxWidth().padding(25.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Spacer(modifier = Modifier.size(50.dp))

            Text(text = "Squats", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 22.sp)

            Spacer(modifier = Modifier.size(10.dp))

            Text(text = timerText, color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
        }

        Column(modifier = Modifier.fillMaxSize().padding(25.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom) {
            Text(text = "0", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 62.sp)

            Spacer(modifier = Modifier.size(25.dp))


            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                IconButton(onClick = {
                    trigger = true
                },
                    modifier = Modifier.size(75.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.PlayArrow,
                        contentDescription = "Start Activity",
                        modifier = Modifier.size(75.dp).clip(RoundedCornerShape(40.dp)).background(Color.White).padding(6.dp),
                        tint = Color.Blue
                    )
                }


                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                    IconButton(onClick = {
                        navController.popBackStack()
                        isTimerRunning = false
                    },
                        modifier = Modifier.size(50.dp)) {
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            contentDescription = "End Activity",
                            modifier = Modifier.size(50.dp).clip(RoundedCornerShape(40.dp)).background(Color.White).padding(6.dp),
                            tint = Color.Blue
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.size(15.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CameraScreenPreview(){
    CameraScreen(rememberNavController())
}