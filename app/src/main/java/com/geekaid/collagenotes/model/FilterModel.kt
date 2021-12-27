package com.geekaid.collagenotes.model

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.local.ReferenceSet
import java.lang.ref.Reference

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

data class ListFetch1(
    var list: List<DocumentReference> = listOf()
)