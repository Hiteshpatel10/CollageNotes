package com.geekaid.collegenotes.components.noteLayoutComponents

import android.Manifest
import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.geekaid.collegenotes.components.InterstitialAdShow
import com.geekaid.collegenotes.components.permissionsComposable.isPermanentlyDenied
import com.geekaid.collegenotes.firebaseDao.noteLayoutDao.likeDao
import com.geekaid.collegenotes.firebaseDao.noteLayoutDao.noteDownloadDao
import com.geekaid.collegenotes.model.FileUploadModel
import com.geekaid.collegenotes.navigation.BottomNavScreen
import com.geekaid.collegenotes.viewmodel.DashboardViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@ExperimentalFoundationApi
@ExperimentalPermissionsApi
@Composable
fun NoteBottomBar(
    note: FileUploadModel,
    context: Context,
    downloadManager: DownloadManager,
    navController: NavHostController,
    dashboardViewModel: DashboardViewModel
) {

    val currentUser = Firebase.auth.currentUser!!
    val activity = LocalContext.current as Activity
    val permissionState =
        rememberPermissionState(permission = Manifest.permission.READ_EXTERNAL_STORAGE)
    var permissionStateSnackBar by remember { mutableStateOf(false) }

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
            if (permissionState.hasPermission) {
                InterstitialAdShow.showInterstitialAd(
                    activity = activity,
                    adUnitId = "ca-app-pub-3017813434968451/9745122372",
                    dashboardViewModel = dashboardViewModel
                )
                noteDownloadDao(note = note, context = context, downloadManager = downloadManager)
                downloadIconTint = true
            } else {
                permissionState.launchPermissionRequest()
            }

            if (!permissionState.hasPermission)
                permissionStateSnackBar = true
        }) {
            Icon(
                Icons.TwoTone.FileDownload,
                contentDescription = "Downloaded Times",
                tint = if (downloadIconTint) Color.Blue else Color.Black
            )
        }

        ClickableText(
            text = AnnotatedString("BY : ${note.fileInfo.uploadedBy.uppercase()}"),
            onClick = {
                navController.navigate("${BottomNavScreen.UserProfileScreenNav.route}/${note.fileInfo.uploaderEmail}")
            },
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(start = 4.dp)
        )
    }

    if (permissionStateSnackBar) {
        when (permissionState.permission) {

            Manifest.permission.WRITE_EXTERNAL_STORAGE -> {
                when {
                    permissionState.shouldShowRationale -> {
                        Toast.makeText(
                            context,
                            "Storage permission is needed to download notes.",
                            Toast.LENGTH_LONG
                        ).show()
                        permissionStateSnackBar = false
                    }

                    permissionState.isPermanentlyDenied() -> {
                        Toast.makeText(
                            context,
                            "Storage permission is permanently denied. \n You can enable it in the app settings.",
                            Toast.LENGTH_LONG
                        ).show()
                        permissionStateSnackBar = false
                    }
                }
            }
        }
    }

}