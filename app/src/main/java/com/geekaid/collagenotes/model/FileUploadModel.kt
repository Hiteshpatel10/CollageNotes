package com.geekaid.collagenotes.model

data class FileUploadModel(
    var branch: String = "",
    var course: String = "",
    var date: String = "",
    var noteType: String = "",
    var downloadedTimes: Long = 0,
    var favSpaceName: String = "",
    val fileInfo: FileMeta = FileMeta(),
    var favourite: MutableList<String> = mutableListOf(),
    var subject: String = "",
    val likes: MutableList<String> = mutableListOf(),
)

data class FileMeta(
    var fileName: String = "",
    var fileMime: String = "",
    var fileUploadPath: String = "",
    var fileDescription: String = "",
    var uploadedBy: String = "",
    var uploaderEmail: String = ""
)
