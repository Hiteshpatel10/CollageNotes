package com.geekaid.collagenotes.firebaseDao

import com.geekaid.collagenotes.model.FileUploadModel
import com.geekaid.collagenotes.model.FilterModel
import com.geekaid.collagenotes.viewmodel.DashboardViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

fun dashboardDao(viewModel: DashboardViewModel) {

    val db = Firebase.firestore
    val auth = Firebase.auth

    db.collection("Users").document(auth.currentUser?.email.toString())
        .collection("SearchFilter").document("FilterData").get()
        .addOnSuccessListener { document ->
            viewModel.filter.value = document.toObject<FilterModel>()!!
        }


    db.collection("courses").document("BTech")
        .collection("Computer Science").document("Operating System")
        .collection("notes").addSnapshotListener { value, error ->

            if(error != null){
                return@addSnapshotListener
            }

            for (dc: DocumentChange in value?.documentChanges!!) {
                if (dc.type == DocumentChange.Type.ADDED) {
                    viewModel.courseList.value.add(dc.document.toObject(FileUploadModel::class.java))
                }
            }
        }
}