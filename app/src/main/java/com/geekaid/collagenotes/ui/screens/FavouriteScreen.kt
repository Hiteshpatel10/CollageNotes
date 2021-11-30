package com.geekaid.collagenotes.ui.screens

import android.app.DownloadManager
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.components.NoNotesFound
import com.geekaid.collagenotes.components.NoteLayout
import com.geekaid.collagenotes.model.FileUploadModel
import com.geekaid.collagenotes.viewmodel.DashboardViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun FavouriteScreen(
    downloadManager: DownloadManager,
    dashboardViewModel: DashboardViewModel,
    navController: NavHostController
) {

    val context = LocalContext.current

    dashboardViewModel.getFavouriteNotes()
        .collectAsState(initial = null).value?.toObjects(FileUploadModel::class.java)?.let { list ->
            dashboardViewModel.favouriteList.value = list
        }


    when {

        dashboardViewModel.favouriteList.value.isEmpty() -> {
            NoNotesFound(
                buttonText = "Add Notes To Fav",
                displayText = "No favourite notes found",
                navController = navController,
                buttonDisplay = false
            )
        }

        else -> {
            NoteLayout(
                notes = dashboardViewModel.favouriteList.value,
                context = context,
                downloadManager = downloadManager
            )
        }
    }
}