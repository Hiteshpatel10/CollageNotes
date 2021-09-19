package com.geekaid.collagenotes.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.unit.dp
import com.geekaid.collagenotes.components.dropdownList

@Composable
fun FilterScreen() {
    val list = listOf("Item1", "Item2", "Item3")

    var course by remember { mutableStateOf("") }
    var branch by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var semester by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(8.dp)) {
        course = dropdownList(list = list, label = "Course")
        dropdownList(list = list, label = "Branch")
        dropdownList(list = list, label = "Year")
        dropdownList(list = list, label = "Semester")
        dropdownList(list = list, label = "Subject")
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .padding(start = 128.dp, end = 128.dp, top = 16.dp)
                .fillMaxWidth()
        ) {
            Text(text = course)
        }
    }
}