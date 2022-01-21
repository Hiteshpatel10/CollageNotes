package com.geekaid.collegenotes.firebaseDao.screenDao

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.geekaid.collegenotes.model.UploaderDetailModel
import com.geekaid.collegenotes.util.userDetailRef
import com.geekaid.collegenotes.util.userProfileRef
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

fun uploaderDetailDao(uploaderDetailModel: UploaderDetailModel, imageUri: Uri?, context: Context) {

    val currentUser = Firebase.auth.currentUser!!
    val firestore = Firebase.firestore
    val storageRef = Firebase.storage.reference

    val userDetailRef = userDetailRef(firestore = firestore, currentUser = currentUser)
    val userProfileRef = userProfileRef(storageRef = storageRef, currentUser = currentUser)

    userDetailRef.set(uploaderDetailModel)
        .addOnCompleteListener {
            Toast.makeText(context, "saved successfully", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        }

    if (imageUri != null)
        userProfileRef.putFile(imageUri)
            .addOnSuccessListener {
                userProfileRef.downloadUrl
                    .addOnSuccessListener {
                        userDetailRef.update("profileUri",it.toString())
                    }
            }


}
