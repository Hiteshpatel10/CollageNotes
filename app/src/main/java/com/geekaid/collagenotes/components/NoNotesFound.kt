package com.geekaid.collagenotes.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.navigation.Screens

@Composable
fun NoNotesFound(navController: NavHostController, buttonDisplay: Boolean) {
    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        ) {
        Icon(Icons.Filled.Info, contentDescription = "Info", Modifier.size(40.dp))
        Text(text = "No Notes Found", style = MaterialTheme.typography.h5)

        if (buttonDisplay) {
            Spacer(modifier = Modifier.padding(8.dp))
            Button(onClick = { navController.navigate(Screens.FilterNav.route) }) {
                Text(text = "Change Filter")
            }
        }
    }
}

