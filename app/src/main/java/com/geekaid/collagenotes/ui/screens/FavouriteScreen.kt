package com.geekaid.collagenotes.ui.screens

import android.app.DownloadManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.components.NoNotesFound
import com.geekaid.collagenotes.components.NoteLayout
import com.geekaid.collagenotes.model.FileUploadModel
import com.geekaid.collagenotes.viewmodel.DashboardViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun FavouriteScreen(
    downloadManager: DownloadManager,
    dashboardViewModel: DashboardViewModel,
    navController: NavHostController
) {

    dashboardViewModel.getFavouriteNotes()
        .collectAsState(initial = null).value?.toObjects(FileUploadModel::class.java)?.let { list ->
            dashboardViewModel.favouriteList.value = list
        }


    if (dashboardViewModel.favouriteList.value.isEmpty()) {
        NoNotesFound(navController = navController, buttonDisplay = false)
    } else {
        NoteLayout(
            notes = dashboardViewModel.favouriteList.value,
            downloadManager = downloadManager
        )
    }
}