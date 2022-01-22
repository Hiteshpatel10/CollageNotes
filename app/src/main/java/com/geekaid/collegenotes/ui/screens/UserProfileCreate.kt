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
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.geekaid.collegenotes.components.dropdownList
import com.geekaid.collegenotes.model.UploaderDetailModel
import com.geekaid.collegenotes.util.Constants

@Composable
fun UserProfileCreate(navController: NavHostController) {

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var uploaderType by remember { mutableStateOf("") }
    var qualification by remember { mutableStateOf("") }
    var institution by remember { mutableStateOf("") }
    var about by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var saveButtonClicked by remember { mutableStateOf(false) }
    var saveAlertDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        imageUri = uri
    }


    Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text(text = "First Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text(text = "Last Name") },
                modifier = Modifier.fillMaxWidth()
            )

            uploaderType =
                dropdownList(
                    list = Constants.uploaderType,
                    label = "Profession",
                    validateInput = false
                )

            OutlinedTextField(
                value = institution,
                onValueChange = { institution = it },
                label = { Text(text = "Institution Associated With") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = qualification,
                onValueChange = { qualification = it },
                label = { Text(text = "Qualifications") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = about,
                onValueChange = { about = it },
                label = { Text(text = "About") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(8.dp))

            Text(text = "Select Profile Picture")

            Box(modifier = Modifier.clickable { launcher.launch("image/*") }) {
                if (imageUri != null) {
                    Image(
                        rememberImagePainter(imageUri),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                    )
                } else
                    Image(
                        Icons.Filled.Face,
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                    )
            }

            if (saveAlertDialog)
                AlertDialog(
                    onDismissRequest = { saveAlertDialog = false },
                    text = {
                        Text(
                            text = "First name and last name can't be edited once saved",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    },
                    buttons = {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .padding(bottom = 12.dp)
                                .fillMaxWidth()
                        ) {
                            Row {
                                Button(
                                    onClick = { saveAlertDialog = false },
                                    modifier = Modifier.padding(8.dp)
                                ) {
                                    Text(text = "Edit Details")
                                }
                                Button(
                                    onClick = {
                                        saveButtonClicked = true
                                        saveAlertDialog = false
                                    },
                                    modifier = Modifier.padding(8.dp)
                                ) {
                                    Text(text = "Proceed")
                                }
                            }
                        }
                    }
                )
        }

        if (saveButtonClicked)
            UserSocialMediaLinks(
                uploaderDetailModel = UploaderDetailModel(
                    firstName = firstName,
                    lastName = lastName,
                    uploaderType = uploaderType,
                    qualification = qualification,
                    about = about,
                    institutionAssociatedWith = institution
                ),
                imageUri = imageUri,
                navController = navController
            )

        Button(onClick = {
            if (firstName.isEmpty() || lastName.isEmpty())
                Toast.makeText(context, "Name Can't Be Empty", Toast.LENGTH_SHORT).show()
            else
                saveAlertDialog = true

        }, modifier = Modifier.padding(bottom = 64.dp)) {
            Text("Save")
        }

    }
}

