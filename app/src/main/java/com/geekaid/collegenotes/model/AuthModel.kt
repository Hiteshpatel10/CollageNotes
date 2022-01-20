package com.geekaid.collegenotes.model

data class SignUpModel(
    val email: String,
    val password: String,
    val confirmPassword: String,
)

data class SignInModel(
    val email: String,
    val password: String
)

