package com.geekaid.collegenotes.ui.screens

import android.app.DownloadManager
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.geekaid.collegenotes.R
import com.geekaid.collegenotes.components.NoNotesFound
import com.geekaid.collegenotes.components.ProgressBar
import com.geekaid.collegenotes.components.noteLayoutComponents.NoteLayout
import com.geekaid.collegenotes.model.FileUploadModel
import com.geekaid.collegenotes.model.ListFetch
import com.geekaid.collegenotes.navigation.BottomNavScreen
import com.geekaid.collegenotes.util.Constants
import com.geekaid.collegenotes.viewmodel.DashboardViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalPermissionsApi
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
    var isProgressBarVisible by remember { mutableStateOf(false) }



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

