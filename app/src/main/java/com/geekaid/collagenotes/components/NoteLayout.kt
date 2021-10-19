package com.geekaid.collagenotes.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.geekaid.collagenotes.model.FileUploadModel

@Composable
fun NoteLayout(course: ArrayList<FileUploadModel>,modifier: Modifier) {
    
    Card(modifier.padding(2.dp)) {
        Column(modifier.padding(4.dp)) {
           LazyColumn{
                items(course){  item->
                    Row {
                        NoteDetails(item,modifier)
                        NoteSidebar(modifier)
                    }
                }
           }
        }
    }
}



@Composable
fun NoteDetails(course: FileUploadModel, modifier: Modifier) {
    Column(modifier.fillMaxWidth(0.9f)) {
        Text(text = "Subject: Computer Science ${course.subject}")
        Spacer(modifier = Modifier.padding(2.dp))
        Text(text = "Format: Pdf ${course.fileMime}")
        Spacer(modifier = Modifier.padding(2.dp))
        Vote(modifier.padding(top = 50.dp))
    }
}

@Composable
fun NoteSidebar(modifier: Modifier) {
    Column(modifier) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Filled.Share, contentDescription = "Share")
        }

        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Filled.Favorite, contentDescription = "Favourite")
        }

        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Filled.Download, contentDescription = "Download")
        }
    }
}

@Composable
fun Vote(modifier: Modifier) {
    Row(modifier) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Filled.ThumbUp, contentDescription = "Upvote")
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Filled.ThumbDown, contentDescription = "Upvote")
        }
    }
}


