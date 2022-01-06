package com.geekaid.collegenotes.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.geekaid.collegenotes.BuildConfig
import com.geekaid.collegenotes.R

@Composable
fun AboutScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = "Icon",
            modifier = Modifier
                .size(100.dp)
                .padding(8.dp)
        )

        Text(text = "Collage Notes")
        Text(text = "version ${BuildConfig.VERSION_NAME}", modifier = Modifier.alpha(0.6f))

        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Developed By : Geek Aid")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(text = "Contact Us: geekAid10@gmail.com")
            }
        }
    }


}
