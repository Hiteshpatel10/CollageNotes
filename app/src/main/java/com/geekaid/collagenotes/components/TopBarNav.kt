package com.geekaid.collagenotes.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.geekaid.collagenotes.navigation.BottomNavScreen
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

            if (topBarDropdownVisibility(navController = navController)) {
                IconButton(onClick = { notesType = !notesType }) {
                    Icon(Icons.Filled.FilterList, contentDescription = "more")
                }

                IconButton(onClick = { notesOrderBy = !notesOrderBy }) {
                    Icon(Icons.Filled.SortByAlpha, contentDescription = "more")
                }
            }


            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(Icons.Filled.MoreVert, contentDescription = "more")
            }

            DropdownMenu(expanded = notesType, onDismissRequest = { notesType = false }) {

                DropdownMenuItem(onClick = {
                    dashboardViewModel.notesType.value = "notes"
                    notesType = false
                }) {
                    Text(text = "Notes")
                }

                DropdownMenuItem(onClick = {
                    dashboardViewModel.notesType.value = "papers"
                    notesType = false
                }) {
                    Text(text = "Papers")
                }

                DropdownMenuItem(onClick = {
                    dashboardViewModel.notesType.value = "assignments"
                    notesType = false
                }) {
                    Text(text = "Assignment")
                }
            }

            DropdownMenu(expanded = notesOrderBy, onDismissRequest = { notesOrderBy = false }) {

                DropdownMenuItem(onClick = {
                    dashboardViewModel.orderBy.value = "date"
                    notesOrderBy = false
                }) {
                    Text(text = "Date")
                }

                DropdownMenuItem(onClick = {
                    dashboardViewModel.orderBy.value = "likes"
                    notesOrderBy = false
                }) {
                    Text(text = "Likes")
                }

                DropdownMenuItem(onClick = {
                    dashboardViewModel.orderBy.value = "Downloaded Times"
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
                    TextIcon(
                        textString = "Profile",
                        imageVector = Icons.Filled.Face,
                        iconBefore = true
                    )
                }

                DropdownMenuItem(onClick = {
                    alertBoxShow = true
                    showMenu = false
                }) {
                    TextIcon(
                        textString = "Sign Out",
                        imageVector = Icons.Filled.Logout,
                        iconBefore = true
                    )
                }
            }

            if (alertBoxShow)
                signOutAlertDialog(isShow = alertBoxShow)

        }
    )
}


@Composable
fun topBarDropdownVisibility(navController: NavController): Boolean {

    var isTopBarDropdownVisible by remember { mutableStateOf(false) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    isTopBarDropdownVisible = when (navBackStackEntry?.destination?.route) {
        BottomNavScreen.DashboardNav.route -> true
        BottomNavScreen.FavouriteScreenNav.route -> true
        else -> false
    }

    return isTopBarDropdownVisible
}

