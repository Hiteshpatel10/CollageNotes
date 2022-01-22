package com.geekaid.collegenotes.ui.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.geekaid.collegenotes.R
import com.geekaid.collegenotes.firebaseDao.screenDao.uploaderDetailDao
import com.geekaid.collegenotes.model.UploaderDetailModel
import com.geekaid.collegenotes.navigation.BottomNavScreen

@Composable
fun UserSocialMediaLinks(
    modifier: Modifier = Modifier,
    uploaderDetailModel: UploaderDetailModel,
    imageUri: Uri?,
    navController: NavHostController
) {

    var instagram by remember { mutableStateOf(uploaderDetailModel.instagram) }
    var youtube by remember { mutableStateOf(uploaderDetailModel.youtube) }
    var twitter by remember { mutableStateOf(uploaderDetailModel.twitter) }
    val context = LocalContext.current


    Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {

        Column(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(8.dp)
        ) {

            Text(
                text = AnnotatedString("Enter your social media profile links"),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            )

            OutlinedTextField(
                value = instagram,
                onValueChange = { instagram = it },
                label = { Text(text = "Instagram Username") },
                leadingIcon = {
                    Image(
                        painter = rememberImagePainter(R.drawable.instagram_icon),
                        contentDescription = ""
                    )
                },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )

            OutlinedTextField(
                value = youtube,
                onValueChange = { youtube = it },
                label = { Text(text = "Youtube Channel Link") },
                leadingIcon = {
                    Image(
                        painter = rememberImagePainter(R.drawable.youtube_icon),
                        contentDescription = ""
                    )
                },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )


            OutlinedTextField(
                value = twitter,
                onValueChange = { twitter = it },
                label = { Text(text = "Twitter Username") },
                leadingIcon = {
                    Image(
                        painter = rememberImagePainter(R.drawable.twitter_icon),
                        contentDescription = ""
                    )
                },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )

        }

        Button(onClick = {

            uploaderDetailModel.instagram = instagram
            uploaderDetailModel.youtube = youtube
            uploaderDetailModel.twitter = twitter

            uploaderDetailDao(
                uploaderDetailModel = uploaderDetailModel,
                imageUri = imageUri,
                context = context
            ).also {
                navController.navigate(BottomNavScreen.DashboardNav.route)
            }
        }, modifier = modifier.padding(64.dp)) {
            Text(text = "Save")
        }
    }
}