package com.geekaid.collagenotes.firebaseDao.authDao

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.navigation.Screens
import com.google.firebase.auth.FirebaseAuth
import timber.log.Timber


fun emailVerificationDao(context: Context, navController: NavHostController) {

    val auth = FirebaseAuth.getInstance()

    auth.currentUser!!.sendEmailVerification()
        .addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(context, "Email verification link send", Toast.LENGTH_SHORT)
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
