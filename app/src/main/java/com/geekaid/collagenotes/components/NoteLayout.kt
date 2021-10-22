package com.geekaid.collagenotes.components

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
import com.geekaid.collagenotes.model.FileUploadModel

@Composable
fun NoteLayout(course: ArrayList<FileUploadModel>) {

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
        items(course) { course ->
            Card(modifier = Modifier.padding(4.dp)) {
                ConstraintLayout(constraintSet = constraintSet) {
                    NoteDetails()
                    NoteSidebar(course = course)
                    Vote()
                }
            }
        }
    }
}


@Composable
fun NoteDetails() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp)
            .layoutId("noteDetails")
    ) {
        Text(text = "Subject: Computer Science ")
        Spacer(modifier = Modifier.padding(2.dp))
        Text(text = "Format: Pdf ")
    }
}

@Composable
fun NoteSidebar(course: FileUploadModel) {
    Column(
        modifier = Modifier
            .padding(2.dp)
            .layoutId("noteSidebar")
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Filled.Share, contentDescription = "Share")
        }

        IconButton(onClick = {
            favouriteDao(course = course)
        }) {
            Icon(
                Icons.Filled.Favorite,
                contentDescription = "Favourite",
                tint = if (course.fav) Color.Red else Color.Black
            )
        }

        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Filled.Download, contentDescription = "Download")
        }
    }
}

@Composable
fun Vote() {
    Row(
        modifier = Modifier
            .padding(2.dp)
            .layoutId("noteVote")
    ) {

        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Filled.ThumbUp, contentDescription = "Upvote")
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Filled.ThumbDown, contentDescription = "Upvote")
        }
    }
}


