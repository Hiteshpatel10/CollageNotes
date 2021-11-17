package com.geekaid.collagenotes.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.components.ProgressBar
import com.geekaid.collagenotes.components.passwordVisible
import com.geekaid.collagenotes.firebaseDao.authDao.signInUser
import com.geekaid.collagenotes.model.SignInModel
import com.geekaid.collagenotes.navigation.Screens
import com.geekaid.collagenotes.viewmodel.AuthViewModel

@Composable
fun SignInScreen(navController: NavHostController, authViewModel: AuthViewModel) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }


    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(top = 180.dp)
            .padding(10.dp)
    ) {


        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            trailingIcon = {
                Icon(Icons.Filled.Email, contentDescription = "Email")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            trailingIcon = {
                passwordVisibility = passwordVisible()
            },
            visualTransformation = if (passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.padding(8.dp))

        ProgressBar(isDisplay = authViewModel.displayProgressBar.value)

        Button(
            onClick = {
                signInUser(context, navController, authViewModel ,SignInModel(email, password))
            },
            contentPadding = PaddingValues(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        {
            Text(text = "Log In")
        }

        Row(
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Forgotten your login details?")
            ClickableText(
                text = AnnotatedString(" Get help Signing in"),
                onClick = {
                    navController.navigate(Screens.ForgotPasswordNav.route)
                }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            Alignment.BottomCenter
        ) {
            Row(
                modifier = Modifier
                    .padding(4.dp)
            ) {
                Text(text = "Don't have an account?")
                ClickableText(
                    text = AnnotatedString(" Sign Up"),
                    onClick = {
                        navController.navigate(Screens.SignUpNav.route)
                    }
                )
            }
        }

    }
}