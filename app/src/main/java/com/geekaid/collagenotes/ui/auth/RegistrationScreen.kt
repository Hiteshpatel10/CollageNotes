package com.geekaid.collagenotes.ui.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun RegistrationScreen() {

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Enter Your Mobile Number",
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.SansSerif ,
            fontSize = 28.sp
        )
    }
}

@Preview
@Composable
fun RegistrationPrev() {
    RegistrationScreen()
}