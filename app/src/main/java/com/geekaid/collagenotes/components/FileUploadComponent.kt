package com.geekaid.collagenotes.components

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.geekaid.collagenotes.model.FileUploadModel
import com.geekaid.collagenotes.util.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun FileUploadComponent(noteUri: String, context: Context) {

    val auth = Firebase.auth

    val uriResult: Uri = Uri.parse(noteUri)
    val mime = context.contentResolver.getType(uriResult)

    var course by remember { mutableStateOf("") }
    var branch by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    val filePath by remember { mutableStateOf("${uriResult.path}") }
    val fileMime by remember { mutableStateOf("$mime") }

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

        Column(Modifier.weight(1f)) {

            Column(modifier = Modifier.padding(8.dp)) {

                OutlinedTextField(
                    value = filePath, onValueChange = {},
                    label = { Text(text = "File Path") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp, bottom = 2.dp)
                )

                OutlinedTextField(
                    value = fileMime, onValueChange = {},
                    label = { Text(text = "File Type") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp, bottom = 2.dp)
                )

                course = dropdownList(list = courseList, label = "Course")

                when (course) {
                    "BTech" -> {
                        branch = dropdownList(list = branchList, label = "Branch")
                        year = dropdownList(list = yearList, label = "Year")
                    }
                }

                when (branch) {
                    "Computer Science" -> subject =
                        dropdownList(list = csSubjectList, label = "Subject")
                }
            }
        }

        Button(onClick = {
            fileUpload(
                uriResult,
                context,
                FileUploadModel(
                    branch = branch,
                    course = course,
                    fileMime = fileMime,
                    fileName = uriResult.lastPathSegment.toString(),
                    fileUploadPath = "${auth.currentUser?.email}${uriResult.lastPathSegment}",
                    subject = subject,
                    year = year,
                )
            )
        }, Modifier.padding(bottom = 64.dp)) {
            Text(text = "Upload File")
        }
    }
}
