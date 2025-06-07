package com.example.flexifit.screens

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.example.flexifit.R
import com.example.flexifit.data.models.MealModel
import com.example.flexifit.data.models.MealPlanData
import com.example.flexifit.data.models.Recipe
import com.example.flexifit.itemView.MealItem
import com.example.flexifit.navigation.Routes
import com.example.flexifit.viewmodels.MealViewModel
import java.util.Locale
import java.util.Random
import kotlin.math.roundToInt
import kotlin.time.times

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealPlanScreen(navController: NavHostController, mealPlanData: MealPlanData) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    val mealViewModel = remember { MealViewModel() }
    val breakfastResult by mealViewModel.breakfastResult.collectAsState()
    val lunchResult by mealViewModel.lunchResult.collectAsState()
    val dinnerResult by mealViewModel.dinnerResult.collectAsState()
    var bfastCal by remember { mutableDoubleStateOf(0.0) }
    var lunchCal by remember { mutableDoubleStateOf(0.0) }
    var dinnerCal by remember { mutableDoubleStateOf(0.0) }

    LaunchedEffect(Unit){
        val health = mealPlanData.health
        val bfastIg = mealPlanData.bfastIngredient
        val lunchIg = mealPlanData.lunchIngredient
        val dinnerIg = mealPlanData.dinnerIngredient
        val cal = mealPlanData.calories

        // Dividing in the ratio of 3:4:3
        bfastCal = ((cal * 0.3).roundToInt() * 100.0 / 100.0)
        lunchCal = ((cal * 0.4).roundToInt() * 100.0 / 100.0)
        dinnerCal = ((cal * 0.3).roundToInt() * 100.0 / 100.0)

        mealViewModel.getMealPlan(bfastIg,health,"breakfast",bfastCal.toString())
        mealViewModel.getMealPlan(lunchIg,health,"lunch",lunchCal.toString())
        mealViewModel.getMealPlan(dinnerIg,health,"dinner",dinnerCal.toString())
    }

    BackHandler {
        // Do nothing here, prevent default back navigation
        Toast.makeText(context, "Press the regenerate button to regenerate meal plan, \nor press back button to move to previous screen",Toast.LENGTH_SHORT).show()
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        TopAppBar(title = { Text(text = "Full Day Meal Plan",
                                 fontSize = 24.sp,
                                 fontWeight = FontWeight.Bold
        )}, navigationIcon = {
            IconButton(onClick = {
                dialogMessage = "Are you sure you want to go back?"
                showDialog = true
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back"
                )
        } }, actions = {
            IconButton(onClick = {
                dialogMessage = "Are you sure you want to regenerate meal plan?"
                showDialog = true
            }) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Back"
                )
            }
        })

        if(breakfastResult == null || lunchResult == null || dinnerResult == null){
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        else{
            MealPlanContent(mealPlanData, breakfastResult!!,bfastCal, lunchResult!!, lunchCal, dinnerResult!!, dinnerCal)
        }
    }

    if (showDialog) {
        ShowAlert(
            message = dialogMessage,
            onDismiss = { showDialog = false },
            onConfirm = {
                showDialog = false
                navController.popBackStack()
            }
        )
    }
}

@Composable
fun ShowAlert(
    message: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Yes")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("No")
            }
        },
        title = { Text("Confirm") },
        text = { Text(message) }
    )
}

@Composable
fun MealPlanContent(
    mealPlanData: MealPlanData,
    breakfastResult: MealModel,
    bfastCal:Double,
    lunchResult: MealModel,
    lunchCal:Double,
    dinnerResult: MealModel,
    dinnerCal:Double
) {
    var bfastCheck by remember { mutableStateOf(false) }
    var lunchCheck by remember { mutableStateOf(false) }
    var dinnerCheck by remember { mutableStateOf(false) }

    val bfastIndex = remember(breakfastResult) {
        if (breakfastResult.count > 0) Random(System.nanoTime()).nextInt(breakfastResult.hits.size) else 0
    }

    val lunchIndex = remember(lunchResult) {
        if (lunchResult.count > 0) Random(System.nanoTime()).nextInt(lunchResult.hits.size) else 0
    }

    val dinnerIndex = remember(dinnerResult) {
        if (dinnerResult.count > 0) Random(System.nanoTime()).nextInt(dinnerResult.hits.size) else 0
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        item {
            MealCard(
                title = "BREAKFAST",
                description = "There's nothing like starting the day with a healthy, filling breakfast",
                result = breakfastResult,
                randomIndex = bfastIndex,
                netCalories = bfastCal,
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
                result = lunchResult,
                randomIndex = lunchIndex,
                netCalories = lunchCal,
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
                result = dinnerResult,
                randomIndex = dinnerIndex,
                netCalories = dinnerCal,
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
    result: MealModel,
    randomIndex:Int,
    netCalories: Double,
    isRice: Boolean,
    chapatiCount: Int,
    isChecked: Boolean,
    onCheckChanged: (Boolean) -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFF63497c)), elevation = CardDefaults.cardElevation(6.dp)) {
        Column(modifier = Modifier.padding(15.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Checkbox(
                    modifier = Modifier.size(22.dp),
                    checked = isChecked,
                    onCheckedChange = { onCheckChanged(!isChecked) },
                    colors = CheckboxDefaults.colors(uncheckedColor = Color.White, checkedColor = Color.White, checkmarkColor = Color.Black)
                )

                Spacer(modifier = Modifier.size(12.dp))

                Text(text = title, fontSize = 21.sp, fontWeight = FontWeight.Bold, color = Color.White)

                val totalCal = netCalories + (if(isRice) 136.0 else 0.0) + (chapatiCount * 104.0)
                Text(text = "$totalCal cal", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End, color = Color.White, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.size(10.dp))

            Text(
                text = description,
                textAlign = TextAlign.Center,
                color = Color.LightGray
            )

            Spacer(modifier = Modifier.size(8.dp))

            // API Call
            if (result.count > 0 && randomIndex in result.hits.indices) {
                val mealRecipe = result.hits[randomIndex].recipe
                // in grams
                val qty = ((mealRecipe.totalWeight / mealRecipe.calories) * netCalories.roundToInt())
                val formattedQty = String.format(Locale.ENGLISH,"%.2f",qty)
                MealItem(mealRecipe.image,mealRecipe.label,formattedQty,netCalories,mealRecipe.url)
            }

            if(isRice){
                MealItem(R.drawable.rice,"Rice","1 Bowl",136.0,"")
            }

            if(chapatiCount>0){
                MealItem(R.drawable.roti,"Chapati","$chapatiCount Serving",104.0,"")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MealPlanScreenPreview() {
//    val novHostController = rememberNavController()
//    MealPlanScreen(novHostController, MealPlanData(1,true,1,true,1,true))
}