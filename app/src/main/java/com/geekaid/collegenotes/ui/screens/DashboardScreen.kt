package com.geekaid.collegenotes.ui.screens

import android.app.DownloadManager
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.geekaid.collegenotes.R
import com.geekaid.collegenotes.components.NoNotesFound
import com.geekaid.collegenotes.components.noteLayoutComponents.NoteLayout
import com.geekaid.collegenotes.model.FileUploadModel
import com.geekaid.collegenotes.model.FilterModel
import com.geekaid.collegenotes.navigation.Screens
import com.geekaid.collegenotes.viewmodel.DashboardViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun DashboardScreen(
    downloadManager: DownloadManager,
    dashboardViewModel: DashboardViewModel,
    navController: NavHostController,
) {

    val auth = Firebase.auth
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    dashboardViewModel.getFilter()
        .collectAsState(initial = null).value?.toObject(FilterModel::class.java)?.let { filter ->
            dashboardViewModel.filter.value = filter
        }

    if (dashboardViewModel.filter.value.course.isNotEmpty()) {
        dashboardViewModel.getNotes()
            .collectAsState(initial = null).value?.toObjects(FileUploadModel::class.java)
            ?.let { list ->
                dashboardViewModel.notesList.value = list
            }
    }

    SideEffect {
        scope.launch {
            dashboardViewModel.userDetails.value =
                dashboardViewModel.getDetails(email = auth.currentUser?.email.toString())
        }
    }


    when {
        auth.currentUser == null -> navController.navigate(Screens.SplashNav.route)

        dashboardViewModel.filter.value.course.isEmpty() -> {
            NoNotesFound(
                buttonText = "Add Filter",
                displayText = "\n No Filter Found \nPlease Add filter",
                painter = painterResource(id = R.drawable.error),
                navController = navController,
                buttonDisplay = true
            )
        }

        dashboardViewModel.notesList.value.isEmpty() -> {
            NoNotesFound(
                buttonText = "Change Filter",
                displayText = "No ${dashboardViewModel.notesType.value} found",
                painter = painterResource(id = R.drawable.empty),
                navController = navController,
                buttonDisplay = true
            )
        }

        else -> {
            NoteLayout(
                notes = dashboardViewModel.notesList.value,
                context = context,
                downloadManager = downloadManager,
                dashboardViewModel = dashboardViewModel,
                navController = navController
            )

        }
    }

}
