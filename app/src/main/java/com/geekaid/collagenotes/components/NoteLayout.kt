package com.geekaid.collagenotes.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NoteLayout() {
    Column {
        Text(text = "Subject: Computer Science")
        Spacer(modifier = Modifier.padding(2.dp))
        Text(text = "Format: Pdf")
        Spacer(modifier = Modifier.padding(2.dp))
        Vote()
        NoteSidebar()

    }
}

@Composable
fun Vote() {
    Row {
        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth(.5f)) {
            Icon(Icons.Filled.ThumbUp, contentDescription = "Upvote")
        }
        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth(.5f)) {
            Icon(Icons.Filled.ThumbDown, contentDescription = "Upvote")
        }

    }
}

@Composable
fun NoteSidebar() {
    Column {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Filled.Share, contentDescription = "Share")
        }

        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Filled.Favorite, contentDescription = "Favourite" )
        }

        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Filled.Download, contentDescription = "Download" )
        }
    }
}

@Preview
@Composable
fun NoteLayoutPrev() {
    NoteLayout()
}
