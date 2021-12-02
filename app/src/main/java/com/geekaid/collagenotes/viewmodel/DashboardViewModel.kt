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
    var courseList: MutableState<MutableList<FileUploadModel>> = mutableStateOf(mutableListOf())
    var courseList1: MutableState<ListFetch> = mutableStateOf(ListFetch())
    var branchList12: MutableState<ListFetch> = mutableStateOf(ListFetch())
    var favouriteList: MutableState<List<FileUploadModel>> = mutableStateOf(mutableListOf())
    var filterLists: MutableState<List<FilterListsModel>> = mutableStateOf(mutableListOf())
    var userDetails: MutableState<UserDetails> = mutableStateOf(UserDetails())

    fun getDetails() {
        viewModelScope.launch {
            userDetails.value = repository.getUserDetails()
        }
    }

    @ExperimentalCoroutinesApi
    fun getFilter() = repository.getFilter()

    @ExperimentalCoroutinesApi
    fun getNotes() = repository.getNotes(filter = filter.value)

    @ExperimentalCoroutinesApi
    fun getFavouriteNotes() = repository.gerFavouriteNotes()

    @ExperimentalCoroutinesApi
    fun getFilterLists() = repository.getFilterLists()

    suspend fun getCourseLists() = repository.getCourseList()

    suspend fun getBranchList(course: String): DocumentSnapshot? {
        return repository.getBranchList(course)
    }

}