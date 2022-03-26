package com.geekaid.collegenotes.model

import androidx.annotation.Keep

@Keep
data class UploaderDetailModel(
    var firstName: String = "",
    var lastName: String = "",
    var uploaderType: String = "",
    var qualification: String = "",
    var institutionAssociatedWith: String = "",
    var profileUri: String = "",
    var about: String = "",
    var verified: Boolean = false,
    var uploaded: Long = 0,
    var downloadedTimes: Long = 0,
    var likes: Long = 0,

    //Social
    var instagram: String = "",
    var youtube: String = "",
    var twitter: String = "",
    var linkedIn: String = "",
)
