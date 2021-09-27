package com.geekaid.collagenotes.ui.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.navigation.Screens

@Composable
fun OTPVerifyScreen(navController: NavHostController) {

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
            text = "9602189944",
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily.SansSerif,
            fontSize = 28.sp,
            modifier = Modifier.padding(top = 100.dp)
        )

        Icon(Icons.Filled.Edit, contentDescription = "Edit Mobile No", Modifier.clickable {
            navController.navigate(Screens.SignUpNav.route)
        })


        OutlinedTextField(
            value = otp,
            onValueChange = { otp = it },
            label = { Text(text = "Enter OTP") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )

        Button(
            onClick = { /*TODO*/ }, modifier = Modifier
                .padding(8.dp)
        ) {
            Text(text = "Verify")
        }

        Row() {
            Text(text = "Didn't receive OTP? ")

            ClickableText(text = AnnotatedString("Resend OTP"), onClick = {})
        }
    }
}