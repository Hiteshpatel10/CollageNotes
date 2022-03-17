package com.geekaid.collegenotes.components.permissionsComposable

import android.Manifest
import android.widget.Toast
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState


@ExperimentalPermissionsApi
@Composable
fun RequestPermissionComposable() {

    val permissionState = rememberPermissionState(permission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    val context = LocalContext.current

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    when(permissionState.permission){

        Manifest.permission.WRITE_EXTERNAL_STORAGE -> {
            when{
                permissionState.hasPermission -> {
                    Toast.makeText(context,"permission accepted", Toast.LENGTH_SHORT).show()
                }

                permissionState.shouldShowRationale -> {
                    Toast.makeText(context,"permission needed", Toast.LENGTH_SHORT).show()
                }

                permissionState.isPermanentlyDenied() -> {
                    Toast.makeText(context,"permission settings", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }





}