package com.geekaid.collagenotes.navigation

import com.geekaid.collagenotes.R

sealed class Screens(val route: String) {

    object DashboardNav: Screens(R.string.DashboardNav.toString())
    object FilterNav: Screens(R.string.FilterNav.toString())
    object DownloadedScreenNav: Screens(R.string.DownloadedNotesNav.toString())
    object UploadScreenNav: Screens(R.string.UploadNav.toString())
    object OTPVerifyNav: Screens(R.string.OTPVerifyNav.toString())
    object SignUpNav: Screens(R.string.SignUpNav.toString())
}