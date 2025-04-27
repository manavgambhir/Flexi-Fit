package com.example.flexifit.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.flexifit.data.models.DietType
import com.example.flexifit.R
import com.example.flexifit.itemView.DietTypeItem
import com.example.flexifit.navigation.Routes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
//navController: NavHostController
fun DietScreen(navController: NavHostController){
    lateinit var type:String
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
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
//    val mealViewModel = MealViewModel()
//    LaunchedEffect(Unit){
//        mealViewModel.getMealPlan("spinach","vegetarian","breakfast","100-700")
//    }
//    val itemlist by mealViewModel.mealPlanResult.observeAsState(null)
//    Log.d("DietScreen", "itemlist: $itemlist")

    Box(modifier = Modifier.fillMaxSize()) {
        DietTypeList(onItemClick = {clickedType->
            // For API Call, converted to lower case with dash
            val clickTypeFormatted = clickedType.lowercase().replace(" ", "-")
            type = clickTypeFormatted
            showBottomSheet = true
        })
    }

    if(showBottomSheet){
        ModalBottomSheet(
            modifier = Modifier.fillMaxSize(),
            sheetState = sheetState,
            onDismissRequest = { showBottomSheet = false }
        ) {
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
                    modifier = Modifier.width(140.dp).padding(start = 6.dp)
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
                            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
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
                                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Black)
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
                                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Black)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.padding(22.dp))

                Checkbox(
                    checked = bfastRice,
                    onCheckedChange = { bfastRice = !bfastRice }
                )

            }

            Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.padding(12.dp)) {
                OutlinedTextField(
                    value = lunchIngredient,
                    onValueChange = { lunchIngredient = it },
                    label = {   Text("Lunch Ingredient", maxLines = 1 ,overflow = TextOverflow.Ellipsis) },
                    shape = RoundedCornerShape(30.dp),
                    modifier = Modifier.width(140.dp).padding(start = 6.dp)
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
                            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
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
                                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Black)
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
                                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Black)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.padding(22.dp))

                Checkbox(
                    checked = lunchRice,
                    onCheckedChange = { lunchRice = !lunchRice }
                )

            }

            Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.padding(12.dp)) {
                OutlinedTextField(
                    value = dinnerIngredient,
                    onValueChange = { dinnerIngredient = it },
                    label = {   Text("Dinner Ingredient", maxLines = 1 ,overflow = TextOverflow.Ellipsis) },
                    shape = RoundedCornerShape(30.dp),
                    modifier = Modifier.width(140.dp).padding(start = 6.dp)
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
                            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
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
                                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Black)
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
                                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Black)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.padding(22.dp))

                Checkbox(
                    checked = dinnerRice,
                    onCheckedChange = { dinnerRice = !dinnerRice }
                )

            }

            ElevatedButton(
                onClick = {
                    scope.launch {
                        sheetState.hide()
                    }
                    val route = Routes.MealPlan.routes
                    navController.navigate(route)
                    showBottomSheet = false },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp)
            ) {
                Text(text = "Submit", fontSize = 16.sp)
            }
        }
    }
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
    DietType(R.drawable.gluton_free, "Keto-Friendly"),
    DietType(R.drawable.gluton_free, "Vegan"),
    DietType(R.drawable.gluton_free, "Vegetarian"),
    DietType(R.drawable.gluton_free, "Peanut Free"),
    DietType(R.drawable.gluton_free, "Fish Free"),
    DietType(R.drawable.gluton_free, "Sugar Free"),
    DietType(R.drawable.gluton_free, "Dairy Free"),
    DietType(R.drawable.gluton_free, "Egg Free"),
    DietType(R.drawable.gluton_free, "Immuno-Supportive"),
    DietType(R.drawable.gluton_free, "Kidney Friendly"),
    DietType(R.drawable.gluton_free, "Low Sugar"),
    DietType(R.drawable.gluton_free, "Mustard Free"),
    DietType(R.drawable.gluton_free, "No Oil Added"),
    DietType(R.drawable.gluton_free, "Shellfish Free"),
)

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DietScreenPreview(){
//    DietScreen()
}