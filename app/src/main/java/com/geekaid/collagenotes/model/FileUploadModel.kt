package com.geekaid.collagenotes.model

data class FileUploadModel(
    var branch: String = "",
    var course: String = "",
    var date: String = "",
    var downloadedTimes: Long = 0,
    val fileInfo: FileInfo = FileInfo(),
    var favourite: MutableList<String> = mutableListOf(),
    var subject: String = "",
    val likes: MutableList<String> = mutableListOf(),
)

data class FileInfo(
    var fileName: String = "",
    var fileMime: String = "",
    var fileUploadPath: String = "",
    var fileDescription: String = "",
    var uploadedBy: String = ""
)
