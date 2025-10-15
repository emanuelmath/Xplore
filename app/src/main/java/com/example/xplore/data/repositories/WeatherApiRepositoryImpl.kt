package com.example.xplore.data.repositories

import com.example.xplore.data.api.RetrofitBuilder
import com.example.xplore.domain.mappers.toModel
import com.example.xplore.domain.models.Weather

class WeatherApiRepositoryImpl : WeatherApiRepository {
    private val api = RetrofitBuilder.weatherApiService
    private val geocodingApi = RetrofitBuilder.geocodingApiService

    override suspend fun getWeatherData(lat: Double, lon: Double): Weather {
        val weatherResponse = api.getWeather(lat, lon)
        val geoResponse = geocodingApi.getReverseGeocoding(lat, lon)

        val locationName = geoResponse.results?.firstOrNull()?.let { "${it.name}, ${it.country}" }

        return weatherResponse.toModel(locationName)
    }
}
