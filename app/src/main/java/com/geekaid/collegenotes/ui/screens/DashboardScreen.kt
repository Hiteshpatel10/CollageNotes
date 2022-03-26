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
import com.geekaid.collegenotes.components.noteLayoutComponents.NoteLayout
import com.geekaid.collegenotes.model.FileUploadModel
import com.geekaid.collegenotes.model.FilterModel
import com.geekaid.collegenotes.navigation.Screens
import com.geekaid.collegenotes.util.Constants
import com.geekaid.collegenotes.viewmodel.DashboardViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalPermissionsApi
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
    var favTabIndex by remember { mutableStateOf(Constants.filterBy.indexOf(dashboardViewModel.notesType.value)) }

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

    LaunchedEffect(key1 = Unit) {
        scope.launch {
            dashboardViewModel.getDetails(email = auth.currentUser?.email.toString())
        }
    }


    Scaffold(
        topBar = {
            TabRow(selectedTabIndex = favTabIndex) {
                Constants.filterBy.forEachIndexed { index, string ->
                    Tab(
                        selected = index == favTabIndex,
                        onClick = {
                            dashboardViewModel.notesType.value = string
                            favTabIndex = Constants.filterBy.indexOf(string)
                        },
                        text = { Text(text = string) },
                        selectedContentColor = MaterialTheme.colors.onPrimary,
                        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
                    )
                }
            }
        }) {

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
}

