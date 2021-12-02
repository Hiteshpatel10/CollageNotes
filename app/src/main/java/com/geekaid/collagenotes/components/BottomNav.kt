package com.geekaid.collagenotes.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.geekaid.collagenotes.util.Constants
import com.geekaid.collagenotes.navigation.BottomNavScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun BottomNav(navController: NavHostController) {

    var alertBoxShow by remember { mutableStateOf(false) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation {
        Constants.screen.forEach {
            AddItem(
                screen = it,
                currentDestination = currentDestination,
                navController = navController
            )
        }

        BottomNavigationItem(

            label = {
                Text(text = BottomNavScreen.SignOutScreenNav.title)
            },

            icon = {
                Icon(
                    imageVector = BottomNavScreen.SignOutScreenNav.icon,
                    contentDescription = BottomNavScreen.SignOutScreenNav.title
                )
            },

            selected = alertBoxShow,

            unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),

            onClick = {
                alertBoxShow = true
            }
        )
    }

    if (alertBoxShow)
        alertBoxShow = signOutAlertDialog(isShow = alertBoxShow)
}

@Composable
fun RowScope.AddItem(
    screen: BottomNavScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {

    BottomNavigationItem(

        label = {
            Text(text = screen.title)
        },

        icon = {
            Icon(imageVector = screen.icon, contentDescription = screen.title)
        },

        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,

        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),

        onClick = {
            navController.navigate(screen.route) {
                popUpTo(BottomNavScreen.DashboardNav.route)
                launchSingleTop = true
            }
        }
    )
}