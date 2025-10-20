package com.example.xplore.domain.models

data class UserPreferences(
    val userName: String? = "Usuario",
    val optionLightDarkMode: Boolean = true,
    val optionWeatherAPI: Boolean = true
)