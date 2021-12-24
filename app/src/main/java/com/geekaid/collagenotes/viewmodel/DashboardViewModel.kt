package com.geekaid.collagenotes.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekaid.collagenotes.model.*
import com.geekaid.collagenotes.repo.Repository
import com.google.firebase.firestore.QuerySnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var filter: MutableState<FilterModel> = mutableStateOf(FilterModel())
    var notesList: MutableState<MutableList<FileUploadModel>> = mutableStateOf(mutableListOf())
    var favouriteList: MutableState<List<FileUploadModel>> = mutableStateOf(mutableListOf())
    var userDetails: MutableState<UploaderDetailModel?> = mutableStateOf(UploaderDetailModel())
    var uploaderDetails: MutableState<UploaderDetailModel?> = mutableStateOf(UploaderDetailModel())
    var userUploadList: MutableState<List<FileUploadModel>> = mutableStateOf(mutableListOf())

    // to store lists fetch in filterScreen.kt
    var courseList: MutableState<ListFetch> = mutableStateOf(ListFetch())
    var branchList: MutableState<ListFetch> = mutableStateOf(ListFetch())
    var subjectList: MutableState<ListFetch> = mutableStateOf(ListFetch())
    var noteTypeList: MutableState<ListFetch> = mutableStateOf(ListFetch())

    //
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
    fun getFavouriteNotes() =
        repository.getFavouriteNotes(
            notesType = notesType.value,
            orderBy = orderBy.value,
            favouriteSpace = favouriteSpace.value
        )

    @ExperimentalCoroutinesApi
    fun getUserUploadList(email: String): Flow<QuerySnapshot?> {
        return repository.getUserUploadList(email = email)
    }

    // functions to fetch filter list
    suspend fun getCourseLists() = repository.getCourseList()

    suspend fun getBranchList(course: String): ListFetch? {
        return repository.getBranchList(course)
    }

    suspend fun getSubjectList(course: String, branch: String): ListFetch? {
        return repository.getSubjectList(course = course, branch = branch)
    }

    suspend fun getNoteTypeList() = repository.getNoteTypeList()

}