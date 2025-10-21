package com.example.xplore.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.xplore.ui.navigation.Screen
import com.example.xplore.ui.screens.*
import com.example.xplore.ui.viewmodels.ConfigurationViewModel
import com.example.xplore.ui.viewmodels.MainViewModel
import com.example.xplore.ui.viewmodels.SplashViewModel

@Composable
fun XploreNavHost(navHostController: NavHostController,
                  startDestination: String,
                  mainViewModel: MainViewModel,
                  splashViewModel: SplashViewModel,
                  configurationViewModel: ConfigurationViewModel
                  //backgroundColor: Color = Color.Black
) {
    NavHost(navController = navHostController, startDestination = startDestination) {
        composable(Screen.Home.route) {
            Home2Screen(navHostController, mainViewModel)
            //HomeScreen(navHostController, mainViewModel)
        }
        composable(Screen.Settings.route) {
            ConfiguracionScreen(configurationViewModel)
        }
        composable (Screen.Tutorial.route) {
            //Luego agrego
        }
        composable(Screen.Splash.route) {
            PantallaInicialScreen(navHostController, splashViewModel, {
                navHostController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Splash.route){ inclusive = true }
                }
            })
            }
        }
    }