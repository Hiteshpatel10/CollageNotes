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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CollageNotesTheme {
                val navController = rememberNavController()
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(
                        bottomBar = { BottomNav(navController) }
                    ) {
                        Navigation(navController)
                    }
                }
            }
        }
    }
}


