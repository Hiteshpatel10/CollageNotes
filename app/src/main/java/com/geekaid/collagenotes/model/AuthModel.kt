package com.geekaid.collagenotes.model

data class SignUpModel(
    val email: String,
    val password: String,
    val confirmPassword: String,
    val userDetails: UserDetails
)

data class SignInModel(
    val email: String,
    val password: String
)

data class AlertReturn(
    var showAlertDialog: Boolean = false,
    var signInNavigate: Boolean = false,
)

