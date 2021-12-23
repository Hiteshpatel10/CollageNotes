package com.geekaid.collagenotes

import android.app.DownloadManager
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.geekaid.collagenotes.components.BottomNav
import com.geekaid.collagenotes.components.TopBarNav
import com.geekaid.collagenotes.navigation.Navigation
import com.geekaid.collagenotes.navigation.Screens
import com.geekaid.collagenotes.ui.theme.CollageNotesTheme
import com.geekaid.collagenotes.viewmodel.DashboardViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val dashboardViewModel: DashboardViewModel by viewModels()

        setContent {
            CollageNotesTheme {
                val navController = rememberNavController()

                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(
                        topBar = {
                            if (bottomNavVisibility(navController = navController)) {
                                TopBarNav(dashboardViewModel = dashboardViewModel, navController = navController)
                            }
                        },
                        bottomBar = {
                            if (bottomNavVisibility(navController = navController)) {
                                BottomNav(navController)
                            }
                        }
                    ) {
                        Navigation(
                            navController = navController,
                            downloadManager = downloadManager,
                            dashboardViewModel = dashboardViewModel,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun bottomNavVisibility(navController: NavController): Boolean {

    var isBottomNavVisible by remember { mutableStateOf(false) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    when (navBackStackEntry?.destination?.route) {
        Screens.SplashNav.route -> isBottomNavVisible = false
        Screens.DashboardNav.route -> isBottomNavVisible = true
    }
    return isBottomNavVisible
}

