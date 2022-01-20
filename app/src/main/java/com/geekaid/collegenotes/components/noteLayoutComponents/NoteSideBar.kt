package com.geekaid.collegenotes.components.noteLayoutComponents

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.geekaid.collegenotes.components.favSpaceAlertBox
import com.geekaid.collegenotes.firebaseDao.noteLayoutDao.favouriteDao
import com.geekaid.collegenotes.firebaseDao.noteLayoutDao.reportDao
import com.geekaid.collegenotes.firebaseDao.noteLayoutDao.shareDao
import com.geekaid.collegenotes.model.FileUploadModel
import com.geekaid.collegenotes.util.Constants
import com.geekaid.collegenotes.viewmodel.DashboardViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@ExperimentalFoundationApi
@Composable
fun NoteSideBar(note: FileUploadModel, context: Context, dashboardViewModel: DashboardViewModel) {
    val currentUser = Firebase.auth.currentUser!!
    var showAlertDialog by remember { mutableStateOf(false) }
    dashboardViewModel.notesType
    var bool by remember { mutableStateOf(false) }
    var reportAlertDialog by remember { mutableStateOf(false) }
    bool =
        note.favourite.contains("${currentUser.email}/fav1") || note.favourite.contains("${currentUser.email}/fav2") || note.favourite.contains(
            "${currentUser.email}/fav3"
        )

    Column(
        modifier = Modifier
            .padding(2.dp)
            .layoutId("noteSidebar")
    ) {
        IconButton(onClick = { shareDao(note = note, context = context) }) {
            Icon(Icons.Filled.Share, contentDescription = "Share")
        }

        IconButton(
            onClick = {
                if (bool) {
                    var favSpace = ""
                    Constants.favSpaces.forEach { favName ->
                        if (note.favourite.contains("${currentUser.email}/${favName}")) {
                            favSpace = favName
                        }
                    }
                    favouriteDao(note = note, favSpaceName = favSpace)
                } else
                    showAlertDialog = true
            },
        ) {
            Icon(
                Icons.Filled.Bookmark,
                contentDescription = "Favourite",
                tint = if (bool) Color.Blue else Color.Black
            )
        }

        IconButton(onClick = {
            reportAlertDialog = true
        }) {
            Icon(
                Icons.Filled.Report,
                tint = Color.Red,
                contentDescription = "Report",
            )
        }

        if (showAlertDialog)
            showAlertDialog = favSpaceAlertBox(
                showAlertBox = showAlertDialog,
                note = note
            )

        if (reportAlertDialog)
            AlertDialog(
                onDismissRequest = { reportAlertDialog = false },
                text = {
                    Text(
                        text = "Report Note",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                buttons = {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(bottom = 12.dp).fillMaxWidth()) {
                        Row {
                            Button(
                                onClick = { reportDao(note = note, context = context) },
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Text(text = "Report")
                            }

                            Button(
                                onClick = { reportAlertDialog = false },
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Text(text = "Cancel")
                            }
                        }
                    }
                }, modifier = Modifier.fillMaxWidth(0.8f))
    }
}
