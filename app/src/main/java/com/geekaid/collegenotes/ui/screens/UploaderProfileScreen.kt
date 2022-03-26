package com.geekaid.collegenotes.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.geekaid.collegenotes.components.ProgressBar
import com.geekaid.collegenotes.components.userProfileComponents.ProfileTopBar
import com.geekaid.collegenotes.components.userProfileComponents.ProfileUploadStatsBar
import com.geekaid.collegenotes.components.userProfileComponents.ProfileUploaderDetail
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

    val scope = rememberCoroutineScope()

    email?.let { it->
        LaunchedEffect(key1 = Unit) {
            scope.launch {
                dashboardViewModel.getUploaderDetails(email = it)
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {

        if (dashboardViewModel.isGetUploaderDetailsFetching.value)
            ProgressBar(isDisplay = dashboardViewModel.isGetUploaderDetailsFetching.value)

        ProfileTopBar(
            uploaderDetails = dashboardViewModel.uploaderDetails.value,
            email = email,
            navController = navController
        )

        ProfileUploadStatsBar(
            likes = dashboardViewModel.uploaderDetails.value?.likes.toString(),
            downloads = dashboardViewModel.uploaderDetails.value?.downloadedTimes.toString(),
            notes = dashboardViewModel.uploaderDetails.value?.uploaded.toString()
        )

        ProfileUploaderDetail(uploaderDetail = dashboardViewModel.uploaderDetails.value)
    }

}