package com.geekaid.collegenotes.firebaseDao.screenDao

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.geekaid.collegenotes.model.FilterModel
import com.geekaid.collegenotes.navigation.BottomNavScreen
import com.geekaid.collegenotes.util.filterDataRef
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

fun filterScreenDao(filterModel: FilterModel, context: Context, navController: NavHostController) {

    val currentUser = Firebase.auth.currentUser!!
    val firestore = Firebase.firestore

    val filterDataRef = filterDataRef(currentUser = currentUser, firestore = firestore)

    filterDataRef.set(filterModel)
        .addOnCompleteListener {
            navController.navigate(BottomNavScreen.DashboardNav.route)
        }
        .addOnFailureListener {
            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
        }

}


