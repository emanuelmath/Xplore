package com.example.xplore.ui.viewmodels

data class ConfigurationUiState(
    val userName: String? = "Usuario",
    val optionLightDarkMode: Boolean? = true,
    val optionWeatherAPI: Boolean? = true,
    val errorMessage: String? = null,
    val isLightSensorAvailable: Boolean? = null,
    val isWeatherSensorAvailable: Boolean? = null,
)
