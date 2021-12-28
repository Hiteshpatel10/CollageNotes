package com.geekaid.collagenotes.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp


@Composable
fun TextIcon(textString: String, imageVector: ImageVector, iconBefore: Boolean = false) {
    Row {
        if (iconBefore) {
            Icon(imageVector = imageVector, contentDescription = "")
            Spacer(modifier = Modifier.padding(4.dp))
        }

        Text(text = textString)

        if (!iconBefore) {
            Spacer(modifier = Modifier.padding(4.dp))
            Icon(imageVector = imageVector, contentDescription = "")
        }
    }
}