package com.example.jobmatchai.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.webkit.Profile
import com.example.jobmatchai.Screens.Add
import com.example.jobmatchai.Screens.BottomNav
import com.example.jobmatchai.Screens.CheckAts
import com.example.jobmatchai.Screens.Home
import com.example.jobmatchai.Screens.Login
import com.example.jobmatchai.Screens.Modification
import com.example.jobmatchai.Screens.Profile
import com.example.jobmatchai.Screens.Register
import com.example.jobmatchai.Screens.Splash

@Composable
fun Navgraph(navController: NavHostController) {

    NavHost(navController = navController,
        startDestination = Routes.Splash.routes) {

        composable(Routes.Splash.routes) {
            Splash(navController)
        }
        composable(Routes.Home.routes) {
            Home(navController)
        }
        composable(Routes.Profile.routes) {
            Profile(navController)
        }
        composable(Routes.Add.routes) {
            Add(navController)
        }
        composable(Routes.BottomNav.routes) {
            BottomNav(navController)
        }
        composable(Routes.Login.routes) {
            Login(navController)
        }
        composable(Routes.Register.routes) {
            Register(navController)
        }

        composable(Routes.CheckAts.routes) {
            CheckAts(navController)
        }

        composable(Routes.Modification.routes) {
            Modification(navController)
        }

    }

}

