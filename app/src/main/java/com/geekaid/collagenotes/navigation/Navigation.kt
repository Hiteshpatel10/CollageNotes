package com.geekaid.collagenotes.navigation

import android.app.DownloadManager
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.geekaid.collagenotes.ui.auth.EmailVerificationScreen
import com.geekaid.collagenotes.ui.auth.ForgotPassword
import com.geekaid.collagenotes.ui.auth.SignInScreen
import com.geekaid.collagenotes.ui.auth.SignUpScreen
import com.geekaid.collagenotes.ui.screens.*
import com.geekaid.collagenotes.viewmodel.DashboardViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun Navigation(
    navController: NavHostController,
    downloadManager: DownloadManager,
    dashboardViewModel: DashboardViewModel
) {

    NavHost(navController = navController, startDestination = Screens.SlashNav.route) {

        composable(Screens.SlashNav.route){
            SplashScreen(navController = navController)
        }

        composable(Screens.DashboardNav.route) {
            DashboardScreen(
                downloadManager = downloadManager,
                dashboardViewModel = dashboardViewModel,
                navController = navController
            )
        }
        composable(Screens.FilterNav.route) {
            FilterScreen(navController = navController, dashboardViewModel = dashboardViewModel)
        }
        composable(Screens.DownloadedScreenNav.route) {
            DownloadedNoteScreen()
        }
        composable(Screens.UploadScreenNav.route) {
            UploadScreen(navController = navController)
        }
        composable(Screens.FavouriteScreenNav.route) {
            FavouriteScreen(
                downloadManager = downloadManager,
                dashboardViewModel = dashboardViewModel,
                navController = navController
            )
        }

        //Authentication Screen Navigation
        composable(Screens.SignInNav.route) {
            SignInScreen(navController = navController)
        }
        composable(Screens.SignUpNav.route) {
            SignUpScreen(navController = navController)
        }
        composable(Screens.ForgotPasswordNav.route) {
            ForgotPassword(navController = navController)
        }
        composable(Screens.EmailVerificationNav.route) {
            EmailVerificationScreen(navController = navController)
        }

    }
}