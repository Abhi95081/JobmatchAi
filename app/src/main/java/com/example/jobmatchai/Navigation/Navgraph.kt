package com.example.jobmatchai.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.jobmatchai.Screens.*

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.Splash) {
        composable(Routes.Splash) { Splash(navController) }
        composable(Routes.Home) { Home(navController) }
        composable(Routes.Profile) { Profile(navController) }
        composable(Routes.Add) { Add(navController) }
        composable(Routes.BottomNav) { BottomNav(navController) }
        composable(Routes.Login) { Login(navController) }
        composable(Routes.Register) { Register(navController) }
        composable(Routes.CoverLetter) { CoverLetter(navController) }
        composable(Routes.Modification) { Modification(navController) }
    }
}
