package com.geekaid.collagenotes.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.geekaid.collagenotes.components.NoteLayout
import com.geekaid.collagenotes.firebaseDao.noteLayoutDao.favouriteDaoFetch
import com.geekaid.collagenotes.viewmodel.FavouriteViewModel

@Composable
fun FavouriteScreen() {
    val favViewModel: FavouriteViewModel = viewModel()
    favouriteDaoFetch(favViewModel = favViewModel)

   Column(modifier = Modifier.padding(4.dp)) {
       NoteLayout(notes = favViewModel.favouriteList.value)
   }
}