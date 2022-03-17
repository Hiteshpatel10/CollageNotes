package com.geekaid.collegenotes.components.permissionsComposable

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState


@ExperimentalPermissionsApi
fun PermissionState.isPermanentlyDenied() : Boolean {
    return !shouldShowRationale && !hasPermission
}