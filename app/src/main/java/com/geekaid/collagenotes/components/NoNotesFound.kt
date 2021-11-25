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
fun NoNotesFound(
    buttonText: String,
    displayText: String,
    buttonDisplay: Boolean,
    navController: NavHostController
) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(Icons.Filled.Info, contentDescription = "Info", Modifier.size(40.dp))
            Text(text = displayText, style = MaterialTheme.typography.h5)
        }

        if (buttonDisplay) {
            Button(
                onClick = { navController.navigate(Screens.FilterNav.route) },
                modifier = Modifier.padding(bottom = 64.dp)
            ) {
                Text(text = buttonText)
            }
        }else{
            Spacer(modifier = Modifier.padding(top = 34.dp ,bottom = 64.dp))
        }
    }
}

