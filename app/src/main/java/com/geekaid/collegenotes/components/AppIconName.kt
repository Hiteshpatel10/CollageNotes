package com.geekaid.collegenotes.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.geekaid.collegenotes.R

@Composable
fun AppIconName(spacerBottom: Boolean = true) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Image(
            painter = rememberImagePainter(R.drawable.collage_notes_icon),
            contentDescription = "Collage Notes Icon",
            modifier = Modifier
                .size(90.dp)
                .clip(shape = CircleShape)
        )

        Text(
            text = stringResource(id = R.string.app_name),
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            modifier = Modifier.padding(8.dp)
        )

        if (spacerBottom)
            Spacer(modifier = Modifier.padding(bottom = 32.dp))


    }
}