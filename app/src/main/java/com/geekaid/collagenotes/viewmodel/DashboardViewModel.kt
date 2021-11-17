package com.geekaid.collagenotes.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekaid.collagenotes.model.FileUploadModel
import com.geekaid.collagenotes.model.FilterModel
import com.geekaid.collagenotes.model.UserDetails
import com.geekaid.collagenotes.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class DashboardViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var filter: MutableState<FilterModel> = mutableStateOf(FilterModel())
    var courseList: MutableState<MutableList<FileUploadModel>> = mutableStateOf(mutableListOf())
    var favouriteList: MutableState<List<FileUploadModel>> = mutableStateOf(mutableListOf())
    var userDetails: MutableState<UserDetails> = mutableStateOf(UserDetails())
    val progressBar: MutableState<Boolean> = mutableStateOf(value = false)


    init {
        viewModelScope.launch {
            userDetails.value = repository.getUserDetails()
        }
    }

    fun getFilter() = repository.getFilter()

    fun getNotes() = repository.getNotes(filter = filter.value)

    fun getFavouriteNotes() = repository.gerFavouriteNotes()


    fun get() {
        viewModelScope.launch {
            repository.gerFavouriteNotes().collect {
                Timber.i("get called")
                if (it != null) {
                    favouriteList.value = it.toObjects(FileUploadModel::class.java)
                    Timber.i(it.toObjects(FileUploadModel::class.java).toString())
                }
            }
        }
    }


}