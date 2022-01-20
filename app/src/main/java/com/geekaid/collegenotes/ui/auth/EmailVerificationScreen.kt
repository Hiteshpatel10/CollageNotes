package com.geekaid.collegenotes.ui.auth

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
import com.geekaid.collegenotes.components.AppIconName
import com.geekaid.collegenotes.components.ProgressBar
import com.geekaid.collegenotes.firebaseDao.authDao.emailVerificationDao
import com.geekaid.collegenotes.viewmodel.AuthViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun EmailVerificationScreen(navController: NavHostController, authViewModel: AuthViewModel) {

    val auth = Firebase.auth
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(top = 60.dp)
            .padding(10.dp)
    ) {

        AppIconName()

        Text(
            text = auth.currentUser?.email.toString(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.Center
        )

        ProgressBar(isDisplay = authViewModel.displayProgressBar.value)

        Button(
            onClick = {
                authViewModel.displayProgressBar.value = true
                emailVerificationDao(context, navController, authViewModel)
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