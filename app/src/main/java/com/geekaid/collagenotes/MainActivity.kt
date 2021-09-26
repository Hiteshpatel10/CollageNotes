package com.geekaid.collagenotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.navigation.compose.rememberNavController
import com.geekaid.collagenotes.components.BottomNav
import com.geekaid.collagenotes.navigation.Navigation
import com.geekaid.collagenotes.ui.theme.CollageNotesTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CollageNotesTheme {
                val navController = rememberNavController()
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(
                        bottomBar = {
                            if (auth.currentUser != null) {
                                BottomNav(navController)
                            }
                        }
                    ) {
                        Navigation(navController)
                    }
                }
            }
        }
    }
}


