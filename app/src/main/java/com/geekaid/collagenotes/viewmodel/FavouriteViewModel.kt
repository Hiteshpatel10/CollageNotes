package com.geekaid.collagenotes.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.geekaid.collagenotes.model.FileUploadModel

class FavouriteViewModel: ViewModel() {

    var favouriteList: MutableState<ArrayList<FileUploadModel>> = mutableStateOf(arrayListOf())

}