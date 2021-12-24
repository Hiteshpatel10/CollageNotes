package com.geekaid.collagenotes.firebaseDao.noteLayoutDao

import com.geekaid.collagenotes.model.FileUploadModel
import com.geekaid.collagenotes.util.noteFavRef
import com.geekaid.collagenotes.util.noteRef
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import timber.log.Timber

fun favouriteDao(note: FileUploadModel, favSpaceName: String) {

    val firestore = Firebase.firestore
    val currentUser = Firebase.auth.currentUser!!
    val fav = note.favourite.indexOf("${currentUser.email}/${favSpaceName}")

    val noteRef = noteRef(note = note, firestore = firestore)
    val favouriteRef = noteFavRef(
        note = note,
        favSpaceName = favSpaceName,
        firestore = firestore,
        currentUser = currentUser
    )

    favouriteRef.get()
        .addOnSuccessListener { document ->

            if (document.exists()) {
                favouriteRef.delete()
                    .addOnFailureListener {
                        Timber.i(it.message)
                    }
                noteRef.update("favourite", FieldValue.arrayRemove("${currentUser.email}/${favSpaceName}"))
                    .addOnFailureListener {
                        Timber.i(it.message)
                    }
            } else {
                noteRef.update("favourite", FieldValue.arrayUnion("${currentUser.email}/${favSpaceName}"))
                    .addOnFailureListener {
                        Timber.i(it.message)
                    }

                note.favourite.add(currentUser.email.toString())
                favouriteRef.set(note)
                    .addOnFailureListener {
                        Timber.i(it.message)
                    }
            }
        }

}

