package com.geekaid.collegenotes.components.userProfileComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.geekaid.collegenotes.components.HeadingValueStyle
import com.geekaid.collegenotes.model.UploaderDetailModel

@Composable
fun ProfileUploaderDetail(uploaderDetail: UploaderDetailModel?) {

    Card(modifier = Modifier
        .padding(vertical = 4.dp)
        .fillMaxWidth()) {

        Column(modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)) {

            HeadingValueStyle(
                heading = "Type",
                value = uploaderDetail?.uploaderType.toString(),
                isSpacer = true
            )

            HeadingValueStyle(
                heading = "Qualification",
                value = uploaderDetail?.qualification.toString(),
                isSpacer = true
            )

            HeadingValueStyle(
                heading = "Institution Associated With",
                value = uploaderDetail?.institutionAssociatedWith.toString(),
                isSpacer = true
            )

            HeadingValueStyle(
                heading = "About",
                value = uploaderDetail?.about.toString(),
                isSpacer = true
            )
        }

    }
}