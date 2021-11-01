package com.geekaid.collagenotes.firebaseDao.noteLayoutDao

import com.geekaid.collagenotes.model.FileUploadModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import timber.log.Timber

fun feelingsLikeDao(course: FileUploadModel) {

    val currentUser = Firebase.auth.currentUser!!
    val db = Firebase.firestore

    val noteRef = db.collection("courses").document(course.course)
        .collection(course.branch).document(course.subject)
        .collection("notes").document(course.fileUploadPath)

    val favNoteRef = db.collection("Users").document(currentUser.email.toString())
        .collection("Favourite").document(course.fileUploadPath)

    favNoteRef.get()
        .addOnSuccessListener { document ->
            if (document.exists()) {
                if (course.likes.contains(currentUser.email)) {
                    course.likes.remove(currentUser.email)
                    favNoteRef.update("likes", FieldValue.arrayRemove(currentUser.email))
                    Timber.i("liked")
                } else {
                    course.likes.add(currentUser.email.toString())
                    Timber.i("liked")
                    favNoteRef.update("likes", FieldValue.arrayUnion(currentUser.email))
                }

                if (course.dislikes.contains(currentUser.email)) {
                    course.dislikes.remove(currentUser.email)
                    Timber.i("liked")
                    favNoteRef.update("dislikes", FieldValue.arrayRemove(currentUser.email))
                }
            }
        }

    if (course.likes.contains(currentUser.email) && course.likes.isNotEmpty()) {
        course.likes.remove(currentUser.email)
        Timber.i("dis")
        noteRef.update("likes", FieldValue.arrayRemove(currentUser.email))
    } else {
        Timber.i("dis")

        course.likes.add(currentUser.email.toString())
        noteRef.update("likes", FieldValue.arrayUnion(currentUser.email))
    }

    if (course.dislikes.contains(currentUser.email)) {
        Timber.i("dis")
        course.dislikes.remove(currentUser.email)
        noteRef.update("dislikes", FieldValue.arrayRemove(currentUser.email))
    }

}

fun feelingsDislikeDao(course: FileUploadModel) {

    val currentUser = Firebase.auth.currentUser!!
    val db = Firebase.firestore

    val noteRef = db.collection("courses").document(course.course)
        .collection(course.branch).document(course.subject)
        .collection("notes").document(course.fileUploadPath)

    val favNoteRef = db.collection("Users").document(currentUser.email.toString())
        .collection("Favourite").document(course.fileUploadPath)

    favNoteRef.get()
        .addOnSuccessListener { document ->

            if (document.exists()) {
                if (course.likes.contains(currentUser.email)) {
                    course.likes.remove(currentUser.email)
                    favNoteRef.update("likes", FieldValue.arrayRemove(currentUser.email))
                }

                if (course.dislikes.contains(currentUser.email)) {
                    course.dislikes.remove(currentUser.email)
                    favNoteRef.update("dislikes", FieldValue.arrayRemove(currentUser.email))
                } else {
                    course.dislikes.add(currentUser.email.toString())
                    favNoteRef.update("dislikes", FieldValue.arrayUnion(currentUser.email))
                }
            }
        }

    if (course.likes.contains(currentUser.email)) {
        course.likes.remove(currentUser.email)
        noteRef.update("likes", FieldValue.arrayRemove(currentUser.email))
    }

    if (course.dislikes.contains(currentUser.email)) {
        course.dislikes.remove(currentUser.email)
        noteRef.update("dislikes", FieldValue.arrayRemove(currentUser.email))
    } else {
        course.dislikes.add(currentUser.email.toString())
        noteRef.update("dislikes", FieldValue.arrayUnion(currentUser.email))
    }

}