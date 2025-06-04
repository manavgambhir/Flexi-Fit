package com.example.flexifit.itemView

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.flexifit.R
import com.example.flexifit.data.models.Pose
import com.example.flexifit.navigation.Routes
import com.google.gson.Gson

@Composable
fun YogaItem(context: Context, navController: NavHostController, yogaPose: Pose) {
    Card(modifier = Modifier.fillMaxWidth().height(250.dp).padding(10.dp)
        .clickable {
            val json = Gson().toJson(yogaPose)
            val encodeJson = Uri.encode(json)
            val route = Routes.ExerciseDetailYoga.routes.replace("{yogaPose}",encodeJson)
            navController.navigate(route)
        },
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row (modifier = Modifier.fillMaxSize().background(Color(0xFFfff2e6)).padding(12.dp)) {
            Box(
                modifier = Modifier.fillMaxHeight()
                    .width(170.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(yogaPose.image_url)
                        .crossfade(true)
                        .placeholder(R.drawable.flexifit_ic)
                        .error(R.drawable.flexifit_ic)
                        .build(),
                    contentDescription = "Exercise Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Column(modifier = Modifier.fillMaxHeight().padding(14.dp)) {
                Text(
                    text = "${yogaPose.english_name} Pose",
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

                Text(
                    text = "Sanskrit: ${yogaPose.sanskrit_name}",
                    color = Color.DarkGray,
                    fontFamily = FontFamily.Default,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Clip
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun YogaItemPreview() {
//    YogaItem(LocalContext.current,"Standing Pose with abc in it for test 123 abc","sukhasna","")
}