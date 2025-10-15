package com.example.xplore.ui.viewmodels

import com.example.xplore.domain.models.Compass
import com.example.xplore.domain.models.Weather

data class MainUiState(
    val compass: Compass? = null,
    val weather: Weather? = null,
    val lon: Double? = null,
    val lat: Double? = null,
    val errorMessage: String = "",
    val normalMessage: String = ""
)