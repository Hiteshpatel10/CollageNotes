package com.geekaid.collagenotes.model

data class FileUploadModel(
    var branch: String = "",
    var course: String = "",
    var data: String = "",
    var fileName: String = "",
    var fileMime: String = "",
    var favourite: MutableList<String> = mutableListOf(),
    var fileUploadPath: String = "",
    var subject: String = "",
    var year: String = "",
    val likes: MutableList<String> = mutableListOf(),
    val dislikes: MutableList<String> = mutableListOf()
)
