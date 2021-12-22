package com.geekaid.collagenotes.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.geekaid.collagenotes.navigation.BottomNavScreen
import com.geekaid.collagenotes.util.getTitle
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@Composable
fun TopBarNav(navController: NavController) {

    val auth = Firebase.auth
    var showMenu by remember { mutableStateOf(false) }
    var alertBoxShow by remember { mutableStateOf(false) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    TopAppBar(
        title = { Text(getTitle(navBackStackEntry?.destination?.route.toString())) },
        actions = {
            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(Icons.Filled.MoreVert, contentDescription = "more")
            }

            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false },
            ) {

                DropdownMenuItem(onClick = { navController.navigate("${BottomNavScreen.UserProfileScreenNav.route}/${auth.currentUser?.email.toString()}") }) {
                    Row {
                        Icon(Icons.Filled.Face, contentDescription = "profile")
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(text = "Profile")
                    }
                }

                DropdownMenuItem(onClick = {
                    alertBoxShow = true
                    showMenu = false
                }) {
                    Row {
                        Icon(Icons.Filled.Logout, contentDescription = "profile")
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(text = "Sign Out")
                    }
                }
            }

            if (alertBoxShow)
                alertBoxShow = signOutAlertDialog(isShow = alertBoxShow)
        }
    )
}