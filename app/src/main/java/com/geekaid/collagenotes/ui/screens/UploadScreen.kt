package com.geekaid.collagenotes.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.geekaid.collagenotes.ui.auth.RegistrationScreen

@Composable
fun UploadScreen() {

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->

        val uriResult: Uri = Uri.parse(uri.toString())
        val contextResolver = context.contentResolver
        val mime = contextResolver.getType(uriResult)

    }
    
//    Button(onClick = { launcher.launch("*/*")}) {
//
//    }

    RegistrationScreen()
}