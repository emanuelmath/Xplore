package com.example.xplore.data.repositories

import com.example.xplore.domain.models.Weather

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, lon: Double): Weather
}