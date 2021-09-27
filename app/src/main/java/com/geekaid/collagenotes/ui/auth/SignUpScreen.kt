package com.geekaid.collagenotes.ui.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.navigation.Screens

@Composable
fun SignUpScreen(navController: NavHostController) {

    var otp by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Enter Your Mobile Number",
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.SansSerif,
            fontSize = 28.sp,
            modifier = Modifier.padding(top = 100.dp)
        )

        Text(
            text = "We will send you one time password",
            fontWeight = FontWeight.Light,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier.padding(8.dp)
        )

        OutlinedTextField(
            value = otp,
            onValueChange = { otp = it },
            label = { Text(text = "Mobile No") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )

        Button(
            onClick = {
                      navController.navigate(Screens.OTPVerifyNav.route)
            }, modifier = Modifier
                .padding(8.dp)
        ) {
            Text(text = "Send OTP")
        }
    }
}
