package com.geekaid.collegenotes.firebaseDao.noteLayoutDao

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import com.geekaid.collegenotes.model.FileUploadModel
import com.geekaid.collegenotes.util.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

fun noteDownloadDao(note: FileUploadModel, context: Context, downloadManager: DownloadManager) {

    val storage = Firebase.storage.reference
    val currentUser = FirebaseAuth.getInstance().currentUser!!
    val firestore = FirebaseFirestore.getInstance()
    var favSpace = ""
    Constants.favSpaces.forEach { favName ->
        if (note.favourite.contains("${currentUser.email}/${favName}")) {
            favSpace = favName
        }
    }

    val noteRef = noteRef(note = note, firestore = firestore)
    val storageRef = noteStorageRef(note = note, storageRef = storage)
    val userUploadRef =
        userUploadRef(note = note, firestore = firestore, email = note.fileInfo.uploaderEmail)
    val favNoteRef = noteFavRef(
        note = note,
        favSpaceName = if (favSpace.isNotEmpty()) favSpace else "fav1",
        firestore = firestore,
        currentUser = currentUser
    )

    storageRef.downloadUrl
        .addOnSuccessListener { uri ->

            val fileName = "${note.fileInfo.fileName}.pdf"
            val file = "${context.getExternalFilesDir(null)}/$fileName"

            if (!File(file).exists()) {
                Toast.makeText(context, "Download started", Toast.LENGTH_LONG).show()
                downloadFile(
                    uri,
                    note,
                    noteRef,
                    favNoteRef,
                    userUploadRef,
                    fileName,
                    downloadManager,
                    context
                )
            } else {
                Toast.makeText(context, "File already exists", Toast.LENGTH_SHORT).show()
            }
        }
}

fun downloadFile(
    uri: Uri,
    note: FileUploadModel,
    noteRef: DocumentReference,
    favNoteRef: DocumentReference,
    userUploadRef: DocumentReference,
    fileName: String,
    downloadManager: DownloadManager,
    context: Context
) {
    val db = FirebaseFirestore.getInstance()
    val currentUser = Firebase.auth.currentUser!!

    val request = DownloadManager.Request(uri)
        .setTitle(note.fileInfo.fileName)
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        .setAllowedOverMetered(true)
        .setDestinationInExternalFilesDir(context, null, fileName)

    downloadManager.enqueue(request)

//    db.runBatch { batch ->
//        batch.update(noteRef, "downloadedTimes", FieldValue.increment(1))
//        batch.update(userUploadRef, "downloadedTimes", FieldValue.increment(1))
//        if (note.favourite.contains(currentUser.email.toString()))
//            batch.update(favNoteRef, "downloadedTimes", FieldValue.increment(1))
//    }.addOnFailureListener {
//        Timber.i(it.message)
//    }

}