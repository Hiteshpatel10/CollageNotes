package com.geekaid.collagenotes.components

import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.navigation.Screens

@Composable
fun BottomNav(navController: NavHostController) {
    BottomAppBar {

        IconButton(onClick = { navController.navigate(Screens.FilterNav.route) }) {
            Icon(Icons.Filled.Search , contentDescription = "Search" )
        }

        IconButton(onClick = { navController.navigate(Screens.DashboardNav.route) }) {
            Icon(Icons.Filled.Dashboard , contentDescription = "Dashboard" )
        }

        IconButton(onClick = { navController.navigate(Screens.DownloadedScreenNav.route) }) {
            Icon(Icons.Filled.Download, contentDescription = "Downloaded Files" )
        }

        IconButton(onClick = { navController.navigate(Screens.UploadScreenNav.route) }) {
            Icon(Icons.Filled.UploadFile, contentDescription = "Downloaded Files" )
        }
    }
}