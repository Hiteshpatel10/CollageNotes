package com.geekaid.collagenotes.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.geekaid.collagenotes.components.dropdownList
import com.geekaid.collagenotes.util.*

@Composable
fun FilterScreen() {
    listOf("Item1", "Item2", "Item3")


    var course by remember { mutableStateOf("") }
    var branch by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var semester by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(8.dp)) {

        course = dropdownList(list = courseList, label = "Course")

        when (course) {
            "BTech" -> {
                branch = dropdownList(list = branchList, label = "Branch")
                year = dropdownList(list = yearList, label = "Year")
                semester = dropdownList(list = semesterList, label = "Semester")
            }
        }

        when (branch) {
            "Computer Science" -> dropdownList(list = csSubjectList, label = "Subject")
        }



        Button(
            onClick = {

            },
            modifier = Modifier
                .padding(start = 128.dp, end = 128.dp, top = 16.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Submit")
        }
    }
}