package com.geekaid.collagenotes.ui.screens

import android.app.DownloadManager
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.R
import com.geekaid.collagenotes.components.NoNotesFound
import com.geekaid.collagenotes.components.ProgressBar
import com.geekaid.collagenotes.components.noteLayoutComponents.NoteLayout
import com.geekaid.collagenotes.model.FileUploadModel
import com.geekaid.collagenotes.model.ListFetch
import com.geekaid.collagenotes.navigation.BottomNavScreen
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
    var isProgressBarVisible by remember { mutableStateOf(true) }

    var list by remember { mutableStateOf(listOf<FileUploadModel>()) }
    dashboardViewModel.getFN()
        .collectAsState(initial = null).value?.toObject(ListFetch::class.java)
        ?.let { favDocRefList ->
            isProgressBarVisible = true
            dashboardViewModel.favDocRefList.value = favDocRefList
            dashboardViewModel.list.collectAsState(initial = null).value?.let {
                list = it
            }
            isProgressBarVisible = false
        }


    Scaffold(topBar = {
        TabRow(selectedTabIndex = favTabIndex) {
            Constants.favSpaces.forEachIndexed { index, string ->
                Tab(
                    selected = index == favTabIndex,
                    onClick = {
                        dashboardViewModel.favouriteSpace.value = string
                        favTabIndex = Constants.favSpaces.indexOf(string)
                        navController.navigate(BottomNavScreen.FavouriteScreenNav.route)
                    },
                    text = { Text(text = string) },
                    selectedContentColor = MaterialTheme.colors.onPrimary,
                    unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
                )
            }
        }
    }) {
        when {

            isProgressBarVisible -> {
                ProgressBar(isDisplay = isProgressBarVisible)
            }

            list.isEmpty() -> {
                NoNotesFound(
                    buttonText = "Add Notes To Fav",
                    displayText = "No favourite ${dashboardViewModel.notesType.value} found",
                    navController = navController,
                    painter = painterResource(id = R.drawable.add_notes),
                    buttonDisplay = false
                )
            }

            else -> {
                NoteLayout(
                    notes = list,
                    context = context,
                    downloadManager = downloadManager,
                    dashboardViewModel = dashboardViewModel,
                    navController = navController
                )
            }
        }

    }
}

