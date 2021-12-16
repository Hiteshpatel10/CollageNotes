package com.geekaid.collagenotes.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.geekaid.collagenotes.components.dropdownList
import com.geekaid.collagenotes.util.Constants

@Composable
fun UploaderDetailsScreen() {

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var uploaderType by remember { mutableStateOf("") }
    var qualification by remember { mutableStateOf("") }
    var about by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        imageUri = uri
    }

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
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
            value = qualification,
            onValueChange = { qualification = it },
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


        if (imageUri != null)
            Image(rememberImagePainter(imageUri),
                contentDescription = null,
                modifier = Modifier
                    .clickable(
                        enabled = true,
                        onClickLabel = "Select Image",
                        onClick = {
                            Toast
                                .makeText(context, "Make", Toast.LENGTH_SHORT)
                                .show()
                            launcher.launch("image/*")
                        }
                    )
                    .fillMaxSize(0.6f))

        if (imageUri == null)
            Image(Icons.Filled.Face,
                contentDescription = null,
                modifier = Modifier
                    .clickable(
                        enabled = true,
                        onClickLabel = "Select Image",
                        onClick = {
                            Toast
                                .makeText(context, "Make", Toast.LENGTH_SHORT)
                                .show()
                            launcher.launch("image/*")
                        }
                    )
                    .fillMaxSize(0.6f))

        Text(text = "Select Profile Image", textAlign = TextAlign.Center)

    }
}