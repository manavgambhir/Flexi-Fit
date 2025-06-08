package com.example.flexifit.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.flexifit.LoginState
import com.example.flexifit.R
import com.example.flexifit.data.models.UserProfile
import com.example.flexifit.navigation.Routes
import com.example.flexifit.utils.sharedPref
import com.example.flexifit.viewmodels.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import java.util.Calendar
import java.util.Locale
import kotlin.math.pow

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UserAccountScreen(navController: NavHostController, userDetails: UserProfile){
    val scrollState = rememberScrollState()
    val authVM= remember { AuthViewModel() }
    val loginState by authVM.loginState.collectAsState()
    var bmi by remember { mutableDoubleStateOf(0.0) }
    var goal by remember { mutableIntStateOf(0) }
    var calory by remember { mutableDoubleStateOf(0.0) }
    var calories by remember { mutableDoubleStateOf(0.0) }
    val age by remember { mutableIntStateOf(findAge(userDetails.dob)) }

    val context = LocalContext.current
    val weight = userDetails.weight
    val height = userDetails.height
    bmi = weight / ((height / 100).pow(2))

    LaunchedEffect(loginState) {
        if (loginState is LoginState.Idle && FirebaseAuth.getInstance().currentUser == null) {
            navController.navigate(Routes.SignIn.routes) {
                popUpTo(Routes.UserAccount.routes) { inclusive = true }
            }
        }
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .systemBarsPadding()) {
        // TOP BAR
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(62.dp), contentAlignment = Alignment.CenterStart){
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back"
                )
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(62.dp), contentAlignment = Alignment.Center){
                Text(text = "Profile",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Box(modifier = Modifier
                .fillMaxWidth()
                .height(62.dp)
                .padding(horizontal = 10.dp), contentAlignment = Alignment.CenterEnd){
                Box(modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .border(BorderStroke(1.dp, Color.Black), RoundedCornerShape(10.dp)).background(Color.White)
                ){
                    Text(text = "1🔥",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        modifier = Modifier.padding(7.dp)
                    )
                }
            }
        }

        val user = FirebaseAuth.getInstance().currentUser
        val creationTimestamp = user?.metadata?.creationTimestamp
        val year = creationTimestamp?.let {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = it
            calendar.get(Calendar.YEAR)
        }

        Column(modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.size(20.dp))

            Box(modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(140.dp))
                .background(Color.Black)){

                AsyncImage(
                    model = if(userDetails.gender=="Male") R.drawable.profile_male else R.drawable.profile_female,
                    contentDescription = "Profile Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Text(text = userDetails.name, fontSize = 25.sp, modifier = Modifier.padding(10.dp), fontWeight = FontWeight.Bold)

            Text(text = "Joined in $year", fontSize = 19.sp, color = Color.Gray)

            Spacer(modifier = Modifier.size(5.dp))

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp))
            {
                // Box 1 - Age Wt Ht
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF99ddff))
                    .border(
                        BorderStroke(1.dp, Color.Black), RoundedCornerShape(16.dp)
                    )
                ){
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround){
                        // Age
                        Column(modifier = Modifier.fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally , verticalArrangement = Arrangement.Center) {
                            Image(
                                painter = painterResource(R.drawable.age_ic),
                                contentDescription = "Age",
                                modifier = Modifier.size(30.dp)
                            )

                            Text(text = "Age", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
                            Text(text = "$age Years", fontSize = 16.sp, color = Color.Black)
                        }

                        // Weight
                        Column(modifier = Modifier.fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally , verticalArrangement = Arrangement.Center) {
                            Image(
                                painter = painterResource(R.drawable.weight_ic),
                                contentDescription = "Weight",
                                modifier = Modifier.size(30.dp)
                            )

                            Text(text = "Weight", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)

                            Text(text = "${weight.toInt()} Kg", fontSize = 16.sp, color = Color.Black)
                        }

                        // Height
                        Column(modifier = Modifier.fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally , verticalArrangement = Arrangement.Center) {
                            Image(
                                painter = painterResource(R.drawable.height_ic),
                                contentDescription = "Height",
                                modifier = Modifier.size(30.dp)
                            )

                            Text(text = "Height", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)

                            Text(text = "${height.toInt()} cm", fontSize = 16.sp, color = Color.Black)
                        }
                    }
                }

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp), horizontalArrangement = Arrangement.spacedBy(10.dp))
                {
                    // BMI Box
                    Box(modifier = Modifier
                        .size(120.dp)
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFfef9e7))
                        .border(
                            BorderStroke(1.dp, Color.Black), RoundedCornerShape(16.dp)
                        )
                    ){
                        val normalizedBmi = ((bmi - 10f) / 30f).coerceIn(0.0, 1.0)
                        val bmiColor = when {
                            bmi < 18.5 -> Color(0xFFFFD700)
                            bmi < 25 -> Color(0xFF4CAF50)
                            bmi < 30 -> Color(0xFFFFA500)
                            else -> Color(0xFFF44336)
                        }

                        Column(modifier = Modifier.fillMaxWidth()) {
                            Spacer(modifier = Modifier.size(10.dp))

                            Text(text = "BMI", modifier = Modifier.fillMaxWidth().padding(15.dp),
                                textAlign = TextAlign.Center, fontSize = 22.sp, fontWeight = FontWeight.Medium, color = Color.Black
                            )

                            // BMI
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

                                val formattedBmi = String.format(Locale.ENGLISH, "%.2f", bmi)
                                Text(
                                    text = formattedBmi,
                                    modifier = Modifier.align(Alignment.Center),
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            }

                        }
                    }

                    // Daily Calory box
                    Box(modifier = Modifier
                        .size(120.dp)
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFfdedec))
                        .border(
                            BorderStroke(1.dp, Color.Black), RoundedCornerShape(16.dp)
                        )
                    ){
                        // Calories to consume everyday
                        goal = if(bmi<18.5) 0 else if(bmi>18.5 && bmi<25) 1 else if(bmi>=25 && bmi<30) 2 else 3
                        val goalTxt = when(goal){
                            0 -> "gaining"
                            1 -> "maintaining"
                            2 -> "reducing"
                            else -> "your goal"
                        }

                        calory = ((10 * weight) + (6.25 * height) - (5 * age.toDouble()) + 5) * 1.2

                        calories = when(goal){
                            0 -> calory + 300
                            1 -> calory
                            2 -> calory - 400
                            else -> calory
                        }

                        Text(text = buildAnnotatedString {
                            append("You should focus on $goalTxt your weight, you should consume\n")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                                append("${calories.toInt()} calories daily")
                            }
                        },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            color = Color.Black,
                            overflow = TextOverflow.Clip ,
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,lineHeight = 18.sp
                        )
                    }
                }

            }

            // History Btn
            Row(modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    Toast
                        .makeText(
                            context,
                            "History: This feature is under development",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
                .padding(horizontal = 15.dp, vertical = 10.dp)

            ){
                Text(text = "History", fontSize = 21.sp, modifier = Modifier.weight(1f))

                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back",
                    modifier = Modifier.rotate(180f)
                )
            }

            // Calculate Btn
            Row(modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    Toast
                        .makeText(
                            context,
                            "Calculate: This feature is under development",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
                .padding(horizontal = 15.dp, vertical = 10.dp)
            ){
                Text(text = "Calculate", fontSize = 21.sp, modifier = Modifier.weight(1f))

                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back",
                    modifier = Modifier.rotate(180f)
                )
            }

            Spacer(modifier = Modifier.size(10.dp))

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                OutlinedButton(onClick = {
                    authVM.signOut()
                    sharedPref.clearUserProfile(context)
                }, border = BorderStroke(1.dp,Color.Red)) {
                    if(loginState is LoginState.Loading){
                        CircularProgressIndicator(
                            color = Color.Red,
                            modifier = Modifier
                                .size(15.dp),
                            strokeWidth = 2.dp
                        )
                    }
                    else{
                        Text(text = "Sign Out", color = Color.Red, fontSize = 18.sp, modifier = Modifier.padding(horizontal = 25.dp))
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UserProfilePreview(){
    UserAccountScreen(rememberNavController(),
        UserProfile("Manav","11/01/2004","Male",67.0,167.0,"Intermediate")
    )
}