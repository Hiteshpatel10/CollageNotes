package com.geekaid.collegenotes.navigation

import android.app.DownloadManager
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.geekaid.collegenotes.ui.auth.EmailVerificationScreen
import com.geekaid.collegenotes.ui.auth.ForgotPassword
import com.geekaid.collegenotes.ui.auth.SignInScreen
import com.geekaid.collegenotes.ui.auth.SignUpScreen
import com.geekaid.collegenotes.ui.screens.*
import com.geekaid.collegenotes.viewmodel.AuthViewModel
import com.geekaid.collegenotes.viewmodel.DashboardViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalFoundationApi
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

        composable(Screens.SplashNav.route) {
            SplashScreen(navController = navController)
        }

        composable(BottomNavScreen.DashboardNav.route) {
            DashboardScreen(
                downloadManager = downloadManager,
                dashboardViewModel = dashboardViewModel,
                navController = navController
            )
        }
        composable(BottomNavScreen.FilterNav.route) {
            FilterScreen(navController = navController, dashboardViewModel = dashboardViewModel)
        }
        composable(Screens.DownloadedScreenNav.route) {
            DownloadedNoteScreen()
        }
        composable(Screens.UploadScreenNav.route) {
            UploadScreen(navController = navController, dashboardViewModel = dashboardViewModel)
        }
        composable(BottomNavScreen.FavouriteScreenNav.route) {
            FavouriteScreen(
                downloadManager = downloadManager,
                dashboardViewModel = dashboardViewModel,
                navController = navController
            )
        }

        composable(BottomNavScreen.UserProfileCreateNav.route) {
            UserProfileCreate(navController = navController)
        }

        composable(
            "${BottomNavScreen.UserProfileScreenNav.route}/{uploaderEmail}", arguments = listOf(
                navArgument("uploaderEmail") { type = NavType.StringType })
        ) { backStackEntry ->
            UserProfileScreen(
                email = backStackEntry.arguments?.getString("uploaderEmail"),
                dashboardViewModel = dashboardViewModel,
                navController = navController
            )
        }

        composable(BottomNavScreen.UserProfileEditScreenNav.route) {
            UserProfileEditScreen(
                dashboardViewModel = dashboardViewModel,
                navController = navController
            )
        }

//        composable(BottomNavScreen.UserSocialMediaLinks.route){
//            UserSocialMediaLinks()
//        }

        composable(BottomNavScreen.AboutNav.route){
            AboutScreen()
        }


        //Authentication Screen Navigation
        composable(Screens.SignInNav.route) {
            SignInScreen(navController = navController, authViewModel = authViewModel)
        }
        composable(Screens.SignUpNav.route) {
            SignUpScreen(navController = navController, authViewModel = authViewModel)
        }
        composable(Screens.ForgotPasswordNav.route) {
            ForgotPassword(navController = navController, authViewModel = authViewModel)
        }
        composable(Screens.EmailVerificationNav.route) {
            EmailVerificationScreen(navController = navController, authViewModel = authViewModel)
        }

    }
}