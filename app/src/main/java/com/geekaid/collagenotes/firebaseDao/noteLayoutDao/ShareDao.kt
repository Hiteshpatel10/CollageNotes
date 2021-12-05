package com.geekaid.collagenotes.firebaseDao.noteLayoutDao

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.geekaid.collagenotes.model.FileUploadModel
import com.geekaid.collagenotes.util.noteStorageRef
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

fun shareDao(note: FileUploadModel, context: Context) {

    val storage = Firebase.storage.reference
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "Collage Notes")
    }

    val storageRef = noteStorageRef(note = note, storageRef = storage)

   storageRef.downloadUrl
        .addOnSuccessListener {
            Toast.makeText(context, "Wait! \n generating download link", Toast.LENGTH_SHORT).show()
            val shareText =
                "Collage Notes \nNotes download link for Branch: ${note.branch} Course: ${note.course} Subject: ${note.subject} \nDownload Link: $it"
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText)
            startActivity(context, shareIntent, null)
        }
        .addOnFailureListener {
            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
        }
}