package com.geekaid.collagenotes.model

import androidx.compose.runtime.MutableState
import androidx.lifecycle.Lifecycle

data class FilterModel(
    var course: String = "",
    var branch: String = "",
    var subject: String = "",
)