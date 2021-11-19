package com.geekaid.collagenotes.components

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.MainActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun signOutAlertDialog(isShow: Boolean, navController: NavHostController): Boolean {

    val auth = Firebase.auth
    var openDialog by remember { mutableStateOf(isShow) }
    val scope = rememberCoroutineScope()
    val activity = (LocalLifecycleOwner.current as ComponentActivity)
    val context = LocalContext.current

    if (!openDialog) return false

    AlertDialog(onDismissRequest = { openDialog = false },

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