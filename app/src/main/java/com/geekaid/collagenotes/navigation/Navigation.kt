package com.geekaid.collagenotes.navigation

import android.app.DownloadManager
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.geekaid.collagenotes.ui.auth.EmailVerificationScreen
import com.geekaid.collagenotes.ui.auth.ForgotPassword
import com.geekaid.collagenotes.ui.auth.SignInScreen
import com.geekaid.collagenotes.ui.auth.SignUpScreen
import com.geekaid.collagenotes.ui.screens.*
import com.geekaid.collagenotes.viewmodel.AuthViewModel
import com.geekaid.collagenotes.viewmodel.DashboardViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun Navigation(
    navController: NavHostController,
    downloadManager: DownloadManager,
    dashboardViewModel: DashboardViewModel
) {

    val authViewModel: AuthViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screens.SplashNav.route) {

        composable(Screens.SplashNav.route){
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
            UploadScreen(navController = navController, dashboardViewModel = dashboardViewModel)
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
            SignInScreen(navController = navController, authViewModel = authViewModel)
        }
        composable(Screens.SignUpNav.route) {
            SignUpScreen(navController = navController, authViewModel = authViewModel)
        }
        composable(Screens.ForgotPasswordNav.route) {
            ForgotPassword(navController = navController,  authViewModel = authViewModel)
        }
        composable(Screens.EmailVerificationNav.route) {
            EmailVerificationScreen(navController = navController,  authViewModel = authViewModel)
        }

    }
}