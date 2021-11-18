package com.geekaid.collagenotes.firebaseDao.authDao

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.model.SignUpModel
import com.geekaid.collagenotes.navigation.Screens
import com.geekaid.collagenotes.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

fun validateSignUpData(context: Context, credential: SignUpModel): Boolean {

    when {
        credential.email.isEmpty() -> Toast.makeText(
            context,
            "Email can't be empty",
            Toast.LENGTH_SHORT
        ).show()

        credential.userDetails.firstName.isEmpty() -> Toast.makeText(
            context,
            "First Name can't be empty",
            Toast.LENGTH_SHORT
        ).show()

        credential.userDetails.lastName.isEmpty() -> Toast.makeText(
            context,
            "Lase Name can't be empty",
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

fun registerUser(
    context: Context,
    credential: SignUpModel,
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    val storageRef = db.collection("Users").document(credential.email)
        .collection("UserData").document("UserInfo")

    if (validateSignUpData(context, credential)) {
        authViewModel.displayProgressBar.value = true
        auth.createUserWithEmailAndPassword(credential.email, credential.password)
            .addOnSuccessListener {
                authViewModel.displayProgressBar.value = false
                Toast.makeText(context, "Registered Successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                authViewModel.displayProgressBar.value = false
                Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
            }
            .addOnCompleteListener {
               if(it.isSuccessful)
                   storageRef.set(credential.userDetails)

                if (auth.currentUser!!.isEmailVerified) {
                    navController.navigate(Screens.DashboardNav.route)
                } else {
                    navController.navigate(Screens.EmailVerificationNav.route)
                }
            }
    }
}
