package com.geekaid.collagenotes.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.geekaid.collagenotes.components.NoteLayout

@Composable
fun DashboardScreen() {
    Column(modifier = Modifier.padding(4.dp)) {
        NoteLayout(modifier = Modifier)
    }
}