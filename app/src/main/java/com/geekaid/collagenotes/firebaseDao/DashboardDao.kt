package com.geekaid.collagenotes.firebaseDao

import android.util.Log
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
    val courseList: ArrayList<FileUploadModel> = arrayListOf()

    db.collection("Users").document(auth.currentUser?.email.toString())
        .collection("SearchFilter").document("FilterData")
        .addSnapshotListener{ value, error ->
            if(error != null){
                return@addSnapshotListener
            }

            if (value != null && value.exists()) {
                viewModel.filter.value = value.toObject<FilterModel>()!!
                val filter = value.toObject<FilterModel>()!!
                db.collection("courses").document("BTech")
                    .collection(filter.branch).document(filter.subject)
                    .collection("notes").addSnapshotListener { course, e ->

                        if(e != null){
                            Log.i("dow","${e.message}")
                        }

                        for (dc: DocumentChange in course?.documentChanges!!) {
                            if (dc.type == DocumentChange.Type.ADDED) {
                                courseList.add(dc.document.toObject(FileUploadModel::class.java))
                            }
                        }
                        viewModel.courseList.value = courseList
                    }
            } else {
                Log.i("notes","fhg f $value")
            }
        }
}

