package com.geekaid.collagenotes.model

data class DataOrException<T, E : Exception?>(
    var data: T? = null,
    var error: E? = null
)
