package com.geekaid.collagenotes.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.components.SelectComponent
import com.geekaid.collagenotes.components.UploadComponent

@Composable
fun UploadScreen(navController: NavHostController) {

    val context = LocalContext.current
    var buttonText by remember { mutableStateOf("Select File")}
    var bool by remember { mutableStateOf(true)}
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->

        bool = false
        val uriResult: Uri = Uri.parse(uri.toString())
        val contextResolver = context.contentResolver
        val mime = contextResolver.getType(uriResult)
    }

    if (bool) {
        SelectComponent(launcher)
    } else {
        UploadComponent(launcher = launcher)
    }

}