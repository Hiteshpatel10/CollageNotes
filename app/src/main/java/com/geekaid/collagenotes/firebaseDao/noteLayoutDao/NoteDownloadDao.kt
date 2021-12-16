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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

fun noteDownloadDao(note: FileUploadModel, context: Context, downloadManager: DownloadManager) {

    val storage = Firebase.storage.reference
    val currentUser = FirebaseAuth.getInstance().currentUser!!
    val firestore = FirebaseFirestore.getInstance()

    val noteRef = noteRef(note = note, firestore = firestore)
    val favNoteRef = noteFavRef(note = note, firestore = firestore, currentUser = currentUser)
    val storageRef = noteStorageRef(note = note, storageRef = storage)

    storageRef.downloadUrl
        .addOnSuccessListener { uri ->

            val index: Int = (note.fileInfo.fileMime.lastIndexOf('/')).plus(1)
            val path =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
            val fileName = "${R.string.app_name}/${note.fileInfo.fileName}.${note.fileInfo.fileMime.substring(index)}"
            val file = "$path/$fileName"

            if (!File(file).exists()) {
                Toast.makeText(context, "Download started", Toast.LENGTH_LONG).show()
                downloadFile(uri, note, noteRef, favNoteRef, fileName, downloadManager)
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
    fileName: String,
    downloadManager: DownloadManager
) {
    val db = FirebaseFirestore.getInstance()

    db.runBatch { batch ->
        batch.update(noteRef, "downloadedTimes", FieldValue.increment(1))
        batch.update(favNoteRef, "downloadedTimes", FieldValue.increment(1))
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