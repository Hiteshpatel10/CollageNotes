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
import androidx.compose.material.icons.filled.Password
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
import com.geekaid.collagenotes.firebaseDao.authDao.registerUser
import com.geekaid.collagenotes.model.SignUpModel
import com.geekaid.collagenotes.model.UserDetails
import com.geekaid.collagenotes.navigation.Screens
import com.geekaid.collagenotes.viewmodel.AuthViewModel

@Composable
fun SignUpScreen(navController: NavHostController, authViewModel: AuthViewModel) {

    var email by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passwordVisibility by remember { mutableStateOf(false) }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }

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
            leadingIcon = {
                Icon(Icons.Filled.Email, contentDescription = "Email")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text(text = "First Name") },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(8.dp)
            )

            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text(text = "Last Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            leadingIcon = { Icon(Icons.Filled.Password, contentDescription = "password") },
            trailingIcon = { passwordVisibility = passwordVisible() },
            visualTransformation = if (passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            leadingIcon = { Icon(Icons.Filled.Password, contentDescription = "password") },
            trailingIcon = { confirmPasswordVisibility = passwordVisible() },
            visualTransformation = if (confirmPasswordVisibility) VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        ProgressBar(isDisplay = authViewModel.displayProgressBar.value)

        Button(
            onClick = {
                registerUser(
                    context,
                    SignUpModel(
                        email,
                        password,
                        confirmPassword,
                        userDetails = UserDetails(firstName = firstName, lastName = lastName)
                    ),
                    navController,
                    authViewModel
                )
            },
            contentPadding = PaddingValues(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        {
            Text(text = "Sign Up")
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
                Text(text = "Already have an account?")
                ClickableText(
                    text = AnnotatedString(" Log In"),
                    onClick = {
                        navController.navigate(Screens.SignInNav.route)
                    }
                )
            }
        }
    }
}