package com.geekaid.collagenotes.navigation

import com.geekaid.collagenotes.R

sealed class Screens(val route: String) {

    //SplashScreen
    object SlashNav : Screens(R.string.SplashNav.toString())

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