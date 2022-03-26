package com.geekaid.collegenotes.components

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.geekaid.collegenotes.firebaseDao.screenDao.fileUploadDao
import com.geekaid.collegenotes.model.FileMeta
import com.geekaid.collegenotes.model.FileUploadModel
import com.geekaid.collegenotes.model.UploaderDetailModel
import com.geekaid.collegenotes.viewmodel.DashboardViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.*

@Composable
fun FileUploadComponent(
    noteUri: String,
    context: Context,
    navController: NavHostController,
    userDetails: UploaderDetailModel,
    dashboardViewModel: DashboardViewModel,
) {

    val auth = Firebase.auth

    val uriResult: Uri = Uri.parse(noteUri)
    val mime = context.contentResolver.getType(uriResult)

    val scope = rememberCoroutineScope()
    var course by remember { mutableStateOf("") }
    var branch by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var noteType by remember { mutableStateOf("") }
    var fileDescription by remember { mutableStateOf("") }
    val filePath by remember { mutableStateOf("${uriResult.path}") }
    val fileMime by remember { mutableStateOf("$mime") }
    val emptyCheck =
        branch.isNotEmpty() && course.isNotEmpty() && subject.isNotEmpty() && noteType.isNotEmpty()
    val index by remember {
        mutableStateOf(
            uriResult.lastPathSegment?.lastIndexOf('/')?.plus(1).toString()
        )
    }
    val fileName by remember { mutableStateOf(uriResult.lastPathSegment?.substring(index.toInt())) }
    var validateInput by remember { mutableStateOf(false) }


    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

        LaunchedEffect(key1 = false) {
            dashboardViewModel.courseList.value = dashboardViewModel.getCourseLists()!!
            dashboardViewModel.noteTypeList.value = dashboardViewModel.getNoteTypeList()!!
        }

        if (course.isNotEmpty())
          SideEffect {
              scope.launch {
                  dashboardViewModel.branchList.value =
                      dashboardViewModel.getBranchList(course)!!
              }
          }

        if (course.isNotEmpty() && branch.isNotEmpty())
           SideEffect {
               scope.launch {
                   dashboardViewModel.subjectList.value =
                       dashboardViewModel.getSubjectList(course, branch)!!
               }
           }

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

                course = dropdownList(
                    list = dashboardViewModel.courseList.value.list,
                    label = "Course",
                    validateInput = validateInput
                )

                if (course.isNotEmpty())
                    branch = dropdownList(
                        list = dashboardViewModel.branchList.value.list,
                        label = "Branch",
                        validateInput = validateInput
                    )

                if (course.isNotEmpty() && branch.isNotEmpty())
                    subject = dropdownList(
                        list = dashboardViewModel.subjectList.value.list,
                        label = "Subject",
                        validateInput = validateInput
                    )

                if (branch.isNotEmpty() && course.isNotEmpty() && subject.isNotEmpty())
                    noteType = dropdownList(
                        list = dashboardViewModel.noteTypeList.value.list,
                        label = "Note Type",
                        validateInput = validateInput
                    )

            }

            if (emptyCheck)
                OutlinedTextField(
                    value = fileDescription,
                    onValueChange = { fileDescription = it },
                    label = { Text(text = "Description") },
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                        .fillMaxWidth()
                )
            if (validateInput && fileDescription.isEmpty() && emptyCheck) {
                Text(
                    text = "Description can't be empty",
                    color = Color.Red,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp)
                )
            }
        }

        Button(onClick = {
            validateInput = true
            if (fileDescription.isNotEmpty() && emptyCheck) {
                scope.launch {
                    fileUploadDao(
                        uriResult,
                        context,
                        FileUploadModel(
                            branch = branch,
                            course = course,
                            subject = subject,
                            noteType = noteType,
                            date = DateFormat.getDateInstance().format(Date()),
                            fileInfo = FileMeta(
                                fileMime = fileMime,
                                fileName = fileName.toString(),
                                fileUploadPath = "${auth.currentUser?.email}${fileName}",
                                fileDescription = fileDescription,
                                uploadedBy = "${userDetails.firstName} ${userDetails.lastName}",
                                uploaderEmail = auth.currentUser?.email.toString()
                            )
                        ),
                        navController = navController
                    )
                }
            }
        }, Modifier.padding(bottom = 64.dp)) {
            Text(text = "Upload File")
        }
    }
}

