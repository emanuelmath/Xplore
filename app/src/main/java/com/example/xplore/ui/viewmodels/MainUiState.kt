package com.example.xplore.ui.viewmodels

import com.example.xplore.domain.models.Compass
import com.example.xplore.domain.models.Light
import com.example.xplore.domain.models.Proximity
import com.example.xplore.domain.models.Weather

data class MainUiState(
    val compass: Compass? = null,
    val weather: Weather? = null,
    val proximity: Proximity? = null,
    val light: Light? = null,
    val lon: Double? = null,
    val lat: Double? = null,
    val errorMessage: String = "",
    val normalMessage: String = "",
    val isLoading: Boolean = false,
    val optionLightDarkMode: Boolean? = true,
    val optionWeatherAPI: Boolean? = true,
    val sensorWeather: Weather? = null,
    val apiWeather: Weather? = null,
    val isManualDarkModeOn: Boolean? = false
    )