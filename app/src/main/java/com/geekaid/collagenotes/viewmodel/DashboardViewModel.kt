package com.geekaid.collagenotes.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.geekaid.collagenotes.model.FileUploadModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DashboardViewModel() : ViewModel() {

    val db = Firebase.firestore
    val auth = Firebase.auth

    val courseList: MutableState<ArrayList<FileUploadModel>> = mutableStateOf(arrayListOf())

    init {
//        db.collection(auth.currentUser?.email.toString()).
    }
}