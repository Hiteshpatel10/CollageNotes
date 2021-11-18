package com.geekaid.collagenotes.components

import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.navigation.Screens
import com.google.firebase.auth.FirebaseAuth

@Composable
fun BottomNav(navController: NavHostController) {

    var alertBoxShow by remember { mutableStateOf(false) }

    BottomAppBar {

        IconButton(onClick = { navController.navigate(Screens.FilterNav.route) }) {
            Icon(Icons.Filled.Search, contentDescription = "Search")
        }

        IconButton(onClick = { navController.navigate(Screens.FavouriteScreenNav.route) }) {
            Icon(Icons.Filled.Favorite, contentDescription = "Favourite List")
        }

        IconButton(onClick = { navController.navigate(Screens.DashboardNav.route) }) {
            Icon(Icons.Filled.Dashboard, contentDescription = "Dashboard")
        }

        IconButton(onClick = { navController.navigate(Screens.UploadScreenNav.route) }) {
            Icon(Icons.Filled.UploadFile, contentDescription = "Downloaded Files")
        }

        IconButton(onClick = {
            alertBoxShow = true
        }) {
            Icon(Icons.Filled.Logout, contentDescription = "Downloaded Files")
            if(alertBoxShow)
                alertBoxShow = signOutAlertDialog(isShow = alertBoxShow, navController = navController)
        }
    }
}