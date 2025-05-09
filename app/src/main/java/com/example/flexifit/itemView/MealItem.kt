package com.example.flexifit.itemView

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import com.example.flexifit.R

@Composable
fun MealItem(dishImgUrl: Any, dishName: String, dishQty:String, dishCal:Double, dishDetailUrl:String?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp)
            .padding(5.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardColors(containerColor = Color.White, contentColor = Color.Black, disabledContentColor = Color.Black, disabledContainerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (image, name, qty, cal) = createRefs()
            val context = LocalContext.current

            // TODO: Local image loading, but url from internet not loading
            Box(
                modifier = Modifier.width(80.dp).fillMaxHeight()
                    .constrainAs(image) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                contentAlignment = Alignment.Center
            ){
                val imageRequest = remember(dishImgUrl) {
                    ImageRequest.Builder(context)
                        .data(dishImgUrl)
                        .crossfade(true)
                        .placeholder(R.drawable.gluton_free)
                        .error(R.drawable.gluton_free)
                        .build()
                }

                AsyncImage(
                    model = imageRequest,
                    contentDescription = "meal image",
                    modifier = Modifier.padding(6.dp),
                    placeholder = painterResource(R.drawable.gluton_free)
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
                }
            )

            Text(
                text = dishQty,
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
    MealItem("https://cdn.pixabay.com/photo/2016/09/07/10/37/kermit-1651325_1280.jpg", "Rice", "1 Bowl", 136.0, "")
}