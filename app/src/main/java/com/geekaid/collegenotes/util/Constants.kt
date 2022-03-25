package com.geekaid.collegenotes.util

import com.geekaid.collegenotes.model.StringValuePair
import com.geekaid.collegenotes.navigation.BottomNavScreen

object Constants {

    val rulesList = listOf(
        "Select the file you want to upload",
        "Upload the file with appropriate name",
        "Select the course, branch and subject of notes uploaded",
        "Provide a appropriate description for the file uploaded",
        "you can only upload notes in .pdf format",
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

    val filterBy = listOf(
        "Notes", "Papers", "Assignments", "Lab Record"
//        StringValuePair("Notes","Notes"),
//        StringValuePair("Papers","Papers"),
//        StringValuePair("Assignments","Assignments"),
//        StringValuePair("Lab Record","Lab Record"),
    )


    val orderBy: List<StringValuePair> = listOf(
        StringValuePair("Date", "date"),
        StringValuePair("Likes", "likes"),
        StringValuePair("Downloaded", "downloadedTimes"),
    )

    val orderByBy = listOf(
        "date",
        "papers",
        "assignments"
    )

}


fun getTitle(route: String): String {

    var routeTrim = route
    if (route.lastIndexOf('/') > -1)
        routeTrim = route.substring(0 until route.lastIndexOf('/'))

    Constants.allScreenList.forEach { screen ->
        if (screen.route == routeTrim)
            return screen.title
    }

    return "Collage Notes"
}


