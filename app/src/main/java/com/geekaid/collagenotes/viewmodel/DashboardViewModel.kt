package com.geekaid.collagenotes.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.geekaid.collagenotes.model.FileUploadModel
import com.geekaid.collagenotes.model.FilterModel

class DashboardViewModel() : ViewModel() {

    var filter = mutableStateOf(FilterModel())
    var courseList = mutableStateOf<ArrayList<FileUploadModel>>(arrayListOf())

}