package com.example.flexifit.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.flexifit.data.models.BottomNavItem
import com.example.flexifit.navigation.Routes

@Composable
fun BottomNav(navController: NavHostController) {
    val navController1 = rememberNavController()
    Scaffold(bottomBar = {
        MyBottomBar(navController1)
    }) { innerPadding->
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