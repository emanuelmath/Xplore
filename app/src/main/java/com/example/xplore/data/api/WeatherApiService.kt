package com.example.xplore.data.api

import com.example.xplore.data.api.dtos.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("forecast")
    suspend fun getWeather(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double,
        @Query("current") current: String = "temperature_2m,relative_humidity_2m,surface_pressure,wind_speed_10m,wind_direction_10m"
    ): WeatherResponse
}