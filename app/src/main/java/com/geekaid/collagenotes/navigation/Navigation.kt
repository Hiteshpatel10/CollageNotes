package com.geekaid.collagenotes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.geekaid.collagenotes.ui.screens.DashboardScreen
import com.geekaid.collagenotes.ui.screens.FilterScreen

@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.FilterNav.route) {

        composable(Screens.DashboardNav.route) {
            DashboardScreen()
        }
        composable(Screens.FilterNav.route) {
            FilterScreen()
        }
    }
}