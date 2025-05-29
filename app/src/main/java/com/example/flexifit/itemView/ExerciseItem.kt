package com.example.flexifit.itemView

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.flexifit.R

@Composable
fun ExerciseItem(context:Context, exTitle:String, difficulty: String, exUrl:String) {
    Card(modifier = Modifier.fillMaxWidth().padding(10.dp)
        .clickable {
            exUrl.let {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                context.startActivity(intent)
            }
        },
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
            Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(4.dp)).height(220.dp).background(Color.White)){
                val videoId = exUrl.substringAfterLast("/")
                val thumbnailUrl = "https://img.youtube.com/vi/$videoId/0.jpg"
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(thumbnailUrl)
                        .crossfade(true)
                        .placeholder(R.drawable.flexifit_ic)
                        .error(R.drawable.flexifit_ic)
                        .build(),
                    contentDescription = "Exercise Image",
                    contentScale = ContentScale.FillBounds
                )
            }

            Text(text = exTitle,
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

            Text(text = difficulty,
                color = Color.DarkGray,
                fontFamily = FontFamily.Default,
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp),
                maxLines = 2,
                overflow = TextOverflow.Clip
            )

//            if(){
//                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
//                    OutlinedButton(modifier = Modifier.padding(top = 10.dp), border = BorderStroke(2.dp,Color.Black),
//                        onClick = {}){
//                        Text(text = "Perform It", color = Color.Black, fontSize = 19.sp)
//                    }
//                }
//            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ExerciseItemPreview() {
//    ExerciseItem("Push Ups","Intermediate","https://img.youtube.com/vi/YaXPRqUwItQ/0.jpg")
}