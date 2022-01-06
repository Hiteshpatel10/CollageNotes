package com.geekaid.collegenotes.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.geekaid.collegenotes.components.CoilImage
import com.geekaid.collegenotes.components.HeadingValueStyle
import com.geekaid.collegenotes.model.FileUploadModel
import com.geekaid.collegenotes.navigation.BottomNavScreen
import com.geekaid.collegenotes.util.userUploadRef
import com.geekaid.collegenotes.viewmodel.DashboardViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import timber.log.Timber

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
    val currentUser = Firebase.auth.currentUser!!
    val firestore = Firebase.firestore

    email?.let { it ->
        dashboardViewModel.getUserUploadList(email = it)
            .collectAsState(initial = null).value?.toObjects(FileUploadModel::class.java)
            ?.let { list ->
                isListFetched = false
                dashboardViewModel.userUploadList.value = listOf()
                dashboardViewModel.userUploadList.value = list
                isListFetched = true
            }

        SideEffect {
            scope.launch {
                dashboardViewModel.uploaderDetails.value =
                    dashboardViewModel.getDetails(email = email)
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

            if (dashboardViewModel.uploaderDetails.value?.profileUri?.isNotEmpty() == true)
                CoilImage(imageUri = dashboardViewModel.uploaderDetails.value?.profileUri.toString())
            else
                Image(
                    Icons.Filled.Face, contentDescription = "No Profile Image",
                    modifier = Modifier.size(100.dp)
                )

            Spacer(modifier = Modifier.padding(8.dp))

            Text(text = "${dashboardViewModel.uploaderDetails.value?.firstName?.uppercase()} ${dashboardViewModel.uploaderDetails.value?.lastName?.uppercase()}")

            if (email == currentUser.email)
                Image(
                    Icons.Filled.Edit,
                    contentDescription = "Edit Profile",
                    modifier = Modifier.clickable(
                        enabled = true,
                        onClickLabel = "Clickable image",
                        onClick = {
                            navController.navigate(BottomNavScreen.UserProfileEditScreenNav.route)
                        }
                    ))
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

    LazyColumn {

    }
}