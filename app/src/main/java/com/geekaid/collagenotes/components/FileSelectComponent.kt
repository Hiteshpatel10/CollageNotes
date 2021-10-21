package com.geekaid.collagenotes.components

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FileSelectComponent(launcher: ManagedActivityResultLauncher<String, Uri>) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

        Column(modifier = Modifier.weight(1f)) {
            Text(text = "Select the file you want to upload")
        }

        Button(
            onClick = { launcher.launch("*/*") },
            modifier = Modifier.padding(bottom = 64.dp)
        ) {
            Text(text = "Select File")
        }
    }
}

