package com.geekaid.collagenotes.components

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.layout.Box
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
fun SelectComponent(launcher: ManagedActivityResultLauncher<String, Uri>) {
    Column {

    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        Alignment.BottomCenter
    ) {
        Button(onClick = { launcher.launch("*/*") }) {
            Text(text = "Select File")
        }
    }
}

@Composable
fun UploadComponent(launcher: ManagedActivityResultLauncher<String, Uri>) {
    Column {

    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        Alignment.BottomCenter
    ) {
        Button(onClick = { launcher.launch("*/*") }) {
            Text(text = "Upload File")
        }
    }
}