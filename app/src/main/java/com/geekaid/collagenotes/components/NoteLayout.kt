package com.geekaid.collagenotes.components

import android.app.DownloadManager
import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.geekaid.collagenotes.model.FileUploadModel

@ExperimentalMaterialApi
@Composable
fun NoteLayout(notes: List<FileUploadModel>, context: Context, downloadManager: DownloadManager) {

    val constraintSet = ConstraintSet {
        val noteDetails = createRefFor("noteDetails")
        val noteSidebar = createRefFor("noteSidebar")
        val noteVote = createRefFor("noteVote")

        constrain(noteDetails) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
        }

        constrain(noteSidebar) {
            top.linkTo(parent.top)
            end.linkTo(parent.end)
        }

        constrain(noteVote) {
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
        }
    }

    LazyColumn(modifier = Modifier.padding(top = 4.dp, start = 4.dp, end = 4.dp, bottom = 60.dp)) {
        items(notes) { note ->
            var isExpanded by remember { mutableStateOf(false) }
            Card(modifier = Modifier.padding(4.dp), onClick = { isExpanded = !isExpanded }) {
                ConstraintLayout(constraintSet = constraintSet) {
                    NoteDetails(note = note, isExpanded = isExpanded)
                    NoteSidebar(note = note, context = context, downloadManager = downloadManager)
                    Vote(note = note)
                }
            }
        }
    }
}





