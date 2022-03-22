package com.geekaid.collegenotes.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.geekaid.collegenotes.R

sealed class Screens(val route: String) {

    //SplashScreen
    object SplashNav : Screens(R.string.SplashNav.toString())

    //Authentication
    object SignInNav : Screens(R.string.SignInNav.toString())
    object SignUpNav : Screens(R.string.SignUpNav.toString())
    object EmailVerificationNav : Screens(R.string.EmailVerificationNav.toString())
    object ForgotPasswordNav : Screens(R.string.ForgotPasswordNav.toString())

    object DownloadedScreenNav : Screens(R.string.DownloadedNotesNav.toString())
    object UploadScreenNav : Screens(R.string.UploadNav.toString())
}

sealed class BottomNavScreen(val route: String, val title: String, val icon: ImageVector) {
    object DashboardNav :
        BottomNavScreen(
            route = R.string.DashboardNav.toString(),
            title = "Dashboard",
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
        title = "Favourite",
        icon = Icons.Filled.Favorite
    )

    object UserProfileScreenNav : BottomNavScreen(
        route = R.string.UserProfileNav.toString(),
        title = "Profile",
        icon = Icons.Filled.Face
    )

    object UserSocialMediaLinks : BottomNavScreen(
        route = R.string.UserSocialMediaLinksNav.toString(),
        title = "Social Media Links",
        icon = Icons.Filled.Facebook
    )

    object UserProfileEditScreenNav : BottomNavScreen(
        route = R.string.UserProfileEditNav.toString(),
        title = "Profile Edit",
        icon = Icons.Filled.Face
    )

    object UserProfileCreateNav : BottomNavScreen(
        route = R.string.UserProfileCreateNav.toString(),
        title = "Profile Create",
        icon = Icons.Filled.Face
    )

    object AboutNav : BottomNavScreen(
        route = R.string.AboutNav.toString(),
        title = "About Us",
        icon = Icons.Filled.AccountCircle
    )

}
