package com.geekaid.collagenotes.firebaseDao.noteLayoutDao

import com.geekaid.collagenotes.model.FileUploadModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

fun favouriteDao(course: FileUploadModel) {

    val db = Firebase.firestore
    val currentUser = Firebase.auth.currentUser!!

    val noteRef = db.collection("courses").document(course.course)
        .collection(course.branch).document(course.subject)
        .collection("notes").document(course.fileInfo.fileUploadPath)

    val favouriteRef = db.collection("Users").document(currentUser.email.toString())
        .collection("Favourite").document(course.fileInfo.fileUploadPath)

    favouriteRef.get()
        .addOnSuccessListener { document ->

            if (document.exists()) {
                favouriteRef.delete()
                noteRef.update("favourite", FieldValue.arrayRemove(currentUser.email))
            } else {
                noteRef.update("favourite", FieldValue.arrayUnion(currentUser.email))

                course.favourite.add(currentUser.email.toString())
                favouriteRef.set(course)
            }
        }

}

