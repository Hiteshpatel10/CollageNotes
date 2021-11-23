package com.geekaid.collagenotes.components

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FileSelectComponent(launcher: ManagedActivityResultLauncher<String, Uri>) {

    val rulesList = listOf(
        "Select the file you want to upload",
        "Upload the file with appropriate name",
        "Select the course, branch and subject of notes uploaded",
        "provide a appropriate description for the file uploaded",
        "Do not upload inappropriate content",
    )

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(30.dp),
            verticalArrangement = Arrangement.Center,
        ) {

            Text(text = "Rules", style = MaterialTheme.typography.h3)
            Spacer(modifier = Modifier.padding(4.dp))

            rulesList.forEachIndexed { index,  rule ->
                Text(
                    text = "${index+1}. $rule",
                    style = MaterialTheme.typography.body2
                )
                Spacer(modifier = Modifier.padding(2.dp))
            }

        }

        Button(
            onClick = { launcher.launch("*/*") },
            modifier = Modifier.padding(bottom = 64.dp)
        ) {
            Text(text = "Select File")
        }
    }
}

