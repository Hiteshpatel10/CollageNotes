package com.geekaid.collagenotes.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {

    val displayProgressBar: MutableState<Boolean> = mutableStateOf(false)
}