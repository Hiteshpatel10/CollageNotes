package com.geekaid.collegenotes.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.geekaid.collegenotes.components.FileSelectComponent
import com.geekaid.collegenotes.components.FileUploadComponent
import com.geekaid.collegenotes.model.UploaderDetailModel
import com.geekaid.collegenotes.navigation.BottomNavScreen
import com.geekaid.collegenotes.viewmodel.DashboardViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun UploadScreen(navController: NavHostController, dashboardViewModel: DashboardViewModel) {

    val context = LocalContext.current
    var bool by remember { mutableStateOf(true) }
    var noteUri by remember { mutableStateOf("") }
    val userDetails by remember { mutableStateOf(dashboardViewModel.userDetails) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        bool = false
        if (uri == null)
            navController.navigate(BottomNavScreen.UploadScreenNav.route)
        noteUri = uri.toString()
    }

    when {

        dashboardViewModel.userDetails.value == UploaderDetailModel() || dashboardViewModel.userDetails.value == null ->
            UserProfileCreate(navController = navController)

        bool -> FileSelectComponent(launcher = launcher)

        else -> {
            FileUploadComponent(
                noteUri = noteUri,
                userDetails = userDetails.value!!,
                context = context,
                dashboardViewModel = dashboardViewModel,
                navController = navController
            )
        }
    }

}