package com.geekaid.collagenotes.ui.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.firebaseDao.authDao.emailVerificationDao
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun EmailVerificationScreen(navController: NavHostController) {

    val auth = Firebase.auth
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(top = 240.dp)
            .padding(10.dp)
    ) {

        Text(
            text = auth.currentUser?.email.toString(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.Center
        )

        Button(
            onClick = {
                emailVerificationDao(context, navController)
            },
            contentPadding = PaddingValues(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        {
            Text(text = "Verify Email")
        }
    }
}