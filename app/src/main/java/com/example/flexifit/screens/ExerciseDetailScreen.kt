package com.example.flexifit.screens

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.alpha
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import com.example.flexifit.R
import com.example.flexifit.data.models.Data
import com.example.flexifit.data.models.ExerciseData
import com.example.flexifit.data.models.Pose
import com.example.flexifit.navigation.Routes

// Polymorphism - Overloading
@Composable
fun ExerciseDetailScreen(navController: NavHostController, yoga: Pose){
    CommonExerciseScreen(
        navController = navController,
        title = yoga.english_name,
        subtitle = yoga.sanskrit_name,
        imageUrl = yoga.image_url,
        description = yoga.yoga_description,
        steps = yoga.yoga_instruction,
        tfFile = yoga.file_name.ifBlank { null },
        isYoga = true
    )
}

@Composable
fun ExerciseDetailScreen(navController: NavHostController, exerciseData: ExerciseData){
    val data = exerciseData.data
    val exUrl = exerciseData.thumbnailUrl
    val exerciseSteps = data.text_tutorials.map { it.text }

    data.file_name?.let { Log.d("TFTest", it) }
    CommonExerciseScreen(
        navController = navController,
        title = data.title,
        subtitle = data.difficulty,
        imageUrl = exUrl,
        videoUrl = data.video_tutorials[0],
        steps = exerciseSteps,
        tfFile = data.file_name?.takeIf { it.isNotBlank() },
        isYoga = false
    )
}

@Composable
fun CommonExerciseScreen(
    navController: NavHostController,
    title: String,
    subtitle: String,
    imageUrl: String,
    videoUrl: String? = null,
    description: String? = null,
    steps: List<String>,
    tfFile: String? = null,
    isYoga: Boolean
) {
    val pagerState = rememberPagerState(pageCount = { steps.size })
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    Column(Modifier.statusBarsPadding()) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .background(Color.White)
        ){
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(imageUrl)
                    .placeholder(R.drawable.flexifit_ic)
                    .error(R.drawable.flexifit_ic)
                    .build(),
                modifier = Modifier.fillMaxSize(),
                contentDescription = "Exercise Image",
                contentScale = ContentScale.FillBounds
            )

            if(videoUrl!=null){
                Box(modifier = Modifier.fillMaxSize()
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
                        context.startActivity(intent)
                    },
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        imageVector = Icons.Rounded.PlayArrow,
                        contentDescription = "Video Play",
                        modifier = Modifier.size(54.dp).clip(RoundedCornerShape(40.dp)).background(Color.Black.copy(alpha = 0.8f)).padding(6.dp),
                        tint = Color.White
                    )
                }
            }

            IconButton(onClick = {
                navController.popBackStack()
            }, modifier = Modifier.padding(10.dp)){
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.Black,
                    modifier = Modifier
                        .clip(RoundedCornerShape(40.dp))
//                        .border(1.dp, Color.Black, RoundedCornerShape(40.dp))
                        .background(Color.White.copy(alpha = 0.7f))
                        .padding(6.dp)
                        .size(30.dp),
                )
            }
        }

        Column(modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 12.dp).verticalScroll(scrollState)) {
            Spacer(modifier = Modifier.size(10.dp))

            Text(text = if(isYoga) "$title Pose" else title,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

            Text(text = subtitle, fontSize = 18.sp, color = Color.Gray)
            
            Spacer(modifier = Modifier.size(10.dp))

            if (description != null) {
                Text(text = description,
                    fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.size(20.dp))

            Box(modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.Blue.copy(0.8f))
            ){
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) { it ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(4.dp),
                    ) {
                        Spacer(modifier = Modifier.size(10.dp))

                        Text(text = "Step ${it + 1}", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.White)

                        Spacer(modifier = Modifier.size(10.dp))

                        Text(
                            text = steps[it],
                            fontSize = 15.sp,
                            color = Color.White,
                            maxLines = 8,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.size(10.dp))
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                // repeat internally uses for loop only
                repeat(steps.size) { index ->
                    Text(
                        text = "●",
                        fontSize = 18.sp,
                        fontWeight = if (index == pagerState.currentPage) FontWeight.Bold else FontWeight.Normal,
                        color = if (index == pagerState.currentPage) {
                            if(isSystemInDarkTheme()){
                                Color.White
                            } else{
                                Color.Black
                            }
                        } else {
                            Color.Gray },
                        modifier = Modifier.padding(horizontal = 2.dp)
                    )
                }
            }

            if(!tfFile.isNullOrEmpty()){
                Spacer(modifier = Modifier.size(25.dp))

                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                    Button(onClick = {
                        val route = Routes.Camera.routes.replace("{exerciseData}",title)
                        navController.navigate(route)
                    }, colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                    ){
                        Text(text = "Perform " + if(isYoga) "Asana" else "Exercise",
                            color = Color.White,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(horizontal = 25.dp, vertical = 5.dp)
                        )
                    }
                }
            }

        }
    }
}

// UNCOMMENT FOR TOP BAR
//        TopAppBar(title = {
//            Text(text = exercise.english_name,
//            textAlign = TextAlign.Center,
//            modifier = Modifier.fillMaxWidth(),
//            fontSize = 24.sp,
//            fontWeight = FontWeight.Bold
//        )}, navigationIcon = {
//            IconButton(onClick = {
//                navController.popBackStack()
//            }){
//                Icon(
//                    imageVector = Icons.Default.Close,
//                    contentDescription = "Back"
//                )
//            }
//        })

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailScreenPreview(){
    ExerciseDetailScreen(rememberNavController(),
        Pose("Tree Pose",
            "Pose",
            "This is a yoga pose",
            "",
            "",
            listOf(""),
            "",
            listOf(
                "Stand in Tadasana. Inhale and raise your arms overhead so that your biceps are just slightly in front of your ears. Either keep the arms parallel, palms facing inward, or join the palms.",
                "Exhale and bend your knees so that your thighs are as parallel to the floor as possible. Your knees will project out over your feet, and your trunk will lean slightly forward over your thighs until your front torso forms approximately a right angle with the tops of your thighs.",
                "Keep your inner thighs parallel to each other and press the heads of the thigh bones down toward your heels.",
                "Firm your shoulder blades against your back. Direct your tailbone down toward the floor and in toward your pubis to keep your lower back long.",
                "Stay for 30 seconds to a minute. To come out of this pose, straighten your knees with an inhalation, lifting strongly through your arms. Exhale and release your arms to your sides into Tadasana."
            )
        )
    )
}