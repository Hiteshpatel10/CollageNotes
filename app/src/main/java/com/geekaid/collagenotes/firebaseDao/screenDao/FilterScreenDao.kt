package com.geekaid.collagenotes.firebaseDao.screenDao

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.model.FilterListsModel
import com.geekaid.collagenotes.model.FilterModel
import com.geekaid.collagenotes.navigation.BottomNavScreen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import timber.log.Timber

fun filterScreenDao(filterModel: FilterModel, context: Context, navController: NavHostController) {

    val auth = Firebase.auth
    val db = Firebase.firestore

    db.collection("Users").document(auth.currentUser?.email.toString())
        .collection("UserData").document("FilterData").set(filterModel)
        .addOnSuccessListener {
            navController.navigate(BottomNavScreen.DashboardNav.route)
        }
        .addOnFailureListener {
            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
        }

}


fun getFilter() {

    val db = Firebase.firestore

    db.collection("courses").document("filterList").get()
        .addOnSuccessListener { document ->

            Timber.i(document.toObject(FilterListsModel::class.java).toString())

        }
}