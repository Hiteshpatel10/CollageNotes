package com.geekaid.collagenotes.firebaseDao.noteLayoutDao

import android.app.DownloadManager
import android.os.Environment
import com.geekaid.collagenotes.model.FileUploadModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

fun noteDownloadDao(note: FileUploadModel, downloadManager: DownloadManager) {

    val storage = Firebase.storage

    storage.reference.child("courses").child(note.course).child(note.branch).child(note.subject)
        .child("notes").child(note.fileUploadPath).downloadUrl
        .addOnSuccessListener { uri ->
            val index: Int = (note.fileMime.lastIndexOf('/')).plus(1)

            val request = DownloadManager.Request(uri)
                .setTitle(note.fileName)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setAllowedOverMetered(true)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOCUMENTS,
                    "${note.fileName}.${note.fileMime.substring(index)}"
                )
            downloadManager.enqueue(request)
        }
}