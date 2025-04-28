package com.example.flexifit.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.flexifit.R
import com.example.flexifit.data.models.MealPlanData
import com.example.flexifit.itemView.MealItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealPlanScreen(navController: NavHostController, mealPlanData: MealPlanData) {
    val context = LocalContext.current
    BackHandler {
        // Do nothing here, prevent default back navigation
        Toast.makeText(context, "Press the regenerate button to regenerate meal plan, or press back button to move to previous screen",Toast.LENGTH_SHORT).show()
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        TopAppBar(title = { Text(text = "Full Day Meal Plan",
                                 fontSize = 24.sp,
                                 fontWeight = FontWeight.Bold
        )}, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
        } }, actions = {
            IconButton(onClick = {
                // TODO: Call API again to regenerate plan
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Back"
                )
            }
        })

        MealPlanContent(mealPlanData)

    }
}

@Composable
fun MealPlanContent(mealPlanData: MealPlanData) {
    var bfastCheck by remember { mutableStateOf(false) }
    var lunchCheck by remember { mutableStateOf(false) }
    var dinnerCheck by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        item {
            MealCard(
                title = "BREAKFAST",
                description = "There's nothing like starting the day with a healthy, filling breakfast",
                isRice = mealPlanData.bfastRice,
                chapatiCount = mealPlanData.bfastChapati,
                isChecked = bfastCheck,
                onCheckChanged = { bfastCheck = it }
            )
        }

        item { Spacer(modifier = Modifier.height(20.dp)) }

        item {
            MealCard(
                title = "LUNCH",
                description = "Lunch, the sacred middle ground between morning hustle and afternoon grind.",
                isRice = mealPlanData.lunchRice,
                chapatiCount = mealPlanData.lunchChapati,
                isChecked = lunchCheck,
                onCheckChanged = { lunchCheck = it }
            )
        }

        item { Spacer(modifier = Modifier.height(20.dp)) }

        item {
            MealCard(
                title = "DINNER",
                description = "The best memories are made around the dinner table",
                isRice = mealPlanData.dinnerRice,
                chapatiCount = mealPlanData.dinnerChapati,
                isChecked = dinnerCheck,
                onCheckChanged = { dinnerCheck = it }
            )
        }
    }
}

@Composable
fun MealCard(
    title: String,
    description: String,
    isRice: Boolean,
    chapatiCount: Int,
    isChecked: Boolean,
    onCheckChanged: (Boolean) -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth(), border = BorderStroke(2.dp, Color.Black)) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Checkbox(
                    modifier = Modifier.size(22.dp),
                    checked = isChecked,
                    onCheckedChange = { onCheckChanged(!isChecked) }
                )

                Spacer(modifier = Modifier.size(12.dp))

                Text(text = title, fontSize = 21.sp, fontWeight = FontWeight.Bold)

                Text(text = "600.0 cal", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End, color = Color.Black)
            }

            Spacer(modifier = Modifier.size(10.dp))

            Text(
                text = description,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.size(8.dp))

            // TODO: API Call Item
            MealItem(getUrl(R.drawable.rice),"API Call","100.0 g",200.0,"")

            if(isRice){
                MealItem(getUrl(R.drawable.rice),"Rice","1 Bowl",136.0,"")
            }

            if(chapatiCount>0){
                MealItem(getUrl(R.drawable.roti),"Chapati","$chapatiCount Serving",104.0,"")
            }
        }
    }
}

private fun getUrl(resourceId: Int): String {
    return Uri.parse(
        "android.resource://" + R::class.java.getPackage().getName() + "/" + resourceId
    ).toString()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MealPlanScreenPreview() {
    val novHostController = rememberNavController()
    MealPlanScreen(novHostController, MealPlanData(1,true,1,true,1,true))
}