package com.geekaid.collegenotes.firebaseDao.noteLayoutDao

import com.geekaid.collegenotes.model.FileUploadModel
import com.geekaid.collegenotes.util.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

fun likeDao(note: FileUploadModel) {

    val currentUser = Firebase.auth.currentUser!!
    val firestore = Firebase.firestore

    var favSpaceName = ""
    Constants.favSpaces.forEach { favSpace ->
        if (note.favourite.contains("${currentUser.email}/${favSpace}")) favSpaceName = favSpace
    }

    val noteRef = noteRef(note = note, firestore = firestore)
    val userUploadRef =
        userUploadRef(note = note, firestore = firestore, email = note.fileInfo.uploaderEmail)
    val userDetailRef = userDetailRef(firestore = firestore, currentUser = note.fileInfo.uploaderEmail)

    if (note.likes.contains(currentUser.email)) {
        firestore.runBatch { batch ->
            batch.update(noteRef, "likes", FieldValue.arrayRemove(currentUser.email))
            batch.update(userUploadRef, "likes", FieldValue.arrayRemove(currentUser.email))
            batch.update(userDetailRef, "likes", FieldValue.increment(-1))
        }
    } else {
        firestore.runBatch { batch ->
            batch.update(noteRef, "likes", FieldValue.arrayUnion(currentUser.email))
            batch.update(userUploadRef, "likes", FieldValue.arrayUnion(currentUser.email))
            batch.update(userDetailRef, "likes", FieldValue.increment(1))
        }
    }
}