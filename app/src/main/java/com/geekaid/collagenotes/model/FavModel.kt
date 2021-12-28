package com.geekaid.collagenotes.model

data class LikeFav(
    var user: String = "",
    var favSpaceName: String = ""
)

data class StringValuePair(
    var string: String = "",
    var value: String = ""
)
