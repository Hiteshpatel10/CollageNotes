package com.geekaid.collagenotes.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.geekaid.collagenotes.components.dropdownList
import com.geekaid.collagenotes.firebaseDao.filterScreenDao
import com.geekaid.collagenotes.model.FilterModel
import com.geekaid.collagenotes.util.*

@Composable
fun FilterScreen() {

    val context = LocalContext.current

    var course by remember { mutableStateOf("") }
    var branch by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(8.dp)) {

        course = dropdownList(list = courseList, label = "Course")

        when (course) {
            "BTech" -> {
                branch = dropdownList(list = branchList, label = "Branch")
            }
        }

        when (branch) {
            "Computer Science" -> subject = dropdownList(list = csSubjectList, label = "Subject")
        }

        Button(
            onClick = {
                filterScreenDao(
                    FilterModel(
                        course = course,
                        branch = branch,
                        subject = subject
                    ),
                    context = context
                )
            },
            modifier = Modifier
                .padding(start = 128.dp, end = 128.dp, top = 16.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Submit")
        }
    }
}