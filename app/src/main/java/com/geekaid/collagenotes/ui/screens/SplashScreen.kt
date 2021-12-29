package com.geekaid.collagenotes.ui.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notes
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.geekaid.collagenotes.navigation.BottomNavScreen
import com.geekaid.collagenotes.navigation.Screens
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun SplashScreen(navController: NavController) {

    val scale = remember { Animatable(initialValue = 0f) }
    var nextScreen by remember { mutableStateOf("") }
    val currentUser = Firebase.auth.currentUser

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 4f,
            animationSpec = tween(
                durationMillis = 600,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )

        when {
            currentUser == null -> nextScreen = Screens.SignInNav.route

            !currentUser.isEmailVerified -> nextScreen = Screens.EmailVerificationNav.route

            currentUser.isEmailVerified -> nextScreen = BottomNavScreen.DashboardNav.route
        }

        navController.navigate(nextScreen) {
            navController.popBackStack()
        }
    }

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Icon(
            Icons.Filled.Notes,
            contentDescription = "Splash Icon",
            tint = Color.Red,
            modifier = Modifier.scale(scale.value)
        )
    }
}