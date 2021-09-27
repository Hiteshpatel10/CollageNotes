package com.geekaid.collagenotes.firebaseDao.authDao

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.navigation.Screens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

fun forgotPasswordDao(context: Context, navController: NavHostController) {
    val auth = Firebase.auth

    FirebaseAuth.getInstance().sendPasswordResetEmail(auth.currentUser.toString())
        .addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(context, "Reset Link Send", Toast.LENGTH_LONG)
                    .show().also {
                        navController.navigate(Screens.SignInNav.route)
                    }
            }
        }
        .addOnFailureListener {
            Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_LONG)
                .show()
        }
}