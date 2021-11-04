package com.geekaid.collagenotes.model

data class FileUploadModel(
    var branch: String = "",
    var course: String = "",
    var data: String = "",
    var description: String = "",
    var fileName: String = "",
    var fileMime: String = "",
    var favourite: MutableList<String> = mutableListOf(),
    var fileUploadPath: String = "",
    var subject: String = "",
    val likes: MutableList<String> = mutableListOf(),
)
