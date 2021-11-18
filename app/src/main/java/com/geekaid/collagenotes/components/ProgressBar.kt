package com.geekaid.collagenotes.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBar(isDisplay: Boolean) {

    if (isDisplay) {
        Box(
            modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(4.dp),
            Alignment.Center
        ) {
            CircularProgressIndicator(color = MaterialTheme.colors.primary, strokeWidth = 4.dp)
        }
    }
}
