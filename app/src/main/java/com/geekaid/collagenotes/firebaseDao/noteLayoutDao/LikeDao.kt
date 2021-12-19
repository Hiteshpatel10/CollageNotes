package com.geekaid.collagenotes.firebaseDao.noteLayoutDao

import com.geekaid.collagenotes.model.FileUploadModel
import com.geekaid.collagenotes.util.noteFavRef
import com.geekaid.collagenotes.util.noteRef
import com.geekaid.collagenotes.util.userUploadRef
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

fun likeDao(note: FileUploadModel) {

    val currentUser = Firebase.auth.currentUser!!
    val firestore = Firebase.firestore

    val noteRef = noteRef(note = note, firestore = firestore)
    val favNoteRef = noteFavRef(note = note, firestore = firestore, currentUser = currentUser)
    val userUploadRef = userUploadRef(note = note, firestore = firestore, currentUser = currentUser)

    if (note.likes.contains(currentUser.email)) {
        firestore.runBatch { batch ->
            batch.update(noteRef, "likes", FieldValue.arrayRemove(currentUser.email))
            batch.update(userUploadRef, "likes", FieldValue.arrayRemove(currentUser.email))
            if (note.favourite.contains(currentUser.email))
                batch.update(favNoteRef, "likes", FieldValue.arrayRemove(currentUser.email))
        }
    } else {
        firestore.runBatch { batch ->
            batch.update(noteRef, "likes", FieldValue.arrayUnion(currentUser.email))
            batch.update(userUploadRef, "likes", FieldValue.arrayUnion(currentUser.email))
            if (note.favourite.contains(currentUser.email))
                batch.update(favNoteRef, "likes", FieldValue.arrayUnion(currentUser.email))
        }
    }

}