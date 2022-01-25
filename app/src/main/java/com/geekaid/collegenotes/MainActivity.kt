package com.geekaid.collegenotes

import android.app.DownloadManager
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.geekaid.collegenotes.components.*
import com.geekaid.collegenotes.navigation.BottomNavScreen
import com.geekaid.collegenotes.navigation.Navigation
import com.geekaid.collegenotes.navigation.Screens
import com.geekaid.collegenotes.ui.theme.CollageNotesTheme
import com.geekaid.collegenotes.viewmodel.DashboardViewModel
import com.google.android.gms.ads.MobileAds
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.lang.RuntimeException
import java.text.DateFormat
import java.util.*

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val dashboardViewModel: DashboardViewModel by viewModels()

        MobileAds.initialize(this)

        loadInterstitialDownload(
            context = this,
            adUnitId = getString(R.string.ad_id_download_interstitial),
            dashboardViewModel = dashboardViewModel
        )

        loadInterstitialSubmit(
            context = this,
            adUnitId = getString(R.string.ad_id_submit_interstitial),
            dashboardViewModel = dashboardViewModel
        )



        setContent {
            CollageNotesTheme {
                val navController = rememberNavController()

                Surface {
                    Scaffold(
                        topBar = {
                            if (bottomNavVisibility(navController = navController)) {
                                TopBarNav(
                                    dashboardViewModel = dashboardViewModel,
                                    navController = navController
                                )
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
        BottomNavScreen.DashboardNav.route -> isBottomNavVisible = true
    }
    return isBottomNavVisible
}

