package com.geekaid.collagenotes.util

import com.geekaid.collagenotes.model.StringValuePair
import com.geekaid.collagenotes.navigation.BottomNavScreen
import timber.log.Timber

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
        BottomNavScreen.DashboardNav,
        BottomNavScreen.FavouriteScreenNav,
        BottomNavScreen.UploadScreenNav,
    )


    val uploaderType = listOf(
        "Student",
        "Professor"
    )

    val allScreenList = listOf(
        BottomNavScreen.FilterNav,
        BottomNavScreen.FavouriteScreenNav,
        BottomNavScreen.DashboardNav,
        BottomNavScreen.UploadScreenNav,
        BottomNavScreen.UserProfileEditScreenNav,
        BottomNavScreen.UserProfileScreenNav,
        BottomNavScreen.UserProfileCreateNav
    )

    val favSpaces = listOf(
        "fav1",
        "fav2",
        "fav3"
    )

    val filterBy :List<StringValuePair> = listOf(
        StringValuePair("Notes","notes"),
        StringValuePair("Papers","papers"),
        StringValuePair("Assignments","assignments"),
    )

    val orderBy :List<StringValuePair> = listOf(
        StringValuePair("Date","date"),
        StringValuePair("Likes","likes"),
        StringValuePair("Downloaded","downloadedTimes"),
    )

  val orderByBy = listOf(
        "date",
        "papers",
        "assignments"
    )

}


fun getTitle(route: String): String {

    var routeTrim = route
    if(route.lastIndexOf('/') > -1)
        routeTrim = route.substring(0 until route.lastIndexOf('/'))

    Constants.allScreenList.forEach { screen ->
        if (screen.route == routeTrim)
            return screen.title
    }

    return "Collage Notes"
}


