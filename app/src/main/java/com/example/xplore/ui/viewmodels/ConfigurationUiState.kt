package com.example.xplore.ui.viewmodels

import com.example.xplore.domain.models.Light
import com.example.xplore.domain.models.Weather

data class ConfigurationUiState(
    val userName: String? = "Usuario",
    val optionLightDarkMode: Boolean? = false,
    val optionWeatherAPI: Boolean? = true,
    val errorMessage: String? = null,
    val light: Light? = null,
    val weather: Weather? = null
)
