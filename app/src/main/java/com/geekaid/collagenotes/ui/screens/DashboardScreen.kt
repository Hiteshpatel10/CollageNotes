package com.geekaid.collagenotes.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.geekaid.collagenotes.components.NoteLayout
import com.geekaid.collagenotes.firebaseDao.dashboardDao
import com.geekaid.collagenotes.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen() {

    val viewModel: DashboardViewModel = viewModel()
    dashboardDao(viewModel = viewModel)

    Column(modifier = Modifier.padding(4.dp)) {
        Text(text = "Total results found ${ viewModel.filter.value.branch}")
        NoteLayout(course = viewModel.courseList.value, modifier = Modifier)
    }
}