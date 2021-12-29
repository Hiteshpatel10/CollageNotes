package com.geekaid.collagenotes.repo

import com.geekaid.collagenotes.model.FilterModel
import com.geekaid.collagenotes.model.ListFetch
import com.geekaid.collagenotes.model.UploaderDetailModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Singleton

@Singleton
class Repository {

    private val auth = Firebase.auth
    private val firestore = FirebaseFirestore.getInstance()

    //To get filter uploaded by the user
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

    //To get notes on the basis of filter details provided by the user
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

    //To get notes uploaded by the uploaded/user
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

    //To get uploader/user Details
    suspend fun getUserDetails(email: String): UploaderDetailModel? {
        val collection = firestore.collection("Users").document(email)
            .collection("UserData").document("UserInfo")

        return collection.get().await().toObject(UploaderDetailModel::class.java)
    }

    //To get course list
    suspend fun getCourseList(): ListFetch? {
        val collection = firestore.collection("filterLists").document("courseList")

        return collection.get().await().toObject(ListFetch::class.java)
    }

    //To get branch list
    suspend fun getBranchList(course: String): ListFetch? {
        val collection = firestore.collection("filterLists").document(course)
            .collection("branch").document("branchList")

        return collection.get().await().toObject(ListFetch::class.java)
    }

    //To get subjects list
    suspend fun getSubjectList(course: String, branch: String): ListFetch? {
        val collection = firestore.collection("filterLists").document(course)
            .collection(branch).document("subjectList")

        return collection.get().await().toObject(ListFetch::class.java)
    }

    //To get note type list
    suspend fun getNoteTypeList(): ListFetch? {
        val collection = firestore.collection("filterLists").document("noteType")

        return collection.get().await().toObject(ListFetch::class.java)
    }

    //To get favourite note document path list
    @ExperimentalCoroutinesApi
    fun getFavNoteRef(favSpaceName: String, notesType: String) = callbackFlow {
        val collection = firestore.collection("Users").document(auth.currentUser?.email.toString())
            .collection(favSpaceName).document(notesType)

        val snapshotListener = collection.addSnapshotListener { value, error ->
            if (error == null) {
                trySendBlocking(value)
                    .onFailure {
                        Timber.i(it)
                    }
            }

        }

        awaitClose {
            snapshotListener.remove()
        }
    }
}
