package com.example.jobmatchai.Screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jobmatchai.Navigation.Routes
import com.example.jobmatchai.model.BottomNavItem

@Composable
fun BottomNav(navController: NavHostController) {
    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = { MyBottomBar(navController = bottomNavController) },
        containerColor = Color(0xFFF8F9FA) // Light background for contrast
    ) { innerPadding ->

        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = bottomNavController,
                startDestination = Routes.Home
            ) {
                composable(Routes.Home) {
                    AnimatedScreen { Home(navController) }
                }
                composable(Routes.Profile) {
                    AnimatedScreen { Profile(navController) }
                }
                composable(Routes.Add) {
                    AnimatedScreen { Add(navController) }
                }
            }
        }
    }
}

@Composable
fun MyBottomBar(navController: NavHostController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val items = listOf(
        BottomNavItem("Home", Routes.Home, Icons.Rounded.Home),
        BottomNavItem("Add", Routes.Add, Icons.Rounded.Add),
        BottomNavItem("Profile", Routes.Profile, Icons.Rounded.Person)
    )

    BottomAppBar(
        containerColor = Color(0xFF2196F3), // Modern blue color
        tonalElevation = 8.dp, // Subtle elevation effect
        contentColor = Color.White
    ) {
        items.forEach { item ->
            val isSelected = item.route == currentRoute

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = if (isSelected) Color.White else Color(0xFFBBDEFB) // Light tint when unselected
                    )
                },
                label = { Text(item.title) }
            )
        }
    }
}

/**
 * Adds a smooth fade-in animation when navigating between screens.
 */
@Composable
fun AnimatedScreen(content: @Composable () -> Unit) {
    AnimatedVisibility(
        visible = true,
        enter = fadeIn() + slideInHorizontally(initialOffsetX = { it / 2 }),
        exit = fadeOut() + slideOutHorizontally(targetOffsetX = { it / 2 })
    ) {
        content()
    }
}
