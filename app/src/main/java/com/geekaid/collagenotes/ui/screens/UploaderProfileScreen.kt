package com.geekaid.collagenotes.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.geekaid.collagenotes.components.CoilImage
import com.geekaid.collagenotes.components.HeadingValueStyle
import com.geekaid.collagenotes.model.FileUploadModel
import com.geekaid.collagenotes.viewmodel.DashboardViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import timber.log.Timber

@ExperimentalCoroutinesApi
@Composable
fun UserProfileScreen(dashboardViewModel: DashboardViewModel, email: String?) {

    var likes by remember { mutableStateOf("0") }
    var downloads by remember { mutableStateOf("0") }
    var notes by remember { mutableStateOf("0") }
    var isListFetched by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    email?.let {
        dashboardViewModel.getUserUploadList(email = it)
            .collectAsState(initial = null).value?.toObjects(FileUploadModel::class.java)
            ?.let { list ->
                isListFetched = false
                dashboardViewModel.userUploadList.value = list
                isListFetched = true
            }

        SideEffect {
            scope.launch {
                dashboardViewModel.uploaderDetails.value =
                    dashboardViewModel.getDetails(email = email)
                Timber.i(dashboardViewModel.uploaderDetails.value.toString())
            }
        }
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
            CoilImage(imageUri = dashboardViewModel.uploaderDetails.value?.profileUri)
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = "${dashboardViewModel.uploaderDetails.value?.firstName} ${dashboardViewModel.uploaderDetails.value?.lastName}")
            Box(contentAlignment = Alignment.TopEnd) {
                Image(Icons.Filled.Edit, contentDescription = "Edit Profile")
            }
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
            value = dashboardViewModel.uploaderDetails.value?.uploaderType.toString(),
            isSpacer = true
        )

        HeadingValueStyle(
            heading = "Qualification",
            value = dashboardViewModel.uploaderDetails.value?.qualification.toString(),
            isSpacer = true
        )

        HeadingValueStyle(
            heading = "Institution Associated With",
            value = dashboardViewModel.uploaderDetails.value?.institutionAssociatedWith.toString(),
            isSpacer = true
        )

        HeadingValueStyle(
            heading = "About",
            value = dashboardViewModel.uploaderDetails.value?.about.toString(),
            isSpacer = true
        )
    }
}