package com.geekaid.collagenotes.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.geekaid.collagenotes.components.CoilImage
import com.geekaid.collagenotes.components.HeadingValueStyle
import com.geekaid.collagenotes.model.FileUploadModel
import com.geekaid.collagenotes.viewmodel.DashboardViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun UserProfileScreen(dashboardViewModel: DashboardViewModel) {

    val userDetail by remember { mutableStateOf(dashboardViewModel.userDetails.value) }
    var likes by remember { mutableStateOf("0") }
    var downloads by remember { mutableStateOf("0") }
    var notes by remember { mutableStateOf("0") }
    var isListFetched by remember { mutableStateOf(false) }

    dashboardViewModel.getUserUploadList()
        .collectAsState(initial = null).value?.toObjects(FileUploadModel::class.java)?.let { list ->
            isListFetched = false
            dashboardViewModel.userUploadList.value = list
            isListFetched = true
        }

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {

        if (isListFetched) {
            likes = "0"
            downloads = "0"
            dashboardViewModel.userUploadList.value.forEach { note ->
                likes = (likes.toInt() + note.likes.size).toString()
                downloads = "${downloads.toInt().plus(note.downloadedTimes)}"
                notes = dashboardViewModel.userUploadList.value.size.toString()
            }
        }


        Column(
            modifier = Modifier
                .padding(top = 32.dp, bottom = 8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CoilImage(imageUri = userDetail?.profileUri)
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = "${dashboardViewModel.userDetails.value!!.firstName} ${dashboardViewModel.userDetails.value!!.firstName}")
        }

        Row(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Likes")
                Text(text = likes)
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "downloads")
                Text(text = downloads)
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Notes")
                Text(text = notes)
            }
        }

        HeadingValueStyle(
            heading = "Type",
            value = dashboardViewModel.userDetails.value?.uploaderType.toString(),
            isSpacer = true
        )

        HeadingValueStyle(
            heading = "Qualification",
            value = dashboardViewModel.userDetails.value?.qualification.toString(),
            isSpacer = true
        )

        HeadingValueStyle(
            heading = "Institution Associated With",
            value = dashboardViewModel.userDetails.value?.institutionAssociatedWith.toString(),
            isSpacer = true
        )

        HeadingValueStyle(
            heading = "About",
            value = dashboardViewModel.userDetails.value?.about.toString(),
            isSpacer = true
        )
    }
}