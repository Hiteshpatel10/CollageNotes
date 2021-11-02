package com.geekaid.collagenotes.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.geekaid.collagenotes.model.FileUploadModel
import com.geekaid.collagenotes.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    var favouriteList: MutableState<ArrayList<FileUploadModel>> = mutableStateOf(arrayListOf())

    @ExperimentalCoroutinesApi
    fun getFavouriteNotes()  = repository.gerFavouriteNotes()

}