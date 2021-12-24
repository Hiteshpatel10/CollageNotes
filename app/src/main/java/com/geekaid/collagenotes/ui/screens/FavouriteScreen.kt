package com.geekaid.collagenotes.ui.screens

import android.app.DownloadManager
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.components.NoNotesFound
import com.geekaid.collagenotes.components.NoteLayout
import com.geekaid.collagenotes.model.FileUploadModel
import com.geekaid.collagenotes.util.Constants
import com.geekaid.collagenotes.viewmodel.DashboardViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun FavouriteScreen(
    downloadManager: DownloadManager,
    dashboardViewModel: DashboardViewModel,
    navController: NavHostController
) {

    val context = LocalContext.current
    var favTabIndex by remember { mutableStateOf(Constants.favSpaces.indexOf(dashboardViewModel.favouriteSpace.value)) }

    dashboardViewModel.getFavouriteNotes()
        .collectAsState(initial = null).value?.toObjects(FileUploadModel::class.java)?.let { list ->
            dashboardViewModel.favouriteList.value = list
        }


    Scaffold(topBar = {
        TabRow(selectedTabIndex = favTabIndex) {
            Constants.favSpaces.forEachIndexed { index, string ->
                Tab(
                    selected = index == favTabIndex,
                    onClick = {
                        dashboardViewModel.favouriteSpace.value = string
                        favTabIndex = Constants.favSpaces.indexOf(string)
                    },
                    text = { Text(text = string) },
                    selectedContentColor = MaterialTheme.colors.primary,
                    unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
                )
            }
        }
    }) {
        when {
            dashboardViewModel.favouriteList.value.isEmpty() -> {
                NoNotesFound(
                    buttonText = "Add Notes To Fav",
                    displayText = "No favourite ${dashboardViewModel.notesType.value} found",
                    navController = navController,
                    buttonDisplay = false
                )
            }

            else -> {
                NoteLayout(
                    notes = dashboardViewModel.favouriteList.value,
                    context = context,
                    downloadManager = downloadManager,
                    dashboardViewModel = dashboardViewModel,
                    navController = navController
                )
            }
        }

    }
}

