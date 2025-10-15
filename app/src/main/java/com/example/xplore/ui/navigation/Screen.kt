package com.example.xplore.ui.navigation

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object HomePreview: Screen("homepreview")
}