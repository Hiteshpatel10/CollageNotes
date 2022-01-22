package com.geekaid.collegenotes.components.userProfileComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.geekaid.collegenotes.R
import com.geekaid.collegenotes.components.CoilImage
import com.geekaid.collegenotes.model.UploaderDetailModel
import com.geekaid.collegenotes.navigation.BottomNavScreen
import com.geekaid.collegenotes.util.socialMediaLinkOpenIntent
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun ProfileTopBar(uploaderDetails: UploaderDetailModel?, email: String?, navController: NavController) {

    val currentUser = Firebase.auth.currentUser!!
    val context = LocalContext.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = 32.dp)
    ) {
        if (uploaderDetails?.profileUri?.isNotEmpty() == true)
            CoilImage(imageUri = uploaderDetails.profileUri)
        else
            Image(
                Icons.Filled.Face, contentDescription = "No Profile Image",
                modifier = Modifier.size(100.dp)
            )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "${uploaderDetails?.firstName?.uppercase()} ${uploaderDetails?.lastName?.uppercase()}")

                Spacer(modifier = Modifier.padding(4.dp))

                if (email == currentUser.email)
                    Image(
                        Icons.Filled.Edit,
                        contentDescription = "Edit Profile",
                        modifier = Modifier.clickable(
                            enabled = true,
                            onClickLabel = "Clickable image",
                            onClick = {
                                navController.navigate(BottomNavScreen.UserProfileEditScreenNav.route)
                            }
                        ))
            }

            if (uploaderDetails?.instagram?.isNotEmpty() == true || uploaderDetails?.youtube?.isNotEmpty() == true || uploaderDetails?.twitter?.isNotEmpty() == true)
                Text(text = "Follow At")

            Row {
                if (uploaderDetails?.instagram?.isNotEmpty() == true)
                    IconButton(onClick = {
                        socialMediaLinkOpenIntent(
                            context = context,
                            intentUri = "http://instagram.com/_u/${uploaderDetails.instagram}",
                            packageName = "com.instagram.android",
                            webLink = "http://instagram.com/${uploaderDetails.instagram}"
                        )
                    }) {
                        Image(
                            painter = rememberImagePainter(R.drawable.instagram_icon),
                            contentDescription = "Instagram profile link"
                        )
                    }

                if (uploaderDetails?.youtube?.isNotEmpty() == true)
                    IconButton(onClick = {
                        socialMediaLinkOpenIntent(
                            context = context,
                            intentUri = uploaderDetails.youtube,
                            packageName = "com.google.android.youtube",
                            webLink = uploaderDetails.youtube
                        )
                    }) {
                        Image(
                            painter = rememberImagePainter(R.drawable.youtube_icon),
                            contentDescription = "Instagram profile link"
                        )
                    }

                if (uploaderDetails?.twitter?.isNotEmpty() == true)
                    IconButton(onClick = {
                        socialMediaLinkOpenIntent(
                            context = context,
                            intentUri = "twitter://user?user_id=${uploaderDetails.twitter}",
                            packageName = "com.twitter.android",
                            webLink = "https://twitter.com/${uploaderDetails.twitter}"
                        )
                    }) {
                        Image(
                            painter = rememberImagePainter(R.drawable.twitter_icon),
                            contentDescription = "Instagram profile link"
                        )
                    }
            }
        }
    }
}

