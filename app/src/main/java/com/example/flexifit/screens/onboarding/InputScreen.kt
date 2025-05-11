package com.example.flexifit.screens.onboarding

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.flexifit.R
import com.example.flexifit.navigation.Routes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputScreen(
    title:String,
    value:String,
    hint:String?="",
    onValueChange:(String)->Unit,
    onNextClick:()->Unit,
    keyboardType: KeyboardType,
    navController: NavHostController
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val showBackButton = currentRoute != Routes.OnboardName.routes
    // This base composable will be implemented by 1) Age Screen 2) Height Screen 3) Weight Screen 4) Any injury Screen
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState){ data->
                Snackbar(
                    snackbarData = data,
                    containerColor = Color.Red,
                    contentColor = Color.White
                )
            }
        }
    ) { innerPadding->
        Column(modifier = Modifier.fillMaxSize().padding(
            PaddingValues(
                start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                bottom = innerPadding.calculateBottomPadding()
            )
        )) {
            OnboardingTopBar(title,showBackButton,navController)

            TextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = { Text("Enter your $title") },
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 10.dp, start = 20.dp, end = 20.dp)
                    .border(1.dp, Color.Gray, shape = MaterialTheme.shapes.small)
            )

            if (hint != null) {
                Text(text = hint, modifier = Modifier.padding(start = 25.dp))
            }

            Spacer(modifier = Modifier.height(20.dp))

            ElevatedButton(onClick = {
                if(value.isNotBlank()){
                    onNextClick()
                } else{
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Field cannot be empty")
                    }
                } },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier.fillMaxWidth().padding(10.dp)
            ) {
                Text(text = "Next", fontSize = 16.sp, modifier = Modifier.padding(5.dp))
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingTopBar(title:String, showBackButton: Boolean, navController: NavHostController) {
    TopAppBar(
        title = {},
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "Back"
                    )
                }
            }
        },
        modifier = Modifier.fillMaxWidth()
    )

    Text(text = title, fontSize = 43.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(top = 20.dp, start = 20.dp))
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TextInputScreenPreview(){
//    InputScreen(
//        title = "Title",
//        value = "Value",
//        onValueChange = {},
//        onNextClick = {},
//        keyboardType = KeyboardType.Text,
//        navController = rememberNavController()
//    )
}