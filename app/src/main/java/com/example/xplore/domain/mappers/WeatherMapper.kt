package com.example.xplore.domain.mappers

import com.example.xplore.data.api.dtos.WeatherResponse
import com.example.xplore.domain.models.Weather

fun WeatherResponse.toModel(locationName: String? = null): Weather {
    val current = this.current
    return Weather(
        temperature = current.temperature_2m ?: 0.0,
        humidity = current.relative_humidity_2m ?: 0.0,
        pressure = current.surface_pressure ?: 0.0,
        windSpeed = current.wind_speed_10m ?: 0.0,
        windDirection = current.wind_direction_10m ?: 0.0,
        locationName = locationName,
        isSensorAvailable = true
    )
}