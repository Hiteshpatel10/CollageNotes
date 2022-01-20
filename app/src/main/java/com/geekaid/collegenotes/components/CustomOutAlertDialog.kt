package com.geekaid.collegenotes.components

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.geekaid.collegenotes.MainActivity
import com.geekaid.collegenotes.firebaseDao.noteLayoutDao.favouriteDao
import com.geekaid.collegenotes.model.FileUploadModel
import com.geekaid.collegenotes.util.Constants
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun signOutAlertDialog(isShow: Boolean): Boolean {

    val auth = Firebase.auth
    var openDialog by remember { mutableStateOf(isShow) }
    val activity = (LocalLifecycleOwner.current as ComponentActivity)
    val context = LocalContext.current

    if (!openDialog) return false

    AlertDialog(

        onDismissRequest = { openDialog = false },

        text = {
            Text(
                text = "Sign out of Collage Notes",
                modifier = Modifier.padding(4.dp),
                textAlign = TextAlign.Center
            )
        },

        buttons = {
            Row(modifier = Modifier.padding(8.dp)) {
                Button(
                    onClick = {
                        auth.signOut()
                        openDialog = false
                        activity.finish()
                        startActivity(context, Intent(context, MainActivity::class.java), null)
                    },
                    modifier = Modifier.padding(6.dp)
                ) {
                    Text(text = "Sign Out", modifier = Modifier.padding(4.dp))
                }

                Button(
                    onClick = { openDialog = false },
                    modifier = Modifier.padding(6.dp)
                ) {
                    Text(text = "Cancel", modifier = Modifier.padding(4.dp))
                }
            }
        }
    )

    return openDialog
}

@Composable
fun favSpaceAlertBox(
    showAlertBox: Boolean,
    note: FileUploadModel,
): Boolean {

    var openDialog by remember { mutableStateOf(showAlertBox) }

    if (!openDialog) return false

    AlertDialog(
        onDismissRequest = { openDialog = false },

        text = {
            Text(
                text = "Select Favourite Space",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },

        buttons = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(start = 4.dp, bottom = 16.dp, end = 4.dp)
                    .fillMaxWidth()
            ) {
                Row {
                    Constants.favSpaces.forEach { favSpaceName ->
                        Button(onClick = {
                            favouriteDao(note = note, favSpaceName = favSpaceName)
                            openDialog = false
                        }, modifier = Modifier.padding(12.dp)) {
                            Text(text = favSpaceName)
                        }
                    }
                }

                Button(onClick = { openDialog = false }, modifier = Modifier.padding(12.dp)) {
                    Text(text = "cancel")
                }
            }
        })

    return openDialog
}