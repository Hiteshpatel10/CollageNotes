package com.geekaid.collagenotes.firebaseDao.noteLayoutDao

import android.content.Context
import android.widget.Toast
import com.geekaid.collagenotes.model.FileUploadModel
import com.geekaid.collagenotes.model.ReportModel
import com.geekaid.collagenotes.util.noteReportRef
import com.geekaid.collagenotes.util.noteReportReviewRef
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

fun reportDao(note: FileUploadModel, context: Context) {

    val currentUser = Firebase.auth.currentUser
    val firestore = Firebase.firestore

    val reportRef = noteReportRef(note = note, firestore = firestore)
    val reviewRef = noteReportReviewRef(note = note, firestore = firestore)

    reportRef.get()
        .addOnSuccessListener { document ->
            if (document.exists()) {
                val doc = document.toObject(ReportModel::class.java)
                if (doc?.reportedBy?.size!! > 0) {
                    reviewRef.set(note)
                }
                reportRef.update("reportedBy", FieldValue.arrayUnion(currentUser?.email))
                Toast.makeText(context, "Already Reported", Toast.LENGTH_SHORT).show()
            } else {
                val reportedBy: MutableList<String> = mutableListOf()
                reportedBy.add(currentUser?.email.toString())
                reportRef.set(ReportModel(reportedBy = reportedBy))
                Toast.makeText(context, "Note Reported", Toast.LENGTH_SHORT).show()
            }
        }
        .addOnFailureListener { exception ->
            Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show()
        }


}