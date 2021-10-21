package com.geekaid.collagenotes.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet

@Composable
fun DTest() {

    val constraintSet = ConstraintSet {
        val noteDetail = createRefFor("noteDetail")
        val noteSidebar = createRefFor("noteSidebar")
        val noteVote = createRefFor("noteVote")

        constrain(noteDetail) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
        }

        constrain(noteSidebar) {
            top.linkTo(parent.top)
            end.linkTo(parent.end)
        }

        constrain(noteVote) {
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
        }
    }

    Card(modifier = Modifier.padding(2.dp)) {
        ConstraintLayout(
            constraintSet = constraintSet, modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
        ) {
            Column(modifier = Modifier.layoutId("noteDetail")) {
                Text(text = "Subject: Computer Science ")
                Spacer(modifier = Modifier.padding(2.dp))
                Text(text = "Format: Pdf")
            }

            Column(modifier = Modifier.layoutId("noteSidebar")) {
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

            Row(modifier = Modifier.layoutId("noteVote")) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.ThumbUp, contentDescription = "Upvote")
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.ThumbDown, contentDescription = "Upvote")
                }
            }
        }
    }
}