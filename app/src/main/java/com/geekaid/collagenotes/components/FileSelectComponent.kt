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
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(30.dp),
            verticalArrangement = Arrangement.Center,
        ) {

            Text(text = "Rules", style = MaterialTheme.typography.h3)
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = "1. Select the file you want to upload",
                style = MaterialTheme.typography.body2
            )
            Text(
                text = "2. Upload the file with appropriate name",
                style = MaterialTheme.typography.body2
            )
            Text(
                text = "3. Upload the file with appropriate name",
                style = MaterialTheme.typography.body2
            )
            Text(
                text = "4. provide a appropriate description for the file uploaded",
                style = MaterialTheme.typography.body2
            )
        }

        Button(
            onClick = { launcher.launch("*/*") },
            modifier = Modifier.padding(bottom = 64.dp)
        ) {
            Text(text = "Select File")
        }
    }
}

