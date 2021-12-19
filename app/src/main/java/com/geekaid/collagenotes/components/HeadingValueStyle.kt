package com.geekaid.collagenotes.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.geekaid.collagenotes.ui.theme.CollageNotesTheme

@Composable
fun HeadingValueStyle(heading: String, value: String, isSpacer: Boolean) {

    var headingColor by remember { mutableStateOf(Color.Blue) }
    var valueColor by remember { mutableStateOf(Color.Blue) }

    headingColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    valueColor = if (isSystemInDarkTheme()) Color.White else Color.DarkGray

    CollageNotesTheme {
        Surface {
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily.SansSerif,
                            color = headingColor
                        )
                    ) {
                        append("$heading : ")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Normal,
                            color = valueColor
                        )
                    ) {
                        append(value)
                    }
                },
                modifier = Modifier.padding(bottom = 2.dp)
            )

            if (isSpacer)
                Spacer(modifier = Modifier.padding(6.dp))

        }

    }
}