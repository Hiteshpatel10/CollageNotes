package com.geekaid.collagenotes.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.geekaid.collagenotes.model.FileUploadModel
import com.geekaid.collagenotes.model.FilterModel
import com.geekaid.collagenotes.model.ListFetch
import com.geekaid.collagenotes.model.UploaderDetailModel
import com.geekaid.collagenotes.repo.Repository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val firestore = Firebase.firestore
    var filter: MutableState<FilterModel> = mutableStateOf(FilterModel())
    var notesList: MutableState<MutableList<FileUploadModel>> = mutableStateOf(mutableListOf())
    var userDetails: MutableState<UploaderDetailModel?> = mutableStateOf(UploaderDetailModel())
    var uploaderDetails: MutableState<UploaderDetailModel?> = mutableStateOf(UploaderDetailModel())
    var userUploadList: MutableState<List<FileUploadModel>> = mutableStateOf(mutableListOf())
    var favDocRefList: MutableState<ListFetch> = mutableStateOf(ListFetch())

    //To store lists fetch in filterScreen.kt
    var courseList: MutableState<ListFetch> = mutableStateOf(ListFetch())
    var branchList: MutableState<ListFetch> = mutableStateOf(ListFetch())
    var subjectList: MutableState<ListFetch> = mutableStateOf(ListFetch())
    var noteTypeList: MutableState<ListFetch> = mutableStateOf(ListFetch())

    //To store filter/sort order selected by the user
    var notesType: MutableState<String> = mutableStateOf("notes")
    var orderBy: MutableState<String> = mutableStateOf("date")
    var favouriteSpace: MutableState<String> = mutableStateOf("fav1")

    //function to get the user detail from firebase
    suspend fun getDetails(email: String): UploaderDetailModel? {
        return repository.getUserDetails(email = email)
    }

    // functions to get data from firebase
    @ExperimentalCoroutinesApi
    fun getFilter() = repository.getFilter()

    @ExperimentalCoroutinesApi
    fun getNotes() = repository.getNotes(
        filter = filter.value,
        notesType = notesType.value,
        orderBy = orderBy.value
    )

    @ExperimentalCoroutinesApi
    fun getUserUploadList(email: String): Flow<QuerySnapshot?> {
        return repository.getUserUploadList(email = email)
    }

    suspend fun getCourseLists() = repository.getCourseList()

    suspend fun getBranchList(course: String): ListFetch? {
        return repository.getBranchList(course)
    }

    suspend fun getSubjectList(course: String, branch: String): ListFetch? {
        return repository.getSubjectList(course = course, branch = branch)
    }

    suspend fun getNoteTypeList() = repository.getNoteTypeList()

    @ExperimentalCoroutinesApi
    fun getFN(): Flow<DocumentSnapshot?> {
        return repository.getFavNoteRef(
            favSpaceName = favouriteSpace.value,
            notesType = notesType.value
        )
    }

    //To get all the favourite note using the document path list
    val list = flow<List<FileUploadModel>> {
        val list: MutableList<FileUploadModel> = mutableListOf()
        favDocRefList.value.list.forEach { noteRef ->
            firestore.document(noteRef).get()
                .addOnSuccessListener { documentSnapshot ->
                    documentSnapshot.toObject(FileUploadModel::class.java)?.let { list.add(it) }
                }
            emit(list)
        }
    }
}
