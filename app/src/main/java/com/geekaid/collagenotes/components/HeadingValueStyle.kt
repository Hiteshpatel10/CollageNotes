package com.geekaid.collagenotes.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.geekaid.collagenotes.ui.theme.CollageNotesTheme

@Composable
fun HeadingValueStyle(heading: String, value: String, isSpacer: Boolean) {

    CollageNotesTheme {
        Surface {
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily.SansSerif
                        )
                    ) {
                        append("$heading : ")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Normal
                        )
                    ) {
                        append(value)
                    }
                },
                modifier = Modifier.padding(bottom = 2.dp)
            )

            if (isSpacer)
                Spacer(modifier = Modifier.padding(8.dp))

        }

    }
}