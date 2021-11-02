package com.geekaid.collagenotes.firebaseDao.screenDao

import com.geekaid.collagenotes.model.FileUploadModel
import com.geekaid.collagenotes.model.FilterModel
import com.geekaid.collagenotes.viewmodel.DashboardViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import timber.log.Timber

fun dashboardDao(viewModel: DashboardViewModel) {

    val db = Firebase.firestore
    val auth = Firebase.auth
    val courseList: MutableList<FileUploadModel> = mutableListOf()

    db.collection("Users").document(auth.currentUser?.email.toString())
        .collection("UserData").document("FilterData")
        .addSnapshotListener { value, error ->
            if (error != null) {
                return@addSnapshotListener
            }

            if (value != null && value.exists()) {
                viewModel.filter.value = value.toObject<FilterModel>()!!
                val filter = value.toObject<FilterModel>()

                if (filter != null) {
                    db.collection("courses").document(filter.course)
                        .collection(filter.branch).document(filter.subject)
                        .collection("notes").addSnapshotListener { course, e ->

                            if (e != null) {
                                Timber.i(e.message)
                            }

                            for (dc: DocumentChange in course?.documentChanges!!) {
                                if (dc.type == DocumentChange.Type.ADDED) {
                                    Timber.i(
                                        dc.document.toObject(FileUploadModel::class.java).toString()
                                    )
                                    courseList.add(dc.document.toObject(FileUploadModel::class.java))
                                }
                            }
                            viewModel.courseList.value = courseList
                        }
                }
            } else {
                Timber.i(value.toString())
            }
        }
}


