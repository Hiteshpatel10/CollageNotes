package com.geekaid.collegenotes.components.noteLayoutComponents

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.geekaid.collegenotes.PdfActivity
import com.geekaid.collegenotes.components.favSpaceAlertBox
import com.geekaid.collegenotes.firebaseDao.noteLayoutDao.favouriteDao
import com.geekaid.collegenotes.firebaseDao.noteLayoutDao.reportDao
import com.geekaid.collegenotes.firebaseDao.noteLayoutDao.shareDao
import com.geekaid.collegenotes.model.FileUploadModel
import com.geekaid.collegenotes.util.Constants
import com.geekaid.collegenotes.viewmodel.DashboardViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.io.File

@ExperimentalFoundationApi
@Composable
fun NoteSideBar(note: FileUploadModel, context: Context, dashboardViewModel: DashboardViewModel) {
    val currentUser = Firebase.auth.currentUser!!


    var showAlertDialog by remember { mutableStateOf(false) }
    dashboardViewModel.notesType
    var bool by remember { mutableStateOf(false) }
    var moreBool by remember { mutableStateOf(false) }
    var reportAlertDialog by remember { mutableStateOf(false) }
    bool =
        note.favourite.contains("${currentUser.email}/fav1") || note.favourite.contains("${currentUser.email}/fav2") || note.favourite.contains(
            "${currentUser.email}/fav3"
        )
    val fileName = "${context.getExternalFilesDir(null)}/${note.fileInfo.fileName}.pdf"

    Column(
        modifier = Modifier
            .padding(2.dp)
            .layoutId("noteSidebar")
    ) {
        IconButton(onClick = { moreBool = true }) {
            Icon(Icons.Filled.MoreVert, contentDescription = "Share")
            if (moreBool)
                DropdownMenu(expanded = moreBool, onDismissRequest = { moreBool = false }) {

                    DropdownMenuItem(onClick = {
                        shareDao(note = note, context = context)
                        moreBool = false
                    }) {
                        Row {
                            Icon(Icons.Filled.Share, contentDescription = "Share")
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text(text = "Share")
                        }
                    }

                    DropdownMenuItem(onClick = {
                        reportAlertDialog = true
                        moreBool = false
                    }) {
                        Row {
                            Icon(Icons.Filled.ReportProblem, contentDescription = "Share")
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text(text = "Report")
                        }
                    }
                }
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
            if (File(fileName).exists())
                startActivity(
                    context,
                    Intent(context, PdfActivity::class.java).putExtra(
                        "fileName",
                        note.fileInfo.fileName
                    ), null
                )
            else
                Toast.makeText(context, "Download notes to open", Toast.LENGTH_SHORT).show()
        }) {
            Icon(
                Icons.Filled.Launch,
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
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(bottom = 12.dp)
                            .fillMaxWidth()
                    ) {
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
                }, modifier = Modifier.fillMaxWidth(0.8f)
            )
    }
}
