package com.geekaid.collegenotes.model

import androidx.annotation.Keep

@Keep
data class FilterModel(
    var course: String = "",
    var branch: String = "",
    var subject: String = "",
)


@Keep
data class ListFetch(
    var list: List<String> = listOf()
)

