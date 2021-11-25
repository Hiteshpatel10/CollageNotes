package com.geekaid.collagenotes.ui.screens

import android.app.DownloadManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.components.NoNotesFound
import com.geekaid.collagenotes.components.NoteLayout
import com.geekaid.collagenotes.components.ProgressBar
import com.geekaid.collagenotes.model.FileUploadModel
import com.geekaid.collagenotes.model.FilterModel
import com.geekaid.collagenotes.navigation.Screens
import com.geekaid.collagenotes.viewmodel.DashboardViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun DashboardScreen(
    downloadManager: DownloadManager,
    dashboardViewModel: DashboardViewModel,
    navController: NavHostController,
) {

    val auth = Firebase.auth

    dashboardViewModel.getFilter()
        .collectAsState(initial = null).value?.toObject(FilterModel::class.java)?.let { filter ->
            dashboardViewModel.filter.value = filter
            dashboardViewModel.progressBar.value = true
        }


    if (dashboardViewModel.filter.value.course.isNotEmpty()) {
        dashboardViewModel.getNotes()
            .collectAsState(initial = null).value?.toObjects(FileUploadModel::class.java)
            ?.let { list ->
                dashboardViewModel.courseList.value = list
                dashboardViewModel.progressBar.value = false
            }
    }

    when {

        auth.currentUser == null -> navController.navigate(Screens.SplashNav.route)

        dashboardViewModel.progressBar.value -> ProgressBar(isDisplay = dashboardViewModel.progressBar.value)

        dashboardViewModel.filter.value.course.isEmpty() -> {
            NoNotesFound(
                buttonText = "Add Filter",
                displayText = "Please add filter",
                navController = navController,
                buttonDisplay = true
            )
        }

        dashboardViewModel.courseList.value.isEmpty() -> {
            NoNotesFound(
                buttonText = "Change Filter",
                displayText = "No Notes Found",
                navController = navController,
                buttonDisplay = true
            )
        }

        else -> {
            NoteLayout(
                notes = dashboardViewModel.courseList.value,
                downloadManager = downloadManager
            )
        }
    }
}

