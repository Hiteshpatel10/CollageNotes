package com.geekaid.collagenotes.firebaseDao.noteLayoutDao

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import com.geekaid.collagenotes.R
import com.geekaid.collagenotes.model.FileUploadModel
import com.geekaid.collagenotes.util.noteFavRef
import com.geekaid.collagenotes.util.noteRef
import com.geekaid.collagenotes.util.noteStorageRef
import com.geekaid.collagenotes.util.userUploadRef
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import timber.log.Timber
import java.io.File

fun noteDownloadDao(note: FileUploadModel, context: Context, downloadManager: DownloadManager) {

    val storage = Firebase.storage.reference
    val currentUser = FirebaseAuth.getInstance().currentUser!!
    val firestore = FirebaseFirestore.getInstance()

    val noteRef = noteRef(note = note, firestore = firestore)
    val favNoteRef = noteFavRef(note = note, firestore = firestore, currentUser = currentUser)
    val storageRef = noteStorageRef(note = note, storageRef = storage)
    val userUploadRef =
        userUploadRef(note = note, firestore = firestore, email = note.fileInfo.uploaderEmail)

    storageRef.downloadUrl
        .addOnSuccessListener { uri ->

            val index: Int = (note.fileInfo.fileMime.lastIndexOf('/')).plus(1)
            val path =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
            val fileName = "Collage Notes/${note.fileInfo.fileName}.${
                note.fileInfo.fileMime.substring(index)
            }"
            val file = "$path/$fileName"

            if (!File(file).exists()) {
                Toast.makeText(context, "Download started", Toast.LENGTH_LONG).show()
                downloadFile(
                    uri,
                    note,
                    noteRef,
                    favNoteRef,
                    userUploadRef,
                    fileName,
                    downloadManager
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
    downloadManager: DownloadManager
) {
    val db = FirebaseFirestore.getInstance()
    val currentUser = Firebase.auth.currentUser!!

    db.runBatch { batch ->
        batch.update(noteRef, "downloadedTimes", FieldValue.increment(1))
        batch.update(userUploadRef, "downloadedTimes", FieldValue.increment(1))
        if (note.favourite.contains(currentUser.email.toString()))
            batch.update(favNoteRef, "downloadedTimes", FieldValue.increment(1))
    }.addOnFailureListener {
        Timber.i(it.message)
    }

    val request = DownloadManager.Request(uri)
        .setTitle(note.fileInfo.fileName)
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        .setAllowedOverMetered(true)
        .setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOCUMENTS,
            fileName
        )
    downloadManager.enqueue(request)
}