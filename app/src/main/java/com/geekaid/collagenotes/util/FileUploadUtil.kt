package com.geekaid.collagenotes.util

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.geekaid.collagenotes.model.FileUploadModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

fun fileUpload(uri: Uri, context: Context, fileModel: FileUploadModel) {

    val storage = Firebase.storage
    val storageRef = storage.reference
    val locationRef =
        storageRef.child(fileModel.course).child(fileModel.branch).child(fileModel.subject)
            .child(fileModel.fileUploadPath)
    val firebaseFirestore = Firebase.firestore
    val firestoreRef = firebaseFirestore.collection(fileModel.course).document(fileModel.branch)
        .collection(fileModel.subject).document(fileModel.fileUploadPath)

    firestoreRef.get()
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if (task.result?.exists() == true) {
                    Toast.makeText(context, "File Already Exist", Toast.LENGTH_SHORT).show()
                } else {
                    firestoreRef
                        .set(fileModel)
                        .addOnSuccessListener {
                            locationRef.putFile(uri).also {
                                Toast.makeText(context, "Upload Started", Toast.LENGTH_SHORT).show()
                            }
                        }
                        .addOnFailureListener {
                            Toast.makeText(context,it.message,Toast.LENGTH_LONG).show()
                        }
                }
            }
        }
        .addOnFailureListener {
            Toast.makeText(context,it.message,Toast.LENGTH_LONG).show()
        }
}