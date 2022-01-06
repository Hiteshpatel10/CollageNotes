package com.geekaid.collegenotes.util

import android.content.Context
import com.geekaid.collegenotes.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

fun getGoogleSignInClient(context: Context) : GoogleSignInClient{

    val signInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
        .requestIdToken(R.string.default_web_client_id.toString())
        .requestEmail()
        .build()

    return GoogleSignIn.getClient(context, signInOption)
}