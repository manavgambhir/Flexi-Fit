package com.example.flexifit.itemView

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.flexifit.R

@Composable
fun YogaItem(context: Context, englishName:String, sanskritName:String, asanaUrl:String) {
    Card(modifier = Modifier.fillMaxWidth().height(250.dp).padding(10.dp)
        .clickable {
            // TODO: Detail Screen
        },
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row (modifier = Modifier.fillMaxSize().padding(12.dp)) {
            Box(
                modifier = Modifier.fillMaxHeight()
                    .width(170.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(asanaUrl)
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
                    text = "$englishName Pose",
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
                    text = "Sanskrit: $sanskritName",
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
    YogaItem(LocalContext.current,"Standing Pose with abc in it for test 123 abc","sukhasna","")
}