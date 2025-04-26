package com.example.flexifit.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.flexifit.data.models.BottomNavItem
import com.example.flexifit.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNav(navController: NavHostController) {
    val navController1 = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    // Get current route
    val currentRoute = navController1.currentBackStackEntryAsState().value?.destination?.route
    Scaffold(modifier = Modifier.fillMaxSize()
        .nestedScroll(scrollBehavior.nestedScrollConnection),
             topBar = {
                 CustomAppBar(scrollBehavior = scrollBehavior,
                              currentRoute = currentRoute,
                              navController = navController1)
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
    val title = when (currentRoute) {
        Routes.Diet.routes -> "Choose Your Diet Plan"
        Routes.Gym.routes -> "Gym Workouts"
        Routes.Yoga.routes -> "Yoga Sessions"
        else -> "FlexiFit"
    }

    // Show back button if not on home screen
    val showBackButton = currentRoute != Routes.Diet.routes

    LargeTopAppBar(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        title = {
            Text(
                text = title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
fun MyBottomBar(navController1: NavController){
    val backStackEntry = navController1.currentBackStackEntryAsState()
    val list = listOf(
        BottomNavItem(
            Icons.Rounded.Menu,
            Routes.Diet.routes,
            "Diet"
        ),
        BottomNavItem(
            Icons.Rounded.Face,
            Routes.Gym.routes,
            "Gym"
        ),
        BottomNavItem(
            Icons.Rounded.AccountBox,
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
            }, icon = { Icon(imageVector = it.icon, contentDescription = it.title)})
        }
    }
}