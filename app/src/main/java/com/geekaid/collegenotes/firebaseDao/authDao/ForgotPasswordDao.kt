package com.geekaid.collegenotes.firebaseDao.authDao

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.geekaid.collegenotes.navigation.Screens
import com.geekaid.collegenotes.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth

fun forgotPasswordDao(
    email: String,
    context: Context,
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    FirebaseAuth.getInstance().sendPasswordResetEmail(email.toString())
        .addOnCompleteListener {
            if (it.isSuccessful) {
                authViewModel.displayProgressBar.value = false
                Toast.makeText(context, "Reset Link Send", Toast.LENGTH_LONG)
                    .show().also {
                        navController.navigate(Screens.SignInNav.route)
                    }
            }
        }
        .addOnFailureListener {
            authViewModel.displayProgressBar.value = false
            Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_LONG)
                .show()
        }
}