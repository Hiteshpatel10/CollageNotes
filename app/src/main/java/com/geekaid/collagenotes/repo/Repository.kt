package com.geekaid.collagenotes.repo

import android.app.DownloadManager
import com.geekaid.collagenotes.model.FilterModel
import com.geekaid.collagenotes.model.ListFetch
import com.geekaid.collagenotes.model.UploaderDetailModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
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
    fun getNotes(filter: FilterModel, notesType: String, orderBy: String) = callbackFlow {

        val noteRef = firestore.collection("courses").document(filter.course)
            .collection(filter.branch).document(filter.subject)
            .collection(notesType).orderBy(orderBy, Query.Direction.DESCENDING)

        val snapshotListener = noteRef.addSnapshotListener { value, error ->
            if (error == null)
                trySend(value)
        }

        awaitClose {
            snapshotListener.remove()
        }
    }

    @ExperimentalCoroutinesApi
    fun getFavouriteNotes(notesType: String, orderBy: String) = callbackFlow {
        val collection = firestore.collection("Users").document(auth.currentUser?.email.toString())
            .collection("Favourite").document("fav1").collection(notesType)
            .orderBy(orderBy, Query.Direction.DESCENDING)

        val snapshotListener = collection.addSnapshotListener { value, error ->
            if (error == null)
                trySend(value)
        }

        awaitClose {
            snapshotListener.remove()
        }
    }

    @ExperimentalCoroutinesApi
    fun getUserUploadList(email: String) = callbackFlow {
        val collection = firestore.collection("Users").document(email)
            .collection("uploads")

        val snapshotListener = collection.addSnapshotListener { value, error ->
            if (error == null)
                trySend(value)
        }

        awaitClose {
            snapshotListener.remove()
        }
    }

    suspend fun getUserDetails(email: String): UploaderDetailModel? {
        val collection = firestore.collection("Users").document(email)
            .collection("UserData").document("UserInfo")

        return collection.get().await().toObject(UploaderDetailModel::class.java)
    }

    suspend fun getCourseList(): ListFetch? {
        val collection = firestore.collection("filterLists").document("courseList")

        return collection.get().await().toObject(ListFetch::class.java)
    }


    suspend fun getBranchList(course: String): ListFetch? {
        val collection = firestore.collection("filterLists").document(course)
            .collection("branch").document("branchList")

        return collection.get().await().toObject(ListFetch::class.java)
    }

    suspend fun getSubjectList(course: String, branch: String): ListFetch? {
        val collection = firestore.collection("filterLists").document(course)
            .collection(branch).document("subjectList")

        return collection.get().await().toObject(ListFetch::class.java)
    }

    suspend fun getNoteTypeList(): ListFetch? {
        val collection = firestore.collection("filterLists").document("noteType")

        return collection.get().await().toObject(ListFetch::class.java)
    }
}