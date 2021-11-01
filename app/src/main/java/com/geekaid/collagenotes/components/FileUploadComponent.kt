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
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.firebaseDao.screenDao.fileUploadDao
import com.geekaid.collagenotes.model.FeelingsModel
import com.geekaid.collagenotes.model.FileUploadModel
import com.geekaid.collagenotes.util.branchList
import com.geekaid.collagenotes.util.courseList
import com.geekaid.collagenotes.util.csSubjectList
import com.geekaid.collagenotes.util.yearList
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.DateFormat
import java.util.*

@Composable
fun FileUploadComponent(
    noteUri: String,
    context: Context,
    navController: NavHostController,
) {

    val auth = Firebase.auth

    val uriResult: Uri = Uri.parse(noteUri)
    val mime = context.contentResolver.getType(uriResult)
    val indexSS = uriResult.lastPathSegment?.lastIndexOf('/')?.and(1)

    val scope = rememberCoroutineScope()
    var course by remember { mutableStateOf("") }
    var branch by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    val filePath by remember { mutableStateOf("${uriResult.path}") }
    val fileMime by remember { mutableStateOf("$mime") }
    val i by remember {
        mutableStateOf(
            uriResult.lastPathSegment?.lastIndexOf('/')?.plus(1).toString()
        )
    }
    val fileName by remember { mutableStateOf(uriResult.lastPathSegment?.substring(i.toInt())) }



    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

        Column(Modifier.weight(1f)) {

            Column(modifier = Modifier.padding(8.dp)) {
                Timber.i(uriResult.lastPathSegment?.lastIndexOf('/').toString())
                Timber.i(uriResult.lastPathSegment?.length.toString())
                Timber.i(uriResult.lastPathSegment.toString())
                Timber.i(uriResult.lastPathSegment?.substring(i.toInt()).toString())
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
            scope.launch {
                fileUploadDao(
                    uriResult,
                    context,
                    FileUploadModel(
                        branch = branch,
                        course = course,
                        data = DateFormat.getDateInstance().format(Date()),
                        fileMime = fileMime,
                        fileName = fileName.toString(),
                        fileUploadPath = "${auth.currentUser?.email}${fileName}",
                        subject = subject,
                        year = year,
                    ),
                    navController = navController
                )
            }
        }, Modifier.padding(bottom = 64.dp)) {
            Text(text = "Upload File")
        }
    }
}
