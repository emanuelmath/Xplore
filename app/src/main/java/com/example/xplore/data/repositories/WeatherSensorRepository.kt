package com.example.xplore.data.repositories

import com.example.xplore.domain.models.Weather

interface WeatherSensorRepository {
    suspend fun getWeatherFromSensor(): Weather
}
