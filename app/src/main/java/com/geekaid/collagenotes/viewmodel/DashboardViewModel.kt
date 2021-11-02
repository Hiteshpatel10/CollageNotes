package com.geekaid.collagenotes.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.geekaid.collagenotes.model.DataOrException
import com.geekaid.collagenotes.model.FileUploadModel
import com.geekaid.collagenotes.model.FilterModel
import com.geekaid.collagenotes.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var filter: MutableState<FilterModel> = mutableStateOf(FilterModel())
    var courseList: MutableState<MutableList<FileUploadModel>> = mutableStateOf(mutableListOf())

    var notes: MutableState<DataOrException<List<FileUploadModel>,Exception>> = mutableStateOf(
        DataOrException(
            listOf(),
            Exception("")
        )
    )

    @ExperimentalCoroutinesApi
    fun getFilter() = repository.getFilter()

    fun getNotes() = repository.getNotes(filter = filter.value)


}