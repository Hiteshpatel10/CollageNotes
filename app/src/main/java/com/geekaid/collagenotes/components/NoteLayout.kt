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
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.geekaid.collagenotes.firebaseDao.noteLayoutDao.favouriteDao
import com.geekaid.collagenotes.firebaseDao.noteLayoutDao.feelingsDislikeDao
import com.geekaid.collagenotes.firebaseDao.noteLayoutDao.feelingsLikeDao
import com.geekaid.collagenotes.firebaseDao.noteLayoutDao.noteDownloadDao
import com.geekaid.collagenotes.model.FileUploadModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun NoteLayout(notes: ArrayList<FileUploadModel>, downloadManager: DownloadManager) {

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

    LazyColumn {
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
        Text(text = "Format: ${note.fileMime}")
    }
}

@Composable
fun NoteSidebar(note: FileUploadModel, downloadManager: DownloadManager) {
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
                Icons.Filled.Favorite,
                contentDescription = "Favourite",
                tint = if (note.fav) Color.Red else Color.Black
            )
        }

        IconButton(onClick = { noteDownloadDao(note = note, downloadManager = downloadManager) }) {
            Icon(Icons.Filled.Download, contentDescription = "Download")
        }
    }
}

@Composable
fun Vote(note: FileUploadModel) {

    val currentUser = Firebase.auth.currentUser!!.email
    Row(
        modifier = Modifier
            .padding(2.dp)
            .layoutId("noteVote")
    ) {

        IconButton(onClick = { feelingsLikeDao(note) }) {
            Icon(
                Icons.Filled.ThumbUp,
                contentDescription = "Upvote",
                tint = if (note.likes.contains(currentUser)) Color.Blue else Color.Black
            )
        }

        IconButton(onClick = { feelingsDislikeDao(note) }) {
            Icon(
                Icons.Filled.ThumbDown,
                contentDescription = "Upvote",
                tint = if (note.dislikes.contains(currentUser)) Color.Blue else Color.Black
            )
        }
    }
}


