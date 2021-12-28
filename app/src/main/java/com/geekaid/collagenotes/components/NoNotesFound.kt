package com.geekaid.collagenotes.components

import com.geekaid.collagenotes.R
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.navigation.Screens

@Composable
fun NoNotesFound(
    buttonText: String,
    displayText: String,
    painter: Painter,
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
            Image(painter = painter, contentDescription ="" )
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

