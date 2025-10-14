package com.example.xplore.data.repositories

import com.example.xplore.domain.models.Weather

interface WeatherApiRepository {
    suspend fun getWeatherData(lat: Double, lon: Double): Weather
}