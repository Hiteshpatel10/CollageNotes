package com.geekaid.collagenotes.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.components.FileSelectComponent
import com.geekaid.collagenotes.components.FileUploadComponent

@Composable
fun UploadScreen(navController: NavHostController) {

    val context = LocalContext.current
    var bool by remember { mutableStateOf(true) }
    var noteUri by remember { mutableStateOf("") }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        bool = false
        noteUri = uri.toString()
    }

    if (bool) {
        FileSelectComponent(launcher = launcher)
    } else {
        FileUploadComponent(noteUri = noteUri, context = context, navController = navController)
    }

}