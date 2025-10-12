package com.example.xplore.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.xplore.ui.navigation.Screen
import com.example.xplore.ui.screens.HomeScreen

@Composable
fun XploreNavHost(navHostController: NavHostController, startDestination: String) {
    NavHost(navController = navHostController, startDestination = startDestination) {
        composable(Screen.Home.route) {
            HomeScreen()
        }
    }
}