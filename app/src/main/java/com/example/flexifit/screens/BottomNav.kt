package com.example.flexifit.screens

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.flexifit.R
import com.example.flexifit.data.models.BottomNavItem
import com.example.flexifit.navigation.Routes
import com.example.flexifit.utils.sharedPref
import com.google.gson.Gson

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNav(navController: NavHostController) {
    val navController1 = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    // Get current route
    val currentRoute = navController1.currentBackStackEntryAsState().value?.destination?.route
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .nestedScroll(scrollBehavior.nestedScrollConnection),
             topBar = {
                 CustomAppBar(scrollBehavior = scrollBehavior,
                              currentRoute = currentRoute,
                              navController = navController)
             },
             bottomBar = { MyBottomBar(navController1) }
    ) { innerPadding->
        NavHost(
            navController = navController1,
            startDestination = Routes.Diet.routes,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(route = Routes.Diet.routes){
                DietScreen(navController)
            }

            composable(route = Routes.Gym.routes){
                GymScreen(navController)
            }

            composable(route = Routes.Yoga.routes){
                YogaScreen(navController)
            }

//            composable(Routes.Profile.routes){
//                Profile(navController)
//            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    currentRoute: String?,
    navController: NavHostController
) {
    val context = LocalContext.current
    val title = when (currentRoute) {
        Routes.Diet.routes -> "Choose Your Diet Plan"
        Routes.Gym.routes -> "Gym Workouts"
        Routes.Yoga.routes -> "Yoga Sessions"
        else -> "FlexiFit"
    }

    // Show back button if not on home screen
    val showBackButton = currentRoute != Routes.Diet.routes

    val navigationIcon: @Composable (() -> Unit) = {
        if (showBackButton) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back",
                )
            }
        }
    }

    val actions: @Composable RowScope.() -> Unit = {
        IconButton(onClick = {
            val user = sharedPref.getUserProfile(context)
            val json = Gson().toJson(user)
            val encodedJson = Uri.encode(json)
            val route = Routes.UserAccount.routes.replace("{userData}", encodedJson)
            navController.navigate(route)
        }) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile",
                modifier = Modifier.size(33.dp)
            )
        }
    }

//    if(currentRoute==Routes.Gym.routes){
//        TopAppBar(
//            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
//            title = {
//                Text(
//                    text = title,
//                    fontSize = 20.sp,
//                    fontWeight = FontWeight.Medium
//                )
//            },
//            navigationIcon = navigationIcon,
//            actions = actions,
//            scrollBehavior = scrollBehavior
//        )
//    }
//    else{
        LargeTopAppBar(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            title = {
                Text(
                    text = title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = navigationIcon,
            actions = actions,
            scrollBehavior = scrollBehavior
        )
//    }
}

// TODO: TEST OUT THE TOP APP BAR WITH LOGO
//val scrollFraction = scrollBehavior.state.collapsedFraction
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(MaterialTheme.colorScheme.primary)
//    ) {
//AnimatedVisibility(visible = scrollFraction < 0.5f) {
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(150.dp),
//        contentAlignment = Alignment.Center
//    ) {
//        Image(
//            painter = painterResource(R.drawable.flexifit_txt), // replace with your image
//            contentDescription = "Header Text Img",
//            modifier = Modifier.size(100.dp)
//        )
//    }
//}

@Composable
fun MyBottomBar(navController1: NavController){
    val backStackEntry = navController1.currentBackStackEntryAsState()
    val list = listOf(
        BottomNavItem(
            R.drawable.diet_ic,
            Routes.Diet.routes,
            "Diet"
        ),
        BottomNavItem(
            R.drawable.gym_ic,
            Routes.Gym.routes,
            "Gym"
        ),
        BottomNavItem(
            R.drawable.yoga_ic,
            Routes.Yoga.routes,
            "Yoga"
        )
//        BottomNavItem(
//            Icons.Rounded.Notifications,
//            Routes.Notifications.routes,
//            "Notification"
//        ),
//        BottomNavItem(
//            Icons.Rounded.AccountCircle,
//            Routes.Profile.routes,
//            "Profile"
//        )
    )

    BottomAppBar {
        list.forEach{
            val selected = it.route == backStackEntry.value?.destination?.route
            NavigationBarItem(selected = selected, onClick = {
                navController1.navigate(it.route){
                    popUpTo(navController1.graph.findStartDestination().id){
                        saveState = true
                    }
                    launchSingleTop = true
                }
            }, icon = { Icon(painter = painterResource(it.icon), contentDescription = it.title, modifier = Modifier.size(25.dp))},
                label = { Text(it.title) }
            )
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun bbscreenPrev(){
//    BottomNav(rememberNavController())
//}