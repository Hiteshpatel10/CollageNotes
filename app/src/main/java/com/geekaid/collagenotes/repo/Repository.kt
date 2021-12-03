package com.geekaid.collagenotes.repo

import com.geekaid.collagenotes.model.FilterModel
import com.geekaid.collagenotes.model.UserDetails
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Singleton

@Singleton
class Repository {

    private val auth = Firebase.auth
    private val firestore = FirebaseFirestore.getInstance()

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
        val collection = firestore.collection("Users").document(auth.currentUser?.email.toString())
            .collection("Favourite")

        val snapshotListener = collection.addSnapshotListener { value, error ->
            if (error == null)
                trySend(value)
        }

        awaitClose {
            snapshotListener.remove()
        }
    }

    @ExperimentalCoroutinesApi
    fun getFilterLists() = callbackFlow {
        val collection = firestore.collection("courses").document("filterLists")

        val snapshotListener = collection.addSnapshotListener { value, error ->
            if (error == null)
                trySend(value)
        }

        awaitClose {
            snapshotListener.remove()
        }
    }


    suspend fun getUserDetails(): UserDetails {

        val collection = firestore.collection("Users").document(auth.currentUser?.email.toString())
            .collection("UserData").document("UserInfo")

        return collection.get().await().toObject(UserDetails::class.java)!!
    }

    suspend fun getCourseList(): DocumentSnapshot? {

        val collection = firestore.collection("filterLists").document("courseList")

        return collection.get().await()
    }

    suspend fun getBranchList(course: String): DocumentSnapshot? {

        val collection = firestore.collection("filterLists").document(course)
            .collection("branch").document("branchList")

        return collection.get().await()
    }

    suspend fun getSubjectList(course: String, branch: String): DocumentSnapshot? {

        val collection = firestore.collection("filterLists").document(course)
            .collection(branch).document("subjectList")

        return collection.get().await()
    }
}