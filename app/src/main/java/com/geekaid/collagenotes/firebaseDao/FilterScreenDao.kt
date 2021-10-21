package com.geekaid.collagenotes.firebaseDao

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.model.FilterModel
import com.geekaid.collagenotes.navigation.Screens
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

fun filterScreenDao(filterModel: FilterModel, context: Context, navController: NavHostController) {

    val auth = Firebase.auth
    val db = Firebase.firestore

    db.collection("Users").document(auth.currentUser?.email.toString())
        .collection("SearchFilter").document("FilterData").set(filterModel)
        .addOnCompleteListener{
            navController.navigate(Screens.DashboardNav.route)
        }
        .addOnFailureListener{
            Toast.makeText(context,it.message,Toast.LENGTH_LONG).show()
        }

}