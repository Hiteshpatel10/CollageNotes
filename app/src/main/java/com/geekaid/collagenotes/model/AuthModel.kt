package com.geekaid.collagenotes.model

data class SignUpModel(
    val email: String,
    val password: String,
    val confirmPassword: String
)

data class SignInModel(
    val email: String,
    val password: String
)