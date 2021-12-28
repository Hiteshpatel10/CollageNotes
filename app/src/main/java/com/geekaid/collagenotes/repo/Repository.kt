package com.geekaid.collagenotes.repo

import com.geekaid.collagenotes.model.FileUploadModel
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
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
    fun getFavouriteNotes(notesType: String, orderBy: String, favouriteSpace: String) =
        callbackFlow {
            val collection =
                firestore.collection("Users").document(auth.currentUser?.email.toString())
                    .collection("Favourite").document(favouriteSpace).collection(notesType)
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

    @ExperimentalCoroutinesApi
    fun getFavNoteRef(favSpaceName: String, notesType: String) = callbackFlow {
        val a = firestore.collection("Users").document(auth.currentUser?.email.toString())
            .collection(favSpaceName).document(notesType)

        val snapshotListener = a.addSnapshotListener { value, error ->
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


//    @ExperimentalCoroutinesApi
//    fun getFavNote(favNoteRefList: List<String>) = callbackFlow {
//
//        val favNotesList: MutableList<FileUploadModel> = mutableListOf()
//
//        favNoteRefList.forEach { noteRef ->
//            firestore.document(noteRef).get()
//                .addOnSuccessListener { note ->
//                    note.toObject(FileUploadModel::class.java).let {
//                        it?.let { it1 ->
//                            favNotesList.add(it1)
//                            trySend(favNotesList)
//                        }
//                    }
//                }
//        }
//        Timber.i(favNoteRefList.toString())
//    }

    fun getFavNote(favNoteRefList: List<String>) {

        val favNotesList: MutableList<FileUploadModel> = mutableListOf()

        val list = flow<List<FileUploadModel>> {
            favNoteRefList.forEach { noteRef ->
                firestore.document(noteRef).get()
                    .addOnSuccessListener { note ->
                        note.toObject(FileUploadModel::class.java).let {
                            it?.let { it1 -> favNotesList.add(it1) }
                        }
                    }

            }
            emit(favNotesList)
        }
    }
}
