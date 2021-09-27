package com.geekaid.collagenotes.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.ui.auth.SignUpScreen

@Composable
fun UploadScreen(navController: NavHostController) {

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->

        val uriResult: Uri = Uri.parse(uri.toString())
        val contextResolver = context.contentResolver
        val mime = contextResolver.getType(uriResult)

    }
    
//    Button(onClick = { launcher.launch("*/*")}) {
//
//    }

    SignUpScreen(navController)
}