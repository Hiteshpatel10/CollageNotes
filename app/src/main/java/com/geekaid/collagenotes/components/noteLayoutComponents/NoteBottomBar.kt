package com.geekaid.collagenotes.components.noteLayoutComponents

import android.app.DownloadManager
import android.content.Context
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.twotone.FileDownload
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.firebaseDao.noteLayoutDao.likeDao
import com.geekaid.collagenotes.firebaseDao.noteLayoutDao.noteDownloadDao
import com.geekaid.collagenotes.model.FileUploadModel
import com.geekaid.collagenotes.navigation.BottomNavScreen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun NoteBottomBar(
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
            text = AnnotatedString("UPLOADED BY : ${note.fileInfo.uploadedBy.uppercase()}"),
            onClick = {
                navController.navigate("${BottomNavScreen.UserProfileScreenNav.route}/${note.fileInfo.uploaderEmail}")
            },
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}