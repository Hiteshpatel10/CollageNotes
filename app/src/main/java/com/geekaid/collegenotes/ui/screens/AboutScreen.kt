package com.geekaid.collegenotes.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.geekaid.collegenotes.BuildConfig
import com.geekaid.collegenotes.R
import com.geekaid.collegenotes.util.socialMediaLinkOpenIntent
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat

import androidx.core.content.ContextCompat.startActivity
import timber.log.Timber
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.ContextCompat.startActivity
import com.geekaid.collegenotes.components.AppIconName


@Composable
fun AboutScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        val context = LocalContext.current

        AppIconName(spacerBottom = false)

        ClickableText(
            text = AnnotatedString("By: Geek App Lab"),
            onClick = {
                socialMediaLinkOpenIntent(
                    context = context,
                    packageName = "com.android.vending",
                    webLink = "https://play.google.com/store/apps/developer?id=Geek+App+Lab",
                    intentUri = "market://details?id=${context.packageName}"
                )
            },
            modifier = Modifier.alpha(0.6f)
        )

        Text(text = "version ${BuildConfig.VERSION_NAME}", modifier = Modifier.alpha(0.6f))

        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ClickableText(text = AnnotatedString("Contact Us: geekaid10@gmail.com"), onClick = {
                    try {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse("mailto:geekaid10@gmail.com?")
                        startActivity(context, intent, null)
                    } catch (e: Exception) {
                        Timber.i(e.message)
                    }
                })
            }
        }
    }


}
