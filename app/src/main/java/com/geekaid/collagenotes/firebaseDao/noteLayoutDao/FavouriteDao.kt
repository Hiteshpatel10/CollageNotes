package com.geekaid.collagenotes.firebaseDao.noteLayoutDao

import com.geekaid.collagenotes.model.FileUploadModel
import com.geekaid.collagenotes.util.Constants
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

    var favSpace = ""
    Constants.favSpaces.forEach { favName ->
        if (note.favourite.contains("${currentUser.email}/${favName}")) {
            favSpace = favName
        }
    }

    val noteRef = noteRef(note = note, firestore = firestore)
    val favouriteRef = noteFavRef(
        note = note,
        favSpaceName = if (favSpace.isNotEmpty()) favSpace else favSpaceName,
        firestore = firestore,
        currentUser = currentUser
    )

    favouriteRef.get()
        .addOnSuccessListener { document ->

            if (document.exists()) {
                favouriteRef.delete()
                if (favSpaceName.isNotEmpty())
                    noteRef.update(
                        "favourite",
                        FieldValue.arrayRemove("${currentUser.email}/${favSpaceName}")
                    )
                else
                    noteRef.update(
                        "favourite",
                        FieldValue.arrayRemove("${currentUser.email}/${favSpace}"))

            } else {
                noteRef.update(
                    "favourite",
                    FieldValue.arrayUnion("${currentUser.email}/${favSpaceName}")
                )

                note.favourite.add("${currentUser.email}/${favSpaceName}")
                favouriteRef.set(note)

            }
        }
}

