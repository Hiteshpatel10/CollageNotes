package com.geekaid.collagenotes.firebaseDao.screenDao

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.model.FileUploadModel
import com.geekaid.collagenotes.navigation.BottomNavScreen
import com.geekaid.collagenotes.util.userUploadRef
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import timber.log.Timber

fun fileUploadDao(
    uri: Uri,
    context: Context,
    note: FileUploadModel,
    navController: NavHostController
) {

    val storage = Firebase.storage.reference
    val firestore = FirebaseFirestore.getInstance()
    val currentUser = Firebase.auth.currentUser!!

    Timber.i("-1")
//    val locationRef = noteStorageRef(note = note, storageRef = storage)
//    val firestoreRef = noteRef(note = note, firestore = firestore)
    val locationRef = storage.child("courses").child(note.course).child(note.branch)
        .child(note.subject).child(note.noteType).child(note.fileInfo.fileUploadPath)

    val firestoreRef = firestore.collection("courses").document(note.course)
        .collection(note.branch).document(note.subject)
        .collection(note.noteType).document(note.fileInfo.fileUploadPath)

    val userUploadRef =
        userUploadRef(note = note, firestore = firestore, email = currentUser.email.toString())

    Timber.i("a")
    firestoreRef.get()
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Timber.i("b")
                if (task.result?.exists() == true) {
                    Toast.makeText(context, "File Already Exist", Toast.LENGTH_SHORT).show()
                } else {
                    Timber.i("c")
                    firestoreRef.set(note)
                        .addOnCompleteListener {
                            Timber.i("d")
                            locationRef.putFile(uri).also {
                                Toast.makeText(context, "Upload Started", Toast.LENGTH_SHORT)
                                    .show()
                            }
                            userUploadRef.set(note)

                            navController.navigate(BottomNavScreen.DashboardNav.route)
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                        }

                }
            }
        }
        .addOnFailureListener {
            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
        }

}