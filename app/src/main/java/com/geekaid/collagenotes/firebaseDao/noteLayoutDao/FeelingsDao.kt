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
                if (course.likes.contains(currentUser.email) && course.likes.isNotEmpty()) {
                    db.runBatch { batch ->
                        batch.update(noteRef, "likes", FieldValue.arrayRemove(currentUser.email))
                        batch.update(favNoteRef, "likes", FieldValue.arrayRemove(currentUser.email))
                    }
                } else {
                    db.runBatch { batch ->
                        batch.update(noteRef, "likes", FieldValue.arrayUnion(currentUser.email))
                        batch.update(favNoteRef, "likes", FieldValue.arrayUnion(currentUser.email))
                    }
                }

                if (course.dislikes.contains(currentUser.email)) {
                    db.runBatch { batch ->
                        batch.update(noteRef, "dislikes", FieldValue.arrayRemove(currentUser.email))
                        batch.update(
                            favNoteRef,
                            "dislikes",
                            FieldValue.arrayRemove(currentUser.email)
                        )
                    }
                }

            } else {

                if (course.likes.contains(currentUser.email)) {
                    noteRef.update("likes", FieldValue.arrayRemove(currentUser.email))
                    Timber.i("liked")
                } else {
                    Timber.i("liked")
                    noteRef.update("likes", FieldValue.arrayUnion(currentUser.email))
                }

                if (course.dislikes.contains(currentUser.email)) {
                    Timber.i("liked")
                    noteRef.update("dislikes", FieldValue.arrayRemove(currentUser.email))
                }
            }
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
                    db.runBatch { batch ->
                        batch.update(noteRef, "likes", FieldValue.arrayRemove(currentUser.email))
                        batch.update(favNoteRef, "likes", FieldValue.arrayRemove(currentUser.email))
                    }
                }

                if (course.dislikes.contains(currentUser.email)) {
                    db.runBatch { batch ->
                        batch.update(noteRef, "dislikes", FieldValue.arrayRemove(currentUser.email))
                        batch.update(
                            favNoteRef,
                            "dislikes",
                            FieldValue.arrayRemove(currentUser.email)
                        )
                    }
                } else {
                    db.runBatch { batch ->
                        batch.update(noteRef, "dislikes", FieldValue.arrayUnion(currentUser.email))
                        batch.update(
                            favNoteRef,
                            "dislikes",
                            FieldValue.arrayUnion(currentUser.email)
                        )
                    }
                }
            } else {
                if (course.likes.contains(currentUser.email)) {
                    favNoteRef.update("likes", FieldValue.arrayRemove(currentUser.email))
                }

                if (course.dislikes.contains(currentUser.email)) {
                    favNoteRef.update("dislikes", FieldValue.arrayRemove(currentUser.email))
                } else {
                    favNoteRef.update("dislikes", FieldValue.arrayUnion(currentUser.email))
                }
            }
        }


}