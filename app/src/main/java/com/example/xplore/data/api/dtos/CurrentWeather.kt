package com.example.xplore.data.api.dtos

data class CurrentWeather(
    val temperature_2m: Double?,
    val relative_humidity_2m: Double?,
    val surface_pressure: Double?,
    val wind_direction_10m: Double?,
    val wind_speed_10m: Double?,
    val weather_code: Int?
)