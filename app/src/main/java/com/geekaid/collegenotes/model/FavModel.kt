package com.geekaid.collegenotes.model

import androidx.annotation.Keep


@Keep
data class StringValuePair(
    var string: String = "",
    var value: String = ""
)
