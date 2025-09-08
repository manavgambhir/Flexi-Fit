package com.example.flexifit.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.view.SurfaceView
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.tensorflow.lite.examples.poseestimation.camera.CameraSource
import org.tensorflow.lite.examples.poseestimation.data.Device
import org.tensorflow.lite.examples.poseestimation.ml.ModelType
import org.tensorflow.lite.examples.poseestimation.ml.MoveNet
import org.tensorflow.lite.examples.poseestimation.ml.PoseClassifier
import org.tensorflow.lite.examples.poseestimation.ml.PoseDetector
import java.util.Locale

@Composable
fun CameraScreen(navController: NavHostController, exerciseData: String?) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var text by remember { mutableStateOf("") }
    var isVisible by remember { mutableStateOf(false) }
    var trigger by remember { mutableStateOf(false) }

    var timerText by remember { mutableStateOf("00:00:00") }
    var isTimerRunning by remember { mutableStateOf(false) }

    var cameraSource by remember { mutableStateOf<CameraSource?>(null) }
    var poseDetector by remember { mutableStateOf<PoseDetector?>(null) }
    var correct_label by remember { mutableStateOf("") }

    var hasCameraPermission by remember {
        mutableStateOf(ContextCompat.checkSelfPermission(
            context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ){ isGranted->
        hasCameraPermission = isGranted
        if(!isGranted){
            Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

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
            withContext(Dispatchers.Default){
                while(isTimerRunning){
                    val hours = seconds / 3600
                    val minutes = (seconds % 3600) / 60
                    val secs = seconds % 60
                    // %-> format specify | 0-> pads number with leading 0
                    // 2-> total width 2 | d-> formats it as an integer
                    val format = String.format(Locale.ENGLISH,"%02d:%02d:%02d", hours, minutes, secs)

                    withContext(Dispatchers.Main){
                        timerText = format
                    }

                    delay(1000L)
                    seconds++
                }
            }
        }
    }

    // When the screen is destroyed, close the camera source
    DisposableEffect(Unit) {
        onDispose {
            cameraSource?.close()
            cameraSource = null
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.Blue)){
        if(hasCameraPermission){
            // Android view is used to integrate native android views in compose UI
            AndroidView(
                factory = { context ->
                    SurfaceView(context).apply {
                        // Initialize camera source when SurfaceView is created
                        if(cameraSource==null){
                            openCamera(context,scope, this, correct_label) { source, detector ->
                                cameraSource = source
                                poseDetector = detector
                            }
                        }
                    }
                },
                // zIndex is the screen overlap parameter, 1f is above 0f and 2f is above 1f
                modifier = Modifier.fillMaxSize().zIndex(0f)
            )
        }

        if (isVisible) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.7f))
                    .zIndex(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(text = text, fontSize = 35.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }
        }

        Column(modifier = Modifier.fillMaxWidth().padding(25.dp).zIndex(2f), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Spacer(modifier = Modifier.size(50.dp))

            Text(text = exerciseData ?: "Exercise", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 22.sp)

            Spacer(modifier = Modifier.size(10.dp))

            Text(text = timerText, color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
        }

        // Bottom controls
        Column(modifier = Modifier.fillMaxSize().padding(25.dp).zIndex(2f), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom) {
            Text(text = "0", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 62.sp)

            Spacer(modifier = Modifier.size(25.dp))


            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                IconButton(
                    onClick = { trigger = true },
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

fun openCamera(
    context: Context,
    scope: CoroutineScope,
    surfaceView: SurfaceView,
    correctLabel: String,
    onInitialized: (CameraSource, PoseDetector) -> Unit
) {
    val poseDetector = MoveNet.create(context, Device.CPU, ModelType.Thunder)

    val cameraSource = CameraSource(surfaceView, object : CameraSource.CameraSourceListener {
        override fun onFPSListener(fps: Int) {}

        override fun onDetectedInfo(
            personScore: Float?,
            poseLabels: List<Pair<String, Float>>?
        ) {
            poseLabels?.sortedByDescending { it.second }?.let { labels ->
                for (label in labels) {
                    if (correctLabel == label.first && label.second >= 0.85) {
                        // Update UI to green
                    } else {
                        // Update UI to red
                    }
                }
            }
        }
    }).apply {
        prepareCamera()
//        setDetector(poseDetector)
//        setClassifier(PoseClassifier.create(context))
    }

    scope.launch(Dispatchers.Main) {
        cameraSource.initCamera()
        onInitialized(cameraSource, poseDetector)
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CameraScreenPreview(){
    CameraScreen(rememberNavController(), "")
}