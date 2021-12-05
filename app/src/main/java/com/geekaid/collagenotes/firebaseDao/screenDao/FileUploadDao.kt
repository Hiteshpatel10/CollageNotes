package com.geekaid.collagenotes.firebaseDao.screenDao

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.model.FileUploadModel
import com.geekaid.collagenotes.navigation.Screens
import com.geekaid.collagenotes.util.noteRef
import com.geekaid.collagenotes.util.noteStorageRef
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import timber.log.Timber

fun fileUploadDao(
    uri: Uri,
    context: Context,
    fileModel: FileUploadModel,
    navController: NavHostController
) {

    val storageRef = Firebase.storage.reference
    val firebaseFirestore = Firebase.firestore

    val locationRef = noteStorageRef(note = fileModel, storageRef = storageRef)
    val firestoreRef = noteRef(note = fileModel, firestore = firebaseFirestore)

    noteRef(note = fileModel, firebaseFirestore)
    firestoreRef.get()
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if (task.result?.exists() == true) {
                    Toast.makeText(context, "File Already Exist", Toast.LENGTH_SHORT).show()
                } else {
                    firestoreRef.set(fileModel)
                        .addOnSuccessListener {
                            locationRef.putFile(uri).also {
                                Toast.makeText(context, "Upload Started", Toast.LENGTH_SHORT).show()
                            }
                            navController.navigate(Screens.DashboardNav.route)
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                            Timber.i(it.message)
                        }
                }
            }
        }
        .addOnFailureListener {
            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
        }
}