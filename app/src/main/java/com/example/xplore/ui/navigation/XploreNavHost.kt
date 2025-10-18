package com.example.xplore.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.xplore.ui.navigation.Screen
import com.example.xplore.ui.screens.*
import com.example.xplore.ui.viewmodels.MainViewModel

@Composable
fun XploreNavHost(navHostController: NavHostController, startDestination: String, mainViewModel: MainViewModel) {
    NavHost(navController = navHostController, startDestination = startDestination) {
        composable(Screen.Home.route) {
            Home2Screen(navHostController, mainViewModel)
        }
    }
}