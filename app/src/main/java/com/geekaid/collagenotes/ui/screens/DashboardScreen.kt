package com.geekaid.collagenotes.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.geekaid.collagenotes.components.NoteLayout
import com.geekaid.collagenotes.firebaseDao.dashboardDao
import com.geekaid.collagenotes.model.FileUploadModel
import com.geekaid.collagenotes.viewmodel.DashboardViewModel
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun DashboardScreen() {

    val viewModel: DashboardViewModel = viewModel()
    dashboardDao(viewModel = viewModel)

    Column(modifier = Modifier.padding(4.dp)) {
        NoteLayout(course = viewModel.courseList.value, modifier = Modifier)
    }
}