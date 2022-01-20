package com.geekaid.collegenotes.model

import androidx.annotation.Keep

@Keep
data class ReportModel(
    var reportedBy: MutableList<String> = mutableListOf()
)
