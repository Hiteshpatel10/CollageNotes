package com.geekaid.collagenotes

import com.geekaid.collagenotes.navigation.BottomNavScreen

object Constants {

    val rulesList = listOf(
        "Select the file you want to upload",
        "Upload the file with appropriate name",
        "Select the course, branch and subject of notes uploaded",
        "provide a appropriate description for the file uploaded",
        "Do not upload inappropriate content",
    )

    val screen = listOf(
        BottomNavScreen.FilterNav,
        BottomNavScreen.FavouriteScreenNav,
        BottomNavScreen.DashboardNav,
        BottomNavScreen.UploadScreenNav,
    )

}