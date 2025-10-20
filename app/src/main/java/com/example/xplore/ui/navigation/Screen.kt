package com.example.xplore.ui.navigation

import com.example.xplore.R

sealed class Screen(val route: String, val title: String = "",
                    val iconLight: Int = R.drawable.ic_launcher_foreground,
                    val iconDark: Int = R.drawable.ic_launcher_foreground) {
    object Home: Screen("home", "Home", R.drawable.homebuttonlight, R.drawable.homebuttondark)
    object Settings: Screen("settings", "Configuración", R.drawable.settingsbuttonlight, R.drawable.settingsbuttondark)
    object Tutorial: Screen("tutorial", "Tutorial", R.drawable.tutorialbuttonlight, R.drawable.tutorialbuttondark)
    object Splash: Screen("splash")
}