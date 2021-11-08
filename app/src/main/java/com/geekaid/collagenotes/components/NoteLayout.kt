package com.geekaid.collagenotes.components

import android.app.DownloadManager
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.geekaid.collagenotes.firebaseDao.noteLayoutDao.favouriteDao
import com.geekaid.collagenotes.firebaseDao.noteLayoutDao.likeDao
import com.geekaid.collagenotes.firebaseDao.noteLayoutDao.noteDownloadDao
import com.geekaid.collagenotes.model.FileUploadModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun NoteLayout(notes: List<FileUploadModel>, downloadManager: DownloadManager) {

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

    LazyColumn(modifier = Modifier.padding(4.dp)) {
        items(notes) { note ->
            Card(modifier = Modifier.padding(4.dp)) {
                ConstraintLayout(constraintSet = constraintSet) {
                    NoteDetails(note = note)
                    NoteSidebar(note = note, downloadManager = downloadManager)
                    Vote(note = note)
                }
            }
        }
    }
}


@Composable
fun NoteDetails(note: FileUploadModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp)
            .layoutId("noteDetails")
    ) {
        Text(text = "Subject: ${note.subject} ")
        Spacer(modifier = Modifier.padding(2.dp))
        Text(text = "Format: ${note.fileInfo.fileMime}")
    }
}

@Composable
fun NoteSidebar(note: FileUploadModel, downloadManager: DownloadManager) {
    val currentUser = Firebase.auth.currentUser!!
    var downloadIconTint by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(2.dp)
            .layoutId("noteSidebar")
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Filled.Share, contentDescription = "Share")
        }

        IconButton(onClick = {
            favouriteDao(course = note)
        }) {
            Icon(
                Icons.Filled.Bookmark,
                contentDescription = "Favourite",
                tint = if (note.favourite.contains(currentUser.email)) Color.Blue else Color.Black
            )
        }

        IconButton(onClick = {
            noteDownloadDao(note = note, downloadManager = downloadManager)
            downloadIconTint = true
        }) {
            Icon(
                Icons.Filled.Download,
                contentDescription = "Download",
                tint = if (downloadIconTint) Color.Blue else Color.Black
            )
        }
    }
}

@Composable
fun Vote(note: FileUploadModel) {

    val currentUser = Firebase.auth.currentUser!!

    Row(
        modifier = Modifier
            .padding(2.dp)
            .layoutId("noteVote")
    ) {

        Text(
            text = note.likes.size.toString(),
            modifier = Modifier.padding(start = 8.dp, top = 12.dp)
        )

        IconButton(onClick = { likeDao(note = note) }) {
            Icon(
                Icons.Filled.Favorite,
                contentDescription = "Like",
                tint = if (note.likes.contains(currentUser.email)) Color.Red else Color.Black
            )
        }
    }
}


