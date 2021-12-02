package com.geekaid.collagenotes.model

import androidx.compose.runtime.MutableState

data class FilterModel(
    var course: String = "",
    var branch: String = "",
    var subject: String = "",
)

data class FilterListsModel(
    var course: List<String> = listOf(),
    var branch: List<String> = listOf(),
    var subject: List<String> = listOf(),
)

data class ListFetch(
    var list: List<String> = listOf()
)