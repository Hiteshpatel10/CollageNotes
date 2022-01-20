package com.geekaid.collegenotes.components

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.geekaid.collegenotes.util.Constants

@Composable
fun FileSelectComponent(launcher: ManagedActivityResultLauncher<String, Uri?>) {

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(30.dp),
            verticalArrangement = Arrangement.Center,
        ) {

            Text(text = "Rules", style = MaterialTheme.typography.h3)
            Spacer(modifier = Modifier.padding(4.dp))

            Constants.rulesList.forEachIndexed { index, rule ->
                Text(
                    text = "${index + 1}. $rule",
                    style = MaterialTheme.typography.body2
                )
                Spacer(modifier = Modifier.padding(2.dp))
            }

        }

        Button(
            onClick = { launcher.launch("*/*") },
            modifier = Modifier.padding(bottom = 64.dp)
        ) {
            Text(text = "Select File")
        }
    }
}

