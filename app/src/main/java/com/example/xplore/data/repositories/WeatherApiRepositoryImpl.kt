package com.example.xplore.data.repositories

import com.example.xplore.data.api.RetrofitBuilder
import com.example.xplore.domain.mappers.toModel
import com.example.xplore.domain.models.Weather

class WeatherApiRepositoryImpl : WeatherApiRepository {
    private val api = RetrofitBuilder.weatherApiService

    override suspend fun getWeatherData(lat: Double, lon: Double): Weather {
        return api.getWeather(lat, lon).toModel()
    }
}
