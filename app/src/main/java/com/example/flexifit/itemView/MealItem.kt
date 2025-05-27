package com.example.flexifit.itemView

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.flexifit.R

@Composable
fun MealItem(dishImgUrl: Any, dishName: String, dishQty:String, dishCal:Double, dishDetailUrl:String?) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(5.dp)
            .clickable(enabled = !dishDetailUrl.isNullOrEmpty()) {
                dishDetailUrl?.let {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                    context.startActivity(intent)
                }
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardColors(containerColor = Color.White, contentColor = Color.Black, disabledContentColor = Color.Black, disabledContainerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (image, name, qty, cal) = createRefs()
            val context = LocalContext.current

            Box(
                modifier = Modifier.width(80.dp).fillMaxSize()
                    .constrainAs(image) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                contentAlignment = Alignment.Center
            ){
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(dishImgUrl)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = "meal image",
                    placeholder = painterResource(R.drawable.gluton_free),
                    error = painterResource(R.drawable.gluton_free),
                    modifier = Modifier
                        .padding(9.dp)
                        .fillMaxSize()
                )
            }

            Text(
                text = dishName,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.constrainAs(name) {
                    start.linkTo(image.end, margin = 12.dp)
                    top.linkTo(parent.top, margin = 13.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }
            )

            Text(
                text = if(dishName=="Rice" || dishName=="Chapati") dishQty else "$dishQty g",
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.constrainAs(qty) {
                    start.linkTo(image.end, margin = 12.dp)
                    top.linkTo(name.bottom)
                }
            )

            Text(
                text = "$dishCal cal",
                fontSize = 14.sp,
                modifier = Modifier.constrainAs(cal) {
                    end.linkTo(parent.end, margin = 16.dp)
                    bottom.linkTo(parent.bottom, margin = 8.dp)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DishItemPreview() {
    MealItem("https://cdn.pixabay.com/photo/2016/09/07/10/37/kermit-1651325_1280.jpg", "Rice with smabar in in idli with rasam and with chutney in", "1 Bowl", 136.0, "")
}