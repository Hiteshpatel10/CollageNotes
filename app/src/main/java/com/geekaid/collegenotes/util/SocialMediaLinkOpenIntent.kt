package com.geekaid.collegenotes.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat

fun socialMediaLinkOpenIntent(
    context: Context,
    intentUri: String,
    packageName: String,
    webLink: String
) {

    val appIntent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse(intentUri)
    ).apply { setPackage(packageName) }

    val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(webLink))

    try {
        ContextCompat.startActivity(context, appIntent, null)
    } catch (e: ActivityNotFoundException) {
        try {
            ContextCompat.startActivity(context, webIntent, null)
        } catch (e: Exception) {
            Toast.makeText(context, "some error occurred", Toast.LENGTH_SHORT).show()
        }
    }
}