package com.geekaid.collagenotes.firebaseDao.authDao

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.model.SignInModel
import com.geekaid.collagenotes.navigation.Screens
import com.geekaid.collagenotes.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth

fun validateSignInData(context: Context, credential: SignInModel): Boolean {

    when {
        credential.email.isEmpty() -> Toast.makeText(
            context,
            "Email can't be empty",
            Toast.LENGTH_SHORT
        ).show()

        credential.password.isEmpty() -> Toast.makeText(
            context,
            "Password can't be empty",
            Toast.LENGTH_SHORT
        ).show()

        credential.email.isNotEmpty() -> {
            if (credential.password.isNotEmpty())
                return true
        }
    }

    return false
}

fun signInUser(
    context: Context,
    navController: NavHostController,
    authViewModel: AuthViewModel,
    credential: SignInModel
) {
    val auth = FirebaseAuth.getInstance()

    if (validateSignInData(context, credential)) {

        authViewModel.displayProgressBar.value = true

        auth.signInWithEmailAndPassword(credential.email, credential.password)
            .addOnFailureListener {
                authViewModel.displayProgressBar.value = false
                Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
            }
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    authViewModel.displayProgressBar.value = false
                    Toast.makeText(context, "SignIn Successfully", Toast.LENGTH_SHORT).show()
                    navController.navigate(Screens.DashboardNav.route) {
                        navController.popBackStack()
                    }
                }
            }
    }
}