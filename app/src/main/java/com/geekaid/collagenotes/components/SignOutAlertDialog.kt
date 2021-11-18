package com.geekaid.collagenotes.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.navigation.Screens
import com.google.firebase.auth.FirebaseAuth

@Composable
fun signOutAlertDialog(isShow: Boolean, navController: NavHostController): Boolean {

    val auth = FirebaseAuth.getInstance()
    var openDialog by remember { mutableStateOf(isShow) }

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
                        openDialog = false
                        auth.signOut().also {
                            navController.navigate(Screens.SignUpNav.route)
                        }
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