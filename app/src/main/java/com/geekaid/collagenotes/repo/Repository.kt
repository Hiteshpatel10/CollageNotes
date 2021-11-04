package com.geekaid.collagenotes.repo

import com.geekaid.collagenotes.model.FilterModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Singleton

@Singleton
class Repository {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val currentUser = auth.currentUser


    @ExperimentalCoroutinesApi
    fun getFilter() = callbackFlow {

        val collection = firestore.collection("Users").document(auth.currentUser?.email.toString())
            .collection("UserData").document("FilterData")

        val snapshotListener = collection.addSnapshotListener { value, error ->
            if (error == null)
                trySend(value)
        }

        awaitClose {
            snapshotListener.remove()
        }
    }

    @ExperimentalCoroutinesApi
    fun getNotes(filter: FilterModel) = callbackFlow {

        val noteRef = firestore.collection("courses").document(filter.course)
            .collection(filter.branch).document(filter.subject)
            .collection("notes")

        val snapshotListener = noteRef.addSnapshotListener { value, error ->
            if (error == null)
                trySend(value)
        }

        awaitClose {
            snapshotListener.remove()
        }
    }

    @ExperimentalCoroutinesApi
    fun gerFavouriteNotes() = callbackFlow {
        val collection = firestore.collection("Users").document(currentUser?.email.toString())
            .collection("Favourite")

        val snapshotListener = collection.addSnapshotListener { value, error ->
            if (error == null)
                trySend(value)
        }

        awaitClose {
            snapshotListener.remove()
        }
    }
}