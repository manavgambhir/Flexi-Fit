package com.example.flexifit.presentation.onboarding

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.flexifit.R

@Composable
fun SignIn(navController: NavHostController){
    var email by remember {
        mutableStateOf("")
    }
    var pass by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    Column(Modifier.fillMaxSize()){
        Row {
            Text(text = "Sign In", fontSize = 44.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.offset(22.dp,25.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(22.dp)
                .offset(y = (-27).dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(value = email,
                onValueChange = { email = it },
                label = { Text(text = "Enter email address") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(15.dp))

            OutlinedTextField(value = pass,
                onValueChange = { pass = it },
                label = { Text(text = "Enter the password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(30.dp))

            ElevatedButton(onClick = {
                if(email.isEmpty()||pass.isEmpty()){
                    Toast.makeText(context, "Please enter valid details to login", Toast.LENGTH_SHORT).show()
                }
                else{
                    // TODO: If email and pass are provided -> Verify and SignIn
                }
            },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Login", fontSize = 16.sp, modifier = Modifier.padding(5.dp))
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "Or", fontSize = 16.sp)

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(onClick = {

            }, modifier = Modifier.fillMaxWidth()) {
                Image(painter = painterResource(R.drawable.g_ic), contentDescription = "google logo", modifier = Modifier.size(35.dp))
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "Sign-In with Google", color = Color.Blue)
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun EmailView() {
    val navController = rememberNavController()
    SignIn(navController)
}