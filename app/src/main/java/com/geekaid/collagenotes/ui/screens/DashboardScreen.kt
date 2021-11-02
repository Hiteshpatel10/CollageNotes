package com.geekaid.collagenotes.ui.screens

import android.app.DownloadManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.geekaid.collagenotes.components.NoteLayout
import com.geekaid.collagenotes.firebaseDao.screenDao.dashboardDao
import com.geekaid.collagenotes.model.FileUploadModel
import com.geekaid.collagenotes.model.FilterModel
import com.geekaid.collagenotes.viewmodel.DashboardViewModel
import com.google.firebase.firestore.ktx.toObjects
import timber.log.Timber


@Composable
fun DashboardScreen(downloadManager: DownloadManager, dashboardViewModel: DashboardViewModel) {


    val filter = dashboardViewModel.getFilter().collectAsState(initial = null).value?.toObject(FilterModel::class.java)
    if (filter == null ) {
        Text(text = "No Notes Found")
    } else {
        dashboardViewModel.filter.value = filter
        val notesList = dashboardViewModel.getNotes().collectAsState(initial = null).value
        val n = notesList?.toObjects<FileUploadModel>()
        Timber.i(n.toString())
        n?.let {
            Column(modifier = Modifier.padding(4.dp)) {
                NoteLayout(notes = it, downloadManager = downloadManager)
            }
        }

    }


}