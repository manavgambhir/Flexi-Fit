package com.example.flexifit.itemView

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.flexifit.LoginState
import com.example.flexifit.R
import com.example.flexifit.data.models.Data
import com.example.flexifit.data.models.ExerciseData
import com.example.flexifit.navigation.Routes
import com.example.flexifit.utils.sharedPref
import com.google.gson.Gson

@Composable
fun ExerciseItem(context:Context, navController: NavHostController, exercise:Data) {
    val exUrl = exercise.video_tutorials[0]
    val videoId = exUrl.substringAfterLast("/")
    val thumbnailUrl = "https://img.youtube.com/vi/$videoId/0.jpg"

    Card(modifier = Modifier.fillMaxWidth().padding(10.dp)
        .clickable {
            val exerciseData = ExerciseData(data = exercise, thumbnailUrl = thumbnailUrl)
            val json = Gson().toJson(exerciseData)
            val encodeJson = Uri.encode(json)
            val route = Routes.ExerciseDetailGym.routes.replace("{exData}",encodeJson)
            navController.navigate(route)
        },
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFe6faff)).padding(12.dp)) {
            Box(modifier = Modifier.fillMaxSize()
                .clip(RoundedCornerShape(6.dp))
                .height(220.dp)
                .background(Color.White)
//                .clickable {
//                    exUrl.let {
//                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
//                        context.startActivity(intent)
//                    }
//                }
            ){
                val painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(context)
                        .data(thumbnailUrl)
                        .crossfade(true)
                        .placeholder(R.drawable.flexifit_ic)
                        .error(R.drawable.flexifit_ic)
                        .build()
                )

                Image(
                    painter = painter,
                    contentDescription = "Exercise Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

//                if(painter.state is AsyncImagePainter.State.Success){
//                    Icon(
//                        imageVector = Icons.Rounded.PlayArrow,
//                        contentDescription = "Video Play",
//                        modifier = Modifier.size(54.dp).clip(RoundedCornerShape(40.dp)).background(Color.White.copy(alpha = 0.8f)).padding(6.dp),
//                        tint = Color.DarkGray
//                    )
//                }
            }

            Text(text = exercise.title,
                color = Color.Black,
                fontFamily = FontFamily.Default,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                maxLines = 2,
                overflow = TextOverflow.Clip
            )

            Text(text = exercise.difficulty,
                color = Color.DarkGray,
                fontFamily = FontFamily.Default,
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp),
                maxLines = 2,
                overflow = TextOverflow.Clip
            )

            if(!exercise.file_name.isNullOrEmpty()){
                Spacer(modifier = Modifier.size(10.dp))

                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                    OutlinedButton(onClick = {
                        navController.navigate(Routes.Camera.routes)
                    }, border = BorderStroke(1.dp,Color.Black), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF80e5ff))) {
                        Text(text = "Perform it", color = Color.Black, fontSize = 18.sp, modifier = Modifier.padding(horizontal = 25.dp))
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ExerciseItemPreview() {
//    ExerciseItem(LocalContext.current,"Push Ups","Intermediate","https://img.youtube.com/vi/YaXPRqUwItQ/0.jpg")
}