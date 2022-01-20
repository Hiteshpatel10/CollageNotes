package com.geekaid.collegenotes.firebaseDao.noteLayoutDao

import com.geekaid.collegenotes.model.FileUploadModel
import com.geekaid.collegenotes.util.Constants
import com.geekaid.collegenotes.util.noteFavRef
import com.geekaid.collegenotes.util.noteRef
import com.geekaid.collegenotes.util.userUploadRef
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
    val favNoteRef = noteFavRef(
        note = note,
        firestore = firestore,
        favSpaceName = if (favSpaceName.isNotEmpty()) favSpaceName else "fav1",
        currentUser = currentUser
    )

    if (note.likes.contains(currentUser.email)) {
        firestore.runBatch { batch ->
            batch.update(noteRef, "likes", FieldValue.arrayRemove(currentUser.email))
            batch.update(userUploadRef, "likes", FieldValue.arrayRemove(currentUser.email))
        }
    } else {
        firestore.runBatch { batch ->
            batch.update(noteRef, "likes", FieldValue.arrayUnion(currentUser.email))
            batch.update(userUploadRef, "likes", FieldValue.arrayUnion(currentUser.email))
        }
    }
}