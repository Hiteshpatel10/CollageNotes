package com.geekaid.collagenotes.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.geekaid.collagenotes.R

sealed class Screens(val route: String) {

    //SplashScreen
    object SplashNav : Screens(R.string.SplashNav.toString())

    //Authentication
    object SignInNav : Screens(R.string.SignInNav.toString())
    object SignUpNav : Screens(R.string.SignUpNav.toString())
    object EmailVerificationNav : Screens(R.string.EmailVerificationNav.toString())
    object ForgotPasswordNav : Screens(R.string.ForgotPasswordNav.toString())

    //MainScreens
    object DashboardNav : Screens(R.string.DashboardNav.toString())
    object FilterNav : Screens(R.string.FilterNav.toString())
    object DownloadedScreenNav : Screens(R.string.DownloadedNotesNav.toString())
    object UploadScreenNav : Screens(R.string.UploadNav.toString())
    object FavouriteScreenNav : Screens(R.string.FavouriteNav.toString())

}

sealed class BottomNavScreen(val route: String, val title: String, val icon: ImageVector) {
    object DashboardNav :
        BottomNavScreen(
            route = R.string.DashboardNav.toString(),
            title = "Notes",
            icon = Icons.Filled.Dashboard
        )

    object FilterNav : BottomNavScreen(
        route = R.string.FilterNav.toString(),
        title = "Filter",
        icon = Icons.Filled.Search
    )

    object UploadScreenNav : BottomNavScreen(
        route = R.string.UploadNav.toString(),
        title = "Upload",
        icon = Icons.Filled.UploadFile
    )

    object FavouriteScreenNav : BottomNavScreen(
        route = R.string.FavouriteNav.toString(),
        title = "Favourites",
        icon = Icons.Filled.Favorite
    )

    object SignOutScreenNav : BottomNavScreen(
        route = "SignOutNav",
        title = "SignOut",
        icon = Icons.Filled.Logout
    )
}
