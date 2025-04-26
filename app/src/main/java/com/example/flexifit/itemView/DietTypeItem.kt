package com.example.flexifit.itemView

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flexifit.data.models.DietType

//Gluten Free
//Keto-Friendly
//Vegan
//Vegetarian
//Peanut Free
//Fish Free
//Sugar Free
//Dairy Free
//Egg Free
//Immuno-Supportive
//Kidney Friendly
//Low Sugar
//Mustard Free
//No Oil Added
//Low Fat Abs Diet
//Shell

@Composable
fun DietTypeItem(dietType: DietType, onClick: () -> Unit){
    Card(modifier = Modifier.padding(8.dp)
        .width(180.dp)
        .height(220.dp).clickable { onClick() },
         elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            Image(painter = painterResource(dietType.imageRes),
                  contentDescription = "Diet Type",
                  contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.9f)
                            ),
                            startY = 450f // controls where the dark part starts
                        )
                    )
            )

            Text(text = dietType.name,
                 color = Color.White,
                 fontFamily = FontFamily.Default,
                 fontSize = 22.sp,
                 fontWeight = FontWeight.Bold,
                 modifier = Modifier.align(Alignment.BottomCenter)
                     .fillMaxWidth()
                     .padding(10.dp),
                 textAlign = TextAlign.Center,
                 maxLines = 2,
                 overflow = TextOverflow.Clip
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DietTypePreview(){
//    DietTypeItem()
}