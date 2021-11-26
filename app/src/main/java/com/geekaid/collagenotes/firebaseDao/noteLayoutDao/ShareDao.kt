package com.geekaid.collagenotes.firebaseDao.noteLayoutDao

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.geekaid.collagenotes.R
import com.geekaid.collagenotes.model.FileUploadModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

fun shareDao(note: FileUploadModel, context: Context) {

    val storage = Firebase.storage
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "Collage Notes")
    }

    storage.reference.child("courses").child(note.course).child(note.branch).child(note.subject)
        .child("notes").child(note.fileInfo.fileUploadPath).downloadUrl
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