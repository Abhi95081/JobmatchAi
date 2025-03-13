package com.example.jobmatchai.Screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.jobmatchai.Navigation.Routes
import com.example.jobmatchai.R
import kotlinx.coroutines.delay

@Composable
fun Splash(navHostController: NavHostController) {
    var isVisible by remember { mutableStateOf(false) }
    var scale by remember { mutableStateOf(1f) }

    // Animation for scaling effect
    val scaleAnimation = animateFloatAsState(
        targetValue = scale,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "ScaleAnimation"
    )

    LaunchedEffect(Unit) {
        isVisible = true  // Trigger fade-in
        delay(1000)

        // Start scaling effect (pulsating)
        scale = 1.1f
        delay(1500)

        isVisible = false // Start fade-out
        delay(500)

        navHostController.navigate(Routes.Login.routes) {
            popUpTo(navHostController.graph.startDestinationId) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Optional padding
        verticalArrangement = Arrangement.Center, // Centers the image vertically
        horizontalAlignment = Alignment.CenterHorizontally // Centers horizontally
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(animationSpec = tween(1000)),
            exit = fadeOut(animationSpec = tween(500))
        ) {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = "Logo",
                modifier = Modifier.scale(scaleAnimation.value) // Apply scale animation
            )
        }
    }
}
