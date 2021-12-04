package com.geekaid.collagenotes.firebaseDao.authDao

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.navigation.Screens
import com.geekaid.collagenotes.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

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