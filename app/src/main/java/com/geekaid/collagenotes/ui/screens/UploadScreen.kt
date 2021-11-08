package com.geekaid.collagenotes.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.components.FileSelectComponent
import com.geekaid.collagenotes.components.FileUploadComponent
import com.geekaid.collagenotes.model.UserDetails
import com.geekaid.collagenotes.viewmodel.DashboardViewModel

@Composable
fun UploadScreen(navController: NavHostController, dashboardViewModel: DashboardViewModel) {

    val context = LocalContext.current
    var bool by remember { mutableStateOf(true) }
    var noteUri by remember { mutableStateOf("") }
    var userDetails by remember { mutableStateOf(UserDetails()) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        bool = false
        noteUri = uri.toString()
    }

    SideEffect {
        userDetails = dashboardViewModel.getUserDetails()
    }

    if (bool) {
        FileSelectComponent(launcher = launcher)
    } else {
        FileUploadComponent(
            noteUri = noteUri,
            userDetails = userDetails,
            context = context,
            navController = navController
        )
    }

}