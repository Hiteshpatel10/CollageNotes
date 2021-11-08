package com.geekaid.collagenotes.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekaid.collagenotes.model.FileUploadModel
import com.geekaid.collagenotes.model.FilterModel
import com.geekaid.collagenotes.model.UserDetails
import com.geekaid.collagenotes.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var filter: MutableState<FilterModel> = mutableStateOf(FilterModel())
    var courseList: MutableState<MutableList<FileUploadModel>> = mutableStateOf(mutableListOf())
    var favouriteList: MutableState<List<FileUploadModel>> = mutableStateOf(mutableListOf())
    private var userDetails: MutableState<UserDetails> = mutableStateOf(UserDetails())


    @ExperimentalCoroutinesApi
    fun getFilter() = repository.getFilter()

    @ExperimentalCoroutinesApi
    fun getNotes() = repository.getNotes(filter = filter.value)

    @ExperimentalCoroutinesApi
    fun getFavouriteNotes() = repository.gerFavouriteNotes()


    fun getUserDetails(): UserDetails {
        viewModelScope.launch {
            userDetails.value = repository.getUserDetails()
        }
        return userDetails.value
    }

}