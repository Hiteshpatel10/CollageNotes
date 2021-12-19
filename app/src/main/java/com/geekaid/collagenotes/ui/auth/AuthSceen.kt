package com.geekaid.collagenotes.ui.auth

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.geekaid.collagenotes.util.AuthResultContract
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun AuthScreen() {

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val signInRequestCode = 1
    var text by remember { mutableStateOf<String?>(null)}

    val authResultLauncher =
        rememberLauncherForActivityResult(contract = AuthResultContract()) { task ->

            try {
                val account = task?.getResult(ApiException::class.java)
                if (account == null) {
                    text = "Google Singin Failed"
                }else{
                    coroutineScope.launch {
                        Timber.i("${account.email} ${account.displayName}")
                    }
                }

            } catch (e: Exception) {
                text = e.message
            }
        }



    Button(onClick = {
        authResultLauncher.launch(signInRequestCode)
    }) {
        Text(text = "Google Sign in")
    }
}