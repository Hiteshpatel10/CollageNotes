package com.geekaid.collagenotes.firebaseDao.noteLayoutDao

import com.geekaid.collagenotes.model.FileUploadModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

fun likeDao(note: FileUploadModel) {

    val currentUser = Firebase.auth.currentUser!!
    val db = Firebase.firestore

    val noteRef = db.collection("courses").document(note.course)
        .collection(note.branch).document(note.subject)
        .collection("notes").document(note.fileUploadPath)

    val favNoteRef = db.collection("Users").document(currentUser.email.toString())
        .collection("Favourite").document(note.fileUploadPath)


    if (note.likes.contains(currentUser.email)) {
        db.runBatch { batch ->
            batch.update(noteRef, "likes", FieldValue.arrayRemove(currentUser.email))
            if (note.favourite.contains(currentUser.email))
                batch.update(favNoteRef, "likes", FieldValue.arrayRemove(currentUser.email))
        }
    } else {
        db.runBatch { batch ->
            batch.update(noteRef, "likes", FieldValue.arrayUnion(currentUser.email))
            if (note.favourite.contains(currentUser.email))
                batch.update(favNoteRef, "likes", FieldValue.arrayUnion(currentUser.email))
        }
    }

}