package com.geekaid.collagenotes.firebaseDao

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.navigation.NavHostController
import androidx.navigation.compose.navArgument
import com.geekaid.collagenotes.model.FileUploadModel
import com.geekaid.collagenotes.navigation.Screens
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

fun fileUploadDao(
    uri: Uri,
    context: Context,
    fileModel: FileUploadModel,
    navController: NavHostController
) {

    val storage = Firebase.storage
    val storageRef = storage.reference
    val firebaseFirestore = Firebase.firestore

    val locationRef =
        storageRef.child("courses").child(fileModel.course).child(fileModel.branch)
            .child(fileModel.subject)
            .child("notes")
            .child(fileModel.fileUploadPath)
    val firestoreRef = firebaseFirestore.collection("courses").document(fileModel.course)
        .collection(fileModel.branch).document(fileModel.subject)
        .collection("notes").document(fileModel.fileUploadPath)


    firestoreRef.get()
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if (task.result?.exists() == true) {
                    Toast.makeText(context, "File Already Exist", Toast.LENGTH_SHORT).show()
                } else {
                    firestoreRef
                        .set(fileModel)
                        .addOnSuccessListener {
                            locationRef.putFile(uri).also {
                                Toast.makeText(context, "Upload Started", Toast.LENGTH_SHORT).show()
                            }
                            navController.navigate(Screens.DashboardNav.route)
                        }
                        .addOnCompleteListener {
                            Toast.makeText(context, it.result.toString(), Toast.LENGTH_LONG).show()
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