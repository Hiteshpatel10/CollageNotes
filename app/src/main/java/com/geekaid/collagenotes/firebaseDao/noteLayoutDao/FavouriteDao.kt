package com.geekaid.collagenotes.firebaseDao.noteLayoutDao

import com.geekaid.collagenotes.model.FileUploadModel
import com.geekaid.collagenotes.util.noteFavRef
import com.geekaid.collagenotes.util.noteRef
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

fun favouriteDao(note: FileUploadModel) {

    val firestore = Firebase.firestore
    val currentUser = Firebase.auth.currentUser!!

    val noteRef = noteRef(note = note, firestore = firestore)
    val favouriteRef = noteFavRef(note = note, firestore = firestore, currentUser = currentUser)

    favouriteRef.get()
        .addOnSuccessListener { document ->

            if (document.exists()) {
                favouriteRef.delete()
                noteRef.update("favourite", FieldValue.arrayRemove(currentUser.email))
            } else {
                noteRef.update("favourite", FieldValue.arrayUnion(currentUser.email))

                note.favourite.add(currentUser.email.toString())
                favouriteRef.set(note)
            }
        }

}

