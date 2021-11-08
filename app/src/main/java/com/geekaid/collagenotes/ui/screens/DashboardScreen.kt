package com.geekaid.collagenotes.ui.screens

import android.app.DownloadManager
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.components.NoNotesFound
import com.geekaid.collagenotes.components.NoteLayout
import com.geekaid.collagenotes.model.FileUploadModel
import com.geekaid.collagenotes.model.FilterModel
import com.geekaid.collagenotes.viewmodel.DashboardViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@Composable
fun DashboardScreen(
    downloadManager: DownloadManager,
    dashboardViewModel: DashboardViewModel,
    navController: NavHostController,
) {

    dashboardViewModel.getFilter()
        .collectAsState(initial = null).value?.toObject(FilterModel::class.java)?.let { filter ->
            dashboardViewModel.filter.value = filter
        }

    if (dashboardViewModel.filter.value.course.isNotEmpty()) {
        dashboardViewModel.getNotes()
            .collectAsState(initial = null).value?.toObjects(FileUploadModel::class.java)
            ?.let { list ->
                dashboardViewModel.courseList.value = list
            }
    }

    when {
        dashboardViewModel.filter.value.course.isEmpty() -> {
            NoNotesFound(navController = navController, buttonDisplay = true)
        }

        dashboardViewModel.courseList.value.isEmpty() -> {
            NoNotesFound(navController = navController, buttonDisplay = true)
        }

        else -> {
            NoteLayout(
                notes = dashboardViewModel.courseList.value,
                downloadManager = downloadManager
            )
        }
    }
}

