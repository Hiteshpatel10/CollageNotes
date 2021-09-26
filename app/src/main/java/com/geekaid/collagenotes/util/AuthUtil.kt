package com.geekaid.collagenotes.util

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.navigation.Screens
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

fun sendOTP(mobileNo: String, context: Context, navController: NavHostController): PhoneAuthOptions {

    val options = PhoneAuthOptions.newBuilder(Firebase.auth)
        .setPhoneNumber(mobileNo)
        .setTimeout(60L, TimeUnit.SECONDS)
        .setActivity(context as Activity)
        .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                navController.navigate(Screens.DashboardNav.route)
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Log.i("notes12", "${p0.message}")
            }

        })
        .build()

    return options
}

