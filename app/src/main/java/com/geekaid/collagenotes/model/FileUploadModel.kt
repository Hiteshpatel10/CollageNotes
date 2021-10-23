package com.geekaid.collagenotes.model

data class FileUploadModel(
    var branch: String = "",
    var course: String = "",
    var data: String = "",
    var fileName: String = "",
    var fileMime: String = "",
    var fav: Boolean = true,
    var fileUploadPath: String = "",
    var subject: String = "",
    var year: String = "",
    val likes: ArrayList<String> = arrayListOf(),
    val dislikes: ArrayList<String> = arrayListOf()
)
