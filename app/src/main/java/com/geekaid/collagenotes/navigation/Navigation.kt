package com.geekaid.collagenotes.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.geekaid.collagenotes.ui.auth.SignInScreen
import com.geekaid.collagenotes.ui.auth.SignUpScreen
import com.geekaid.collagenotes.ui.screens.DashboardScreen
import com.geekaid.collagenotes.ui.screens.DownloadedNoteScreen
import com.geekaid.collagenotes.ui.screens.FilterScreen
import com.geekaid.collagenotes.ui.screens.UploadScreen

@Composable
fun Navigation(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Screens.SignUpNav.route) {

        composable(Screens.DashboardNav.route) {
            DashboardScreen()
        }
        composable(Screens.FilterNav.route) {
            FilterScreen()
        }
        composable(Screens.DownloadedScreenNav.route) {
            DownloadedNoteScreen()
        }
        composable(Screens.UploadScreenNav.route) {
            UploadScreen(navController = navController)
        }
        composable(Screens.SignInNav.route){
            SignInScreen(navController = navController)
        }
        composable(Screens.SignUpNav.route) {
            SignUpScreen(navController = navController)
        }

    }
}