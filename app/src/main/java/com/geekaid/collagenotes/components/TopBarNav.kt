package com.geekaid.collagenotes.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.geekaid.collagenotes.navigation.BottomNavScreen
import com.geekaid.collagenotes.util.Constants
import com.geekaid.collagenotes.util.getTitle
import com.geekaid.collagenotes.viewmodel.DashboardViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalFoundationApi
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
                IconButton(onClick = {
                    notesType = !notesType
                }) {
                    Icon(Icons.Filled.FilterList, contentDescription = "more")
                }

                if (navBackStackEntry?.destination?.route != BottomNavScreen.FavouriteScreenNav.route)
                    IconButton(onClick = { notesOrderBy = !notesOrderBy }) {
                        Icon(Icons.Filled.SortByAlpha, contentDescription = "more")
                    }
            }


            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(Icons.Filled.MoreVert, contentDescription = "more")
            }

            DropdownMenu(expanded = notesType, onDismissRequest = { notesType = false }) {

                Constants.filterBy.forEach { filterBy ->
                    DropdownMenuItem(onClick = {
                        dashboardViewModel.notesType.value = filterBy.value
                        notesType = false
                    }) {
                        Text(text = filterBy.string)
                    }
                }
            }

            DropdownMenu(expanded = notesOrderBy, onDismissRequest = { notesOrderBy = false }) {

                Constants.orderBy.forEach { filterBy ->
                    DropdownMenuItem(onClick = {
                        dashboardViewModel.orderBy.value = filterBy.value
                        notesOrderBy = false
                    }) {
                        Text(text = filterBy.string)
                    }
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
                alertBoxShow = signOutAlertDialog(isShow = alertBoxShow)

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

