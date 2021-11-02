package com.geekaid.collagenotes

import android.app.DownloadManager
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.navigation.compose.rememberNavController
import com.geekaid.collagenotes.components.BottomNav
import com.geekaid.collagenotes.navigation.Navigation
import com.geekaid.collagenotes.ui.theme.CollageNotesTheme
import com.geekaid.collagenotes.viewmodel.DashboardViewModel
import com.geekaid.collagenotes.viewmodel.FavouriteViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val dashboardViewModel: DashboardViewModel by viewModels()
        val favouriteViewModel: FavouriteViewModel by viewModels()

        setContent {
            CollageNotesTheme {
                val navController = rememberNavController()
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(
                        bottomBar = {
                            if (auth.currentUser != null && auth.currentUser!!.isEmailVerified) {
                                BottomNav(navController)
                            }
                        }
                    ) {
                        Navigation(navController, downloadManager, dashboardViewModel, favouriteViewModel)
                    }
                }
            }
        }
    }
}


