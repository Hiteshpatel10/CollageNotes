package com.geekaid.collagenotes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.geekaid.collagenotes.ui.screens.DashboardScreen
import com.geekaid.collagenotes.ui.screens.DownloadedNoteScreen
import com.geekaid.collagenotes.ui.screens.FilterScreen

@Composable
fun Navigation(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Screens.FilterNav.route) {

        composable(Screens.DashboardNav.route) {
            DashboardScreen()
        }
        composable(Screens.FilterNav.route) {
            FilterScreen()
        }

        composable(Screens.DownloadedScreenNav.route) {
            DownloadedNoteScreen()
        }

    }
}