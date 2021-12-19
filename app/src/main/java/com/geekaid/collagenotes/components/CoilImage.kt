package com.geekaid.collagenotes.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import timber.log.Timber

@Composable
fun CoilImage(imageUri: String?) {
    Box(
        modifier = Modifier.size(100.dp).clip(CircleShape),
    ) {
        if (imageUri.isNullOrEmpty())
            Image(
                Icons.Filled.Error,
                contentDescription = "place holder",
                contentScale = ContentScale.Crop
            )
        else {
            val painter = rememberImagePainter(data = imageUri)

            Image(
                painter = painter,
                contentDescription = "Profile",
                contentScale = ContentScale.Crop
            )
            Timber.i(imageUri)
        }
    }
}
