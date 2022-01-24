package com.geekaid.collegenotes.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.geekaid.collegenotes.components.FileSelectComponent
import com.geekaid.collegenotes.components.FileUploadComponent
import com.geekaid.collegenotes.components.ProgressBar
import com.geekaid.collegenotes.model.UploaderDetailModel
import com.geekaid.collegenotes.navigation.BottomNavScreen
import com.geekaid.collegenotes.viewmodel.DashboardViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

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

    val scope = rememberCoroutineScope()
    val auth = Firebase.auth

//    LaunchedEffect(key1 = true) {
//        scope.launch {
//            dashboardViewModel.getDetails(email = auth.currentUser?.email.toString())
//        }
//    }

    when {

        dashboardViewModel.isGetDetailsFetching.value -> ProgressBar(isDisplay = dashboardViewModel.isGetDetailsFetching.value)

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