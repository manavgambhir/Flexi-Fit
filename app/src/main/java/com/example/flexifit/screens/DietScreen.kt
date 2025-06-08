package com.example.flexifit.screens

import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.flexifit.data.models.DietType
import com.example.flexifit.R
import com.example.flexifit.data.models.MealPlanData
import com.example.flexifit.itemView.DietTypeItem
import com.example.flexifit.navigation.Routes
import com.example.flexifit.utils.sharedPref
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.pow

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DietScreen(navController: NavHostController){
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var bfastIngredient by remember { mutableStateOf("") }
    var bfastChapati by remember { mutableIntStateOf(0) }
    var bfastRice by remember { mutableStateOf(false) }
    var lunchIngredient by remember { mutableStateOf("") }
    var lunchChapati by remember { mutableIntStateOf(0) }
    var lunchRice by remember { mutableStateOf(false) }
    var dinnerIngredient by remember { mutableStateOf("") }
    var dinnerChapati by remember { mutableIntStateOf(0) }
    var dinnerRice by remember { mutableStateOf(false) }
    var type by remember { mutableStateOf("") }
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    var bmi by remember { mutableDoubleStateOf(0.0) }
    var calory by remember { mutableDoubleStateOf(0.0) }
    var calories by remember { mutableDoubleStateOf(0.0) }
    var goal by remember { mutableIntStateOf(0) }

    Box(modifier = Modifier.fillMaxSize()) {
        DietTypeList(onItemClick = { clickedType->
            // For API Call, converted to lower case with dash
            val clickTypeFormatted = clickedType.lowercase().replace(" ", "-")
            type = clickTypeFormatted

            when (type) {
                "no-sugar" -> type="sugar-conscious"
            }

            showBottomSheet = true
        })
    }

    if(showBottomSheet){
        // Bottom Sheet
        ModalBottomSheet(
            modifier = Modifier.fillMaxSize(),
            sheetState = sheetState,
            onDismissRequest = { showBottomSheet = false }
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                val userProfile = sharedPref.getUserProfile(context)
                val weight = userProfile?.weight
                val height = userProfile?.height
                if(weight!=null && height!=null){
                    bmi = weight / ((height / 100).pow(2))
                    val age = findAge(userProfile.dob)
                    calory = ((10 * weight.toDouble()) + (6.25 * height.toDouble()) - (5 * age.toDouble()) + 5) * 1.2
                }

                // Progress Indicator
                val normalizedBmi = ((bmi - 10f) / 30f).coerceIn(0.0, 1.0)
                val bmiColor = when {
                    bmi < 18.5 -> Color(0xFFFFD700)
                    bmi < 25 -> Color(0xFF4CAF50)
                    bmi < 30 -> Color(0xFFFFA500)
                    else -> Color(0xFFF44336)
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(36.dp)
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.LightGray)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(fraction = normalizedBmi.toFloat())
                            .background(bmiColor)
                    )

                    val formattedBmi = String.format(Locale.ENGLISH, "%.1f", bmi)
                    Text(
                        text = "BMI: $formattedBmi",
                        modifier = Modifier.align(Alignment.Center),
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.padding(7.dp))

                goal = if(bmi<18.5) 0 else if(bmi>18.5 && bmi<25) 1 else if(bmi>=25 && bmi<30) 2 else 3
                val goalTxt = when(goal){
                    0 -> "gaining your weight"
                    1 -> "maintaining your weight"
                    2 -> "reducing your weight"
                    else -> "your goal"
                }
                Text("You should focus on $goalTxt. You would be suggested recipes for the same.",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(7.dp)
                )
            }

            Spacer(modifier = Modifier.padding(10.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround){
                Text("Ingredient")

                Text("Chapati")

                Text("Rice")
            }

            Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.padding(12.dp)) {
                OutlinedTextField(
                    value = bfastIngredient,
                    onValueChange = { bfastIngredient = it },
                    label = {   Text("Breakfast Ingredient", maxLines = 1 ,overflow = TextOverflow.Ellipsis) },
                    shape = RoundedCornerShape(30.dp),
                    modifier = Modifier
                        .width(140.dp)
                        .padding(start = 6.dp)
                )

                Spacer(modifier = Modifier.padding(12.dp))

                if(bfastChapati==0){
                    TextButton(
                        onClick = {
                            bfastChapati++
                        },
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .background(Color.Black, RoundedCornerShape(8.dp))
                            .width(90.dp)
                            .height(40.dp)
                    ){
                        Text(text = "Add", color = Color.White)
                    }
                }
                else{
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .border(1.dp, if(isSystemInDarkTheme()) Color.White else Color.Black, RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp)
                            .height(40.dp)
                    ) {
                        IconButton(
                            onClick = { bfastChapati-- },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.remove),
                                contentDescription = "Decrease Quantity",
                                modifier = Modifier.size(22.dp),
                                colorFilter = ColorFilter.tint(if (isSystemInDarkTheme()) Color.White else Color.Black)
                            )
                        }

                        Text(
                            text = bfastChapati.toString(),
                            fontSize = 16.sp,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        IconButton(
                            onClick = { bfastChapati++ },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.add),
                                contentDescription = "Increase Quantity",
                                modifier = Modifier.size(22.dp),
                                colorFilter = ColorFilter.tint(if (isSystemInDarkTheme()) Color.White else Color.Black)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.padding(22.dp))

                Checkbox(
                    checked = bfastRice,
                    onCheckedChange = { bfastRice = !bfastRice },
                    colors = CheckboxDefaults.colors(checkedColor = if (isSystemInDarkTheme()) Color.White else Color.Black)
                )

            }

            Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.padding(12.dp)) {
                OutlinedTextField(
                    value = lunchIngredient,
                    onValueChange = { lunchIngredient = it },
                    label = {   Text("Lunch Ingredient", maxLines = 1 ,overflow = TextOverflow.Ellipsis) },
                    shape = RoundedCornerShape(30.dp),
                    modifier = Modifier
                        .width(140.dp)
                        .padding(start = 6.dp)
                )

                Spacer(modifier = Modifier.padding(12.dp))

                if(lunchChapati==0){
                    TextButton(
                        onClick = {
                            lunchChapati++
                        },
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .background(Color.Black, RoundedCornerShape(8.dp))
                            .width(90.dp)
                            .height(40.dp)
                    ){
                        Text(text = "Add", color = Color.White)
                    }
                }
                else{
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .border(1.dp, if(isSystemInDarkTheme()) Color.White else Color.Black, RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp)
                            .height(40.dp)
                    ) {
                        IconButton(
                            onClick = { lunchChapati-- },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.remove),
                                contentDescription = "Decrease Quantity",
                                modifier = Modifier.size(22.dp),
                                colorFilter = ColorFilter.tint(if (isSystemInDarkTheme()) Color.White else Color.Black)
                            )
                        }

                        Text(
                            text = lunchChapati.toString(),
                            fontSize = 16.sp,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        IconButton(
                            onClick = { lunchChapati++ },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.add),
                                contentDescription = "Increase Quantity",
                                modifier = Modifier.size(22.dp),
                                colorFilter = ColorFilter.tint(if (isSystemInDarkTheme()) Color.White else Color.Black)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.padding(22.dp))

                Checkbox(
                    checked = lunchRice,
                    onCheckedChange = { lunchRice = !lunchRice },
                    colors = CheckboxDefaults.colors(checkedColor = if (isSystemInDarkTheme()) Color.White else Color.Black)
                )

            }

            Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.padding(12.dp)) {
                OutlinedTextField(
                    value = dinnerIngredient,
                    onValueChange = { dinnerIngredient = it },
                    label = {   Text("Dinner Ingredient", maxLines = 1 ,overflow = TextOverflow.Ellipsis) },
                    shape = RoundedCornerShape(30.dp),
                    modifier = Modifier
                        .width(140.dp)
                        .padding(start = 6.dp)
                )

                Spacer(modifier = Modifier.padding(12.dp))

                if(dinnerChapati==0){
                    TextButton(
                        onClick = {
                            dinnerChapati++
                        },
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .background(Color.Black, RoundedCornerShape(8.dp))
                            .width(90.dp)
                            .height(40.dp)
                    ){
                        Text(text = "Add", color = Color.White)
                    }
                }
                else{
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .border(1.dp, if(isSystemInDarkTheme()) Color.White else Color.Black, RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp)
                            .height(40.dp)
                    ) {
                        IconButton(
                            onClick = { dinnerChapati-- },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.remove),
                                contentDescription = "Decrease Quantity",
                                modifier = Modifier.size(22.dp),
                                colorFilter = ColorFilter.tint(if (isSystemInDarkTheme()) Color.White else Color.Black)
                            )
                        }

                        Text(
                            text = dinnerChapati.toString(),
                            fontSize = 16.sp,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        IconButton(
                            onClick = { dinnerChapati++ },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.add),
                                contentDescription = "Increase Quantity",
                                modifier = Modifier.size(22.dp),
                                colorFilter = ColorFilter.tint(if (isSystemInDarkTheme()) Color.White else Color.Black)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.padding(22.dp))

                Checkbox(
                    checked = dinnerRice,
                    onCheckedChange = { dinnerRice = !dinnerRice },
                    colors = CheckboxDefaults.colors(checkedColor = if (isSystemInDarkTheme()) Color.White else Color.Black)
                )

            }

            ElevatedButton(
                onClick = {
                    if(bfastIngredient.isNotBlank() || lunchIngredient.isNotBlank() || dinnerIngredient.isNotBlank()){
                        scope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            showBottomSheet = false
                        }

                        val chapatiCal = (bfastChapati+lunchChapati+dinnerChapati)*104

                        var cal = chapatiCal
                        if(bfastRice){
                            cal += 136
                        }
                        if(lunchRice){
                            cal += 136
                        }
                        if(dinnerRice){
                            cal += 136
                        }

                        calories = when(goal){
                            0 -> calory - cal + 300
                            1 -> calory - cal
                            2 -> calory - cal - 400
                            else -> calory - cal
                        }

                        val data = MealPlanData(
                            type,calories,bfastIngredient,bfastChapati, bfastRice, lunchIngredient, lunchChapati, lunchRice, dinnerIngredient, dinnerChapati, dinnerRice
                        )
                        val json = Gson().toJson(data)
                        val encodeJson = Uri.encode(json)

                        val route = Routes.MealPlan.routes.replace("{mealData}",encodeJson)
                        navController.navigate(route)
                    }
                    else{
                        if(bfastIngredient.isBlank() || lunchIngredient.isBlank() || dinnerIngredient.isBlank()){
                            Toast.makeText(context, "Please fill all the ingredients", Toast.LENGTH_SHORT).show()
                        }
                    }
                }, colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp)
            ) {
                Text(text = "Submit", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun findAge(dob: String): Int {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val birthDate = LocalDate.parse(dob, formatter)
    val currentDate = LocalDate.now()
    return Period.between(birthDate, currentDate).years
}

@Composable
fun DietTypeList(onItemClick: (String) -> Unit){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ) {
        items(DietTypeList){ it->
            DietTypeItem(it, onClick = { onItemClick(it.name) })
        }
    }
}

val DietTypeList = listOf(
    DietType(R.drawable.gluton_free, "Gluten Free"),
    DietType(R.drawable.keto, "Keto-Friendly"),
    DietType(R.drawable.vegan, "Vegan"),
    DietType(R.drawable.veg, "Vegetarian"),
    DietType(R.drawable.no_peanut, "Peanut Free"),
    DietType(R.drawable.no_fish, "Fish Free"),
    DietType(R.drawable.no_sugar, "Sugar Free"),
    DietType(R.drawable.dairy_free, "Dairy Free"),
    DietType(R.drawable.no_egg, "Egg Free"),
    DietType(R.drawable.immuno, "Immuno-Supportive"),
    DietType(R.drawable.kidney, "Kidney Friendly"),
    DietType(R.drawable.low_sugar, "Low Sugar"),
    DietType(R.drawable.mustard, "Mustard Free"),
    DietType(R.drawable.no_oil, "No Oil Added"),
)

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DietScreenPreview(){
    DietScreen(rememberNavController())
}