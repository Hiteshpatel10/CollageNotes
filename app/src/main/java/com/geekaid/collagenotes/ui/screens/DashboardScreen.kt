package com.geekaid.collagenotes.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.geekaid.collagenotes.components.NoteLayout
import com.geekaid.collagenotes.firebaseDao.screenDao.dashboardDao
import com.geekaid.collagenotes.viewmodel.DashboardViewModel
import kotlinx.coroutines.launch


@Composable
fun DashboardScreen() {

    val viewModel: DashboardViewModel = viewModel()
    val scope = rememberCoroutineScope()

    SideEffect {
        scope.launch {
            dashboardDao(viewModel = viewModel)
        }
    }


    Column(modifier = Modifier.padding(4.dp)) {
        NoteLayout(notes = viewModel.courseList.value)
    }
}