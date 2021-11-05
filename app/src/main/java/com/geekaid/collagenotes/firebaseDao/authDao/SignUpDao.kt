package com.geekaid.collagenotes.firebaseDao.authDao

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.model.SignUpModel
import com.geekaid.collagenotes.navigation.Screens
import com.google.firebase.auth.FirebaseAuth

fun validateSignUpData(context: Context, credential: SignUpModel): Boolean {

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

        credential.confirmPassword.isEmpty() -> Toast.makeText(
            context,
            "Confirm Password can't be empty",
            Toast.LENGTH_SHORT
        ).show()

        credential.password.isNotEmpty() -> {
            if (credential.password != credential.confirmPassword)
                Toast.makeText(context, "Passwords don't match", Toast.LENGTH_SHORT).show()
            else
                return true
        }
    }
    return false
}

fun registerUser(context: Context, credential: SignUpModel, navController: NavHostController) {
    val auth = FirebaseAuth.getInstance()

    if (validateSignUpData(context, credential)) {
        auth.createUserWithEmailAndPassword(credential.email, credential.password)
            .addOnSuccessListener {
                Toast.makeText(context, "Registered Successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
            }
            .addOnCompleteListener {
                if(auth.currentUser!!.isEmailVerified){
                    navController.navigate(Screens.DashboardNav.route)
                }else{
                    navController.navigate(Screens.EmailVerificationNav.route)
                }
            }
    }
}
