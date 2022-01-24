package com.geekaid.collegenotes.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.geekaid.collegenotes.components.ProgressBar
import com.geekaid.collegenotes.components.userProfileComponents.ProfileTopBar
import com.geekaid.collegenotes.components.userProfileComponents.ProfileUploadStatsBar
import com.geekaid.collegenotes.components.userProfileComponents.ProfileUploaderDetail
import com.geekaid.collegenotes.model.FileUploadModel
import com.geekaid.collegenotes.viewmodel.DashboardViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch


@ExperimentalCoroutinesApi
@Composable
fun UserProfileScreen(
    email: String?,
    dashboardViewModel: DashboardViewModel,
    navController: NavHostController
) {

    var likes by remember { mutableStateOf("0") }
    var downloads by remember { mutableStateOf("0") }
    var notes by remember { mutableStateOf("0") }
    var isListFetched by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()


    email?.let { it ->

        LaunchedEffect(key1 = Unit) {
            scope.launch {
                dashboardViewModel.getUploaderDetails(email = email)
            }
        }

        dashboardViewModel.getUserUploadList(email = it)
            .collectAsState(initial = null).value?.toObjects(FileUploadModel::class.java)
            ?.let { list ->
                isListFetched = false
                dashboardViewModel.userUploadList.value = listOf()
                dashboardViewModel.userUploadList.value = list
                isListFetched = true
            }
    }

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {

        if (dashboardViewModel.isGetUploaderDetailsFetching.value)
            ProgressBar(isDisplay = dashboardViewModel.isGetUploaderDetailsFetching.value)

        if (isListFetched) {
            likes = "0"
            downloads = "0"
            dashboardViewModel.userUploadList.value.forEach { note ->
                likes = (likes.toInt() + note.likes.size).toString()
                downloads = "${downloads.toInt().plus(note.downloadedTimes)}"
                notes = dashboardViewModel.userUploadList.value.size.toString()
            }
        }

        ProfileTopBar(
            uploaderDetails = dashboardViewModel.uploaderDetails.value,
            email = email,
            navController = navController
        )

        ProfileUploadStatsBar(likes = likes, downloads = downloads, notes = notes)

        ProfileUploaderDetail(uploaderDetail = dashboardViewModel.uploaderDetails.value)
    }

}