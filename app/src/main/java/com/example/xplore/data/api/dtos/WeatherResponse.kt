package com.example.xplore.data.api.dtos

data class WeatherResponse(
    val latitude: Double,
    val longitude: Double,
    val current: CurrentWeather
)