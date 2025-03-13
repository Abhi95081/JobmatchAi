package com.example.jobmatchai.Screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.jobmatchai.Navigation.Routes
import com.example.jobmatchai.R
import kotlinx.coroutines.delay

@Composable
fun Splash(navHostController: NavHostController) {
    var isVisible by remember { mutableStateOf(false) }
    var scale by remember { mutableFloatStateOf(1f) }

    val scaleAnimation = animateFloatAsState(
        targetValue = scale,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "ScaleAnimation"
    )

    LaunchedEffect(Unit) {
        isVisible = true
        delay(1000)

        scale = 1.1f
        delay(1500)

        isVisible = false
        delay(500)

        navHostController.navigate(Routes.Login.routes) {
            popUpTo(navHostController.graph.startDestinationId) { inclusive = true }
            launchSingleTop = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD189D2)) // Set Purple Background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn(animationSpec = tween(1000)),
                exit = fadeOut(animationSpec = tween(500))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.background),
                    contentDescription = "Logo",
                    modifier = Modifier.scale(scaleAnimation.value)
                )
            }
        }
    }
}
