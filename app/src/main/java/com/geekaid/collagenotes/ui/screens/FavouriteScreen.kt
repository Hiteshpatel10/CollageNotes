package com.geekaid.collagenotes.ui.screens

import android.app.DownloadManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.components.NoNotesFound
import com.geekaid.collagenotes.components.NoteLayout
import com.geekaid.collagenotes.firebaseDao.noteLayoutDao.favouriteDaoFetch
import com.geekaid.collagenotes.model.FileUploadModel
import com.geekaid.collagenotes.viewmodel.FavouriteViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun FavouriteScreen(
    downloadManager: DownloadManager,
    favouriteViewModel: FavouriteViewModel,
    navController: NavHostController
) {

    favouriteViewModel.getFavouriteNotes()
        .collectAsState(initial = null).value?.toObjects(FileUploadModel::class.java)?.let { list ->
            favouriteViewModel.favouriteList.value = list
        }

    if (favouriteViewModel.favouriteList.value.isEmpty()) {
        NoNotesFound(navController = navController, buttonDisplay = false)
    } else {
        NoteLayout(
            notes = favouriteViewModel.favouriteList.value,
            downloadManager = downloadManager
        )
    }
}