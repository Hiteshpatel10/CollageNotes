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

data class UserDetails(
    var firstName: String = "",
    var lastName: String = "",
)