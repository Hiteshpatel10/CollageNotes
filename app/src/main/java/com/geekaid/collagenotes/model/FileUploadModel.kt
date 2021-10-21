package com.geekaid.collagenotes.model

data class FileUploadModel(
    var branch: String = "",
    var course: String = "",
    var fileName: String = "",
    var fileMime: String = "",
    var fileUploadPath: String = "",
    var subject: String = "",
    var year: String = "",
)
