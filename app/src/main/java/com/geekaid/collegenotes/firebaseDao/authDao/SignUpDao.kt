package com.geekaid.collegenotes.firebaseDao.authDao

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.geekaid.collegenotes.model.ListFetch
import com.geekaid.collegenotes.model.SignUpModel
import com.geekaid.collegenotes.navigation.BottomNavScreen
import com.geekaid.collegenotes.navigation.Screens
import com.geekaid.collegenotes.util.Constants
import com.geekaid.collegenotes.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

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

fun registerUser(
    context: Context,
    credential: SignUpModel,
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    val auth = FirebaseAuth.getInstance()
    val firestore = Firebase.firestore


    if (validateSignUpData(context, credential)) {
        authViewModel.displayProgressBar.value = true
        auth.createUserWithEmailAndPassword(credential.email, credential.password)
            .addOnSuccessListener {
                authViewModel.displayProgressBar.value = false
                Constants.favSpaces.forEach { favSpaceName ->
                    Constants.filterBy.forEach { filterBy ->
                        firestore.collection("users").document(auth.currentUser?.email.toString())
                            .collection(favSpaceName).document(filterBy.value).set(ListFetch())
                    }
                }
                Toast.makeText(context, "Registered Successfully", Toast.LENGTH_SHORT).show()
                if (auth.currentUser!!.isEmailVerified) {
                    navController.navigate(BottomNavScreen.DashboardNav.route)
                } else {
                    navController.navigate(Screens.EmailVerificationNav.route)
                }
            }
            .addOnFailureListener {
                authViewModel.displayProgressBar.value = false
                Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
