package com.geekaid.collegenotes.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.geekaid.collegenotes.components.CoilImage
import com.geekaid.collegenotes.components.dropdownList
import com.geekaid.collegenotes.model.UploaderDetailModel
import com.geekaid.collegenotes.util.Constants
import com.geekaid.collegenotes.viewmodel.DashboardViewModel

@Composable
fun UserProfileEditScreen(
    dashboardViewModel: DashboardViewModel,
    navController: NavHostController
) {

    var uploaderType by remember { mutableStateOf(dashboardViewModel.userDetails.value?.uploaderType.toString()) }
    var qualification by remember { mutableStateOf(dashboardViewModel.userDetails.value?.qualification.toString()) }
    var institution by remember { mutableStateOf(dashboardViewModel.userDetails.value?.institutionAssociatedWith.toString()) }
    var about by remember { mutableStateOf(dashboardViewModel.userDetails.value?.about.toString()) }
    val userDetail by remember { mutableStateOf(dashboardViewModel.userDetails.value) }
    var imageUri by remember { mutableStateOf<Uri?>(Uri.parse(dashboardViewModel.userDetails.value?.profileUri.toString())) }
    var socialMediaLinks by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        imageUri = uri
    }


    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .padding(32.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                if (imageUri.toString().isNotEmpty())
                    CoilImage(imageUri = imageUri.toString())
                else
                    Image(
                        Icons.Filled.Face, contentDescription = "No Profile Image",
                        modifier = Modifier.size(100.dp)
                    )

                Image(Icons.Filled.ImageSearch,
                    contentDescription = "Edit",
                    modifier = Modifier
                        .padding(top = 70.dp)
                        .clickable(
                            enabled = true,
                            onClickLabel = "Select Image",
                            onClick = {
                                Toast
                                    .makeText(context, "Make", Toast.LENGTH_SHORT)
                                    .show()
                                launcher.launch("image/*")
                            }
                        ))
                Spacer(modifier = Modifier.padding(16.dp))
                Text(text = "${dashboardViewModel.uploaderDetails.value?.firstName?.uppercase()} ${dashboardViewModel.uploaderDetails.value?.lastName?.uppercase()}")
            }

            uploaderType =
                dropdownList(
                    list = Constants.uploaderType,
                    label = "Profession",
                    validateInput = false,
                    defaultValue = uploaderType
                )

            OutlinedTextField(
                value = qualification,
                onValueChange = { qualification = it },
                label = { Text(text = "Institution Associated With") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = institution,
                onValueChange = { institution = it },
                label = { Text(text = "Qualifications") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = about,
                onValueChange = { about = it },
                label = { Text(text = "About") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (socialMediaLinks)
            UserSocialMediaLinks(
                uploaderDetailModel = UploaderDetailModel(
                    firstName = userDetail?.firstName!!,
                    lastName = userDetail?.lastName!!,
                    uploaderType = uploaderType,
                    qualification = qualification,
                    about = about,
                    profileUri = imageUri.toString(),
                    institutionAssociatedWith = institution,
                    instagram = userDetail?.instagram.toString(),
                    youtube = userDetail?.youtube.toString(),
                    twitter = userDetail?.twitter.toString()
                ),
                imageUri = imageUri,
                navController = navController
            )

        Button(onClick = {
            socialMediaLinks = true
        }, modifier = Modifier.padding(bottom = 64.dp)) {
            Text("Next")
        }

    }
}
//
//uploaderDetailDao(
//UploaderDetailModel(
//firstName = userDetail?.firstName!!,
//lastName = userDetail?.lastName!!,
//uploaderType = uploaderType,
//qualification = qualification,
//about = about,
//profileUri = imageUri.toString(),
//institutionAssociatedWith = institution
//),
//imageUri = imageUri,
//context = context
//).also {
//    navController.navigate(BottomNavScreen.DashboardNav.route)
//}