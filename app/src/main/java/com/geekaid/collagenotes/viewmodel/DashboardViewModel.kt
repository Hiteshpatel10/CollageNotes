package com.geekaid.collagenotes.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekaid.collagenotes.model.*
import com.geekaid.collagenotes.repo.Repository
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var filter: MutableState<FilterModel> = mutableStateOf(FilterModel())
    var notesList: MutableState<MutableList<FileUploadModel>> = mutableStateOf(mutableListOf())
    var favouriteList: MutableState<List<FileUploadModel>> = mutableStateOf(mutableListOf())
    var userDetails: MutableState<UserDetails> = mutableStateOf(UserDetails())

    // to store lists fetch in filterScreen.kt
    var courseList: MutableState<ListFetch> = mutableStateOf(ListFetch())
    var branchList: MutableState<ListFetch> = mutableStateOf(ListFetch())
    var subjectList: MutableState<ListFetch> = mutableStateOf(ListFetch())

    //function to get the user detail from firebase
    fun getDetails() {
        viewModelScope.launch { userDetails.value = repository.getUserDetails() }
    }

    // functions to get data from firebase
    @ExperimentalCoroutinesApi
    fun getFilter() = repository.getFilter()

    @ExperimentalCoroutinesApi
    fun getNotes() = repository.getNotes(filter = filter.value)

    @ExperimentalCoroutinesApi
    fun getFavouriteNotes() = repository.gerFavouriteNotes()

    @ExperimentalCoroutinesApi
    fun getFilterLists() = repository.getFilterLists()


    // functions to fetch filter list
    suspend fun getCourseLists() = repository.getCourseList()

    suspend fun getBranchList(course: String): DocumentSnapshot? {
        return repository.getBranchList(course)
    }

    suspend fun getSubjectList(course: String, branch: String): DocumentSnapshot? {
        return repository.getSubjectList(course = course, branch = branch)
    }

}