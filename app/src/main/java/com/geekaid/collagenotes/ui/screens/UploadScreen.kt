package com.geekaid.collagenotes.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.components.FileSelectComponent
import com.geekaid.collagenotes.components.FileUploadComponent
import com.geekaid.collagenotes.model.UploaderDetailModel
import com.geekaid.collagenotes.viewmodel.DashboardViewModel
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
        noteUri = uri.toString()
    }

    when {

        dashboardViewModel.userDetails.value == UploaderDetailModel() || dashboardViewModel.userDetails.value == null ->
            UploaderDetailsScreen(navController = navController)

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