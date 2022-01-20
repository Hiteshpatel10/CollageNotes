package com.geekaid.collegenotes.firebaseDao.noteLayoutDao

import com.geekaid.collegenotes.model.FileUploadModel
import com.geekaid.collegenotes.model.ListFetch
import com.geekaid.collegenotes.util.noteFavRef
import com.geekaid.collegenotes.util.noteRef
import com.geekaid.collegenotes.util.noteRefPath
import com.geekaid.collegenotes.util.userUploadRef
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

fun favouriteDao(note: FileUploadModel, favSpaceName: String) {

    val firestore = Firebase.firestore
    val currentUser = Firebase.auth.currentUser!!

    val noteRef = noteRef(note = note, firestore = firestore)
    val noteRefPath = noteRefPath(note = note)
    val userUploadRef =
        userUploadRef(note = note, firestore = firestore, email = note.fileInfo.uploaderEmail)

    val favouriteRef = noteFavRef(
        note = note,
        favSpaceName = favSpaceName,
        firestore = firestore,
        currentUser = currentUser
    )

    
    favouriteRef.get()
        .addOnSuccessListener { document ->
            if (document.exists()) {
                val list = document.toObject(ListFetch::class.java)?.list

                if (list?.contains(noteRefPath) == true)
                    firestore.runBatch { batch ->
                        batch.update(favouriteRef, "list", FieldValue.arrayRemove(noteRefPath))
                        batch.update(
                            userUploadRef,
                            "favourite",
                            FieldValue.arrayRemove("${currentUser.email}/${favSpaceName}")
                        )
                        batch.update(
                            noteRef,
                            "favourite",
                            FieldValue.arrayRemove("${currentUser.email}/${favSpaceName}")
                        )
                    }
                else
                    firestore.runBatch { batch ->
                        batch.update(favouriteRef, "list", FieldValue.arrayUnion(noteRefPath))
                        batch.update(
                            userUploadRef,
                            "favourite",
                            FieldValue.arrayUnion("${currentUser.email}/${favSpaceName}")
                        )
                        batch.update(
                            noteRef,
                            "favourite",
                            FieldValue.arrayUnion("${currentUser.email}/${favSpaceName}")
                        )
                    }
            } else {
                favouriteRef.set(ListFetch(list = listOf(noteRefPath)))
                firestore.runBatch{ batch ->
                    batch.update(
                        userUploadRef,
                        "favourite",
                        FieldValue.arrayRemove("${currentUser.email}/${favSpaceName}")
                    )
                    batch.update(
                        noteRef,
                        "favourite",
                        FieldValue.arrayRemove("${currentUser.email}/${favSpaceName}")
                    )
                }
            }
        }
}

