package com.example.xplore.data.repositories

import android.util.Log
import com.example.xplore.data.api.RetrofitBuilder
import com.example.xplore.domain.mappers.toModel
import com.example.xplore.domain.models.Weather

class WeatherApiRepositoryImpl : WeatherApiRepository {

    private val api = RetrofitBuilder.weatherApiService
    private val geocodingApi = RetrofitBuilder.geocodingApiService

    override suspend fun getWeatherData(lat: Double, lon: Double): Weather {
        val weatherResponse = api.getWeather(lat, lon)
        val locationResponse = try {
            geocodingApi.getReverseGeocoding(lat, lon)
        } catch (e: Exception) {
            null
        }

        val locationName = locationResponse?.address?.let{
            "${it.state}, ${it.country}"
        }
        ?: "Ubicación desconocida"

        return weatherResponse.toModel(locationName)
    }
}

