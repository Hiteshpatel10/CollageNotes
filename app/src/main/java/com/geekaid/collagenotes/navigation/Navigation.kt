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
import com.geekaid.collagenotes.viewmodel.FavouriteViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun Navigation(
    navController: NavHostController,
    downloadManager: DownloadManager,
    dashboardViewModel: DashboardViewModel,
    favouriteViewModel: FavouriteViewModel
) {

    val auth = Firebase.auth

    val startDestination = if (auth.currentUser != null && auth.currentUser!!.isEmailVerified)
        Screens.DashboardNav.route
    else if(auth.currentUser != null && !auth.currentUser!!.isEmailVerified)
        Screens.EmailVerificationNav.route
    else
        Screens.SignInNav.route


    NavHost(navController = navController, startDestination = startDestination) {

        composable(Screens.DashboardNav.route) {
            DashboardScreen(downloadManager = downloadManager, dashboardViewModel)
        }
        composable(Screens.FilterNav.route) {
            FilterScreen(navController = navController)
        }
        composable(Screens.DownloadedScreenNav.route) {
            DownloadedNoteScreen()
        }
        composable(Screens.UploadScreenNav.route) {
            UploadScreen(navController = navController)
        }
        composable(Screens.FavouriteScreenNav.route){
            FavouriteScreen(downloadManager = downloadManager, favouriteViewModel)
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