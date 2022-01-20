package com.geekaid.collegenotes.components.noteLayoutComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import com.geekaid.collegenotes.components.HeadingValueStyle
import com.geekaid.collegenotes.model.FileUploadModel

@Composable
fun NoteDetails(note: FileUploadModel, isExpanded: Boolean) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp)
            .layoutId("noteDetails"),
    ) {

        if (isExpanded) {
            HeadingValueStyle(heading = "Format", value = note.fileInfo.fileMime, isSpacer = true)
            HeadingValueStyle(heading = "Course", value = note.course, isSpacer = true)
            HeadingValueStyle(heading = "Branch", value = note.branch, isSpacer = true)
            HeadingValueStyle(heading = "Subject", value = note.subject, isSpacer = true,)
            HeadingValueStyle(heading = "Upload Date", value = note.date, isSpacer = true)
            HeadingValueStyle(
                heading = "Description",
                value = note.fileInfo.fileDescription,
                isSpacer = true
            )
            Spacer(modifier = Modifier.padding(24.dp))
        } else {
            HeadingValueStyle(heading = "Format", value = note.fileInfo.fileMime, isSpacer = true)
            HeadingValueStyle(heading = "Subject", value = note.subject, isSpacer = true, maxLines = true)
            HeadingValueStyle(heading = "Upload Date", value = note.date, isSpacer = true)
            HeadingValueStyle(
                heading = "Description",
                value = note.fileInfo.fileDescription,
                isSpacer = true,
            )
        }
    }
}