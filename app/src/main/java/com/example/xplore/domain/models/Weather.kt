package com.example.xplore.domain.models

data class Weather(
    val temperature: Double,
    val humidity: Double,
    val pressure: Double,
    val windSpeed: Double,
    val windDirection: Double,
    val locationName: String? = null,
    val isSensorAvailable: Boolean
)
