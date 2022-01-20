package com.geekaid.collegenotes.firebaseDao.authDao

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.geekaid.collegenotes.navigation.Screens
import com.geekaid.collegenotes.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth


fun emailVerificationDao(
    context: Context,
    navController: NavHostController,
    authViewModel: AuthViewModel
) {

    val auth = FirebaseAuth.getInstance()

    auth.currentUser!!.sendEmailVerification()
        .addOnCompleteListener {
            if (it.isSuccessful) {
                authViewModel.displayProgressBar.value = false
                Toast.makeText(context, "Email verification link send", Toast.LENGTH_SHORT)
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
