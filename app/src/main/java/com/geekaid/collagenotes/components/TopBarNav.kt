package com.geekaid.collagenotes.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.geekaid.collagenotes.navigation.BottomNavScreen
import com.geekaid.collagenotes.navigation.Screens
import com.geekaid.collagenotes.util.getTitle
import com.geekaid.collagenotes.viewmodel.DashboardViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@Composable
fun TopBarNav(dashboardViewModel: DashboardViewModel, navController: NavController) {

    val auth = Firebase.auth
    var showMenu by remember { mutableStateOf(false) }
    var alertBoxShow by remember { mutableStateOf(false) }
    var notesType by remember { mutableStateOf(false) }
    var notesOrderBy by remember { mutableStateOf(false) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    TopAppBar(
        title = { Text(getTitle(navBackStackEntry?.destination?.route.toString())) },
        actions = {

            if (topBarDropdownVisibility(navController = navController))
                IconButton(onClick = { notesType = !notesType }) {
                    if (notesType)
                        Icon(Icons.Filled.ArrowDropUp, contentDescription = "more")
                    else
                        Icon(Icons.Filled.ArrowDropDown, contentDescription = "more")
                }

            IconButton(onClick = { notesOrderBy = !notesOrderBy }) {
                if (notesOrderBy)
                    Icon(Icons.Filled.ArrowDropUp, contentDescription = "more")
                else
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = "more")
            }


            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(Icons.Filled.MoreVert, contentDescription = "more")
            }

            DropdownMenu(expanded = notesType, onDismissRequest = { notesType = false }) {

                DropdownMenuItem(onClick = {
                    dashboardViewModel.notesType.value = "notes"
                    notesType = false
                }) {
                    Text(text = "notes")
                }

                DropdownMenuItem(onClick = {
                    dashboardViewModel.notesType.value = "papers"
                    notesType = false
                }) {
                    Text(text = "papers")
                }

                DropdownMenuItem(onClick = {
                    dashboardViewModel.notesType.value = "assignments"
                    notesType = false
                }) {
                    Text(text = "assignment")
                }
            }

            DropdownMenu(expanded = notesOrderBy, onDismissRequest = { notesOrderBy = false }) {

                DropdownMenuItem(onClick = {
                    dashboardViewModel.orderBy.value = "date"
                    notesOrderBy = false
                }) {
                    Text(text = "date")
                }

                DropdownMenuItem(onClick = {
                    dashboardViewModel.orderBy.value = "likes"
                    notesOrderBy = false
                }) {
                    Text(text = "likes")
                }

                DropdownMenuItem(onClick = {
                    dashboardViewModel.orderBy.value = "downloadedTimes"
                    notesOrderBy = false
                }) {
                    Text(text = "downloadedTimes")
                }
            }

            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false },
            ) {

                DropdownMenuItem(
                    onClick = {
                        navController.navigate("${BottomNavScreen.UserProfileScreenNav.route}/${auth.currentUser?.email.toString()}")
                        showMenu = false
                    }) {
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


@Composable
fun topBarDropdownVisibility(navController: NavController): Boolean {

    var isTopBarDropdownVisible by remember { mutableStateOf(false) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    isTopBarDropdownVisible = when (navBackStackEntry?.destination?.route) {
        Screens.DashboardNav.route -> true
        Screens.FavouriteScreenNav.route -> true
        else -> false
    }

    return isTopBarDropdownVisible
}

