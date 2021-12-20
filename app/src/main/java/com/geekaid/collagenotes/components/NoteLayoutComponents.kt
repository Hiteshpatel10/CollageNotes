package com.geekaid.collagenotes.components

import android.app.DownloadManager
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.twotone.FileDownload
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.firebaseDao.noteLayoutDao.*
import com.geekaid.collagenotes.model.FileUploadModel
import com.geekaid.collagenotes.navigation.Screens
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun NoteDetails(note: FileUploadModel, isExpanded: Boolean) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp)
            .layoutId("noteDetails"),
    ) {

        if (isExpanded) {
            HeadingValueStyle(heading = "Format", value = note.fileInfo.fileMime, isSpacer = true)
            HeadingValueStyle(heading = "Course", value = note.course, isSpacer = true)
            HeadingValueStyle(heading = "Branch", value = note.branch, isSpacer = true)
            HeadingValueStyle(heading = "Subject", value = note.subject, isSpacer = true)
            HeadingValueStyle(heading = "Upload Date", value = note.date, isSpacer = true)
            HeadingValueStyle(
                heading = "Description",
                value = note.fileInfo.fileDescription,
                isSpacer = true
            )
            Spacer(modifier = Modifier.padding(24.dp))
        } else {
            HeadingValueStyle(heading = "Format", value = note.fileInfo.fileMime, isSpacer = true)
            HeadingValueStyle(heading = "Subject", value = note.subject, isSpacer = true)
            HeadingValueStyle(heading = "Upload Date", value = note.date, isSpacer = true)
            HeadingValueStyle(
                heading = "Description",
                value = note.fileInfo.fileDescription,
                isSpacer = true
            )
        }
    }
}

@Composable
fun NoteSidebar(note: FileUploadModel, context: Context) {
    val currentUser = Firebase.auth.currentUser!!

    Column(
        modifier = Modifier
            .padding(2.dp)
            .layoutId("noteSidebar")
    ) {
        IconButton(onClick = { shareDao(note = note, context = context) }) {
            Icon(Icons.Filled.Share, contentDescription = "Share")
        }

        IconButton(onClick = {
            favouriteDao(note = note)
        }) {
            Icon(
                Icons.Filled.Bookmark,
                contentDescription = "Favourite",
                tint = if (note.favourite.contains(currentUser.email)) Color.Blue else Color.Black
            )
        }

        IconButton(onClick = {
            reportDao(note = note, context = context)
        }) {
            Icon(
                Icons.Filled.Report,
                contentDescription = "Download",
            )
        }

    }
}

@Composable
fun Vote(
    note: FileUploadModel,
    context: Context,
    downloadManager: DownloadManager,
    navController: NavHostController
) {

    val currentUser = Firebase.auth.currentUser!!
    var downloadIconTint by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(2.dp)
            .layoutId("noteVote"),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = note.likes.size.toString(),
            modifier = Modifier.padding(start = 8.dp)
        )

        IconButton(onClick = { likeDao(note = note) }) {
            Icon(
                Icons.Filled.Favorite,
                contentDescription = "Like",
                tint = if (note.likes.contains(currentUser.email)) Color.Red else Color.Black
            )
        }

        Text(
            text = note.downloadedTimes.toString(),
        )

        IconButton(onClick = {
            noteDownloadDao(note = note, context = context, downloadManager = downloadManager)
            downloadIconTint = true
        }) {
            Icon(
                Icons.TwoTone.FileDownload,
                contentDescription = "Downloaded Times",
                tint = if (downloadIconTint) Color.Blue else Color.Black
            )
        }

        ClickableText(
            text = AnnotatedString("UPLOADED BY : ${note.fileInfo.uploadedBy}"),
            onClick = {
                navController.navigate("${Screens.UserProfileScreenNav.route}/${note.fileInfo.uploaderEmail}")
            },
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}