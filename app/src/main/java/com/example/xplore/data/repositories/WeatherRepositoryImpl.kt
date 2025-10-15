package com.example.xplore.data.repositories

import com.example.xplore.data.api.WeatherApiService
import com.example.xplore.domain.models.Weather

class WeatherRepositoryImpl(
    private val weatherApiRepository: WeatherApiRepository,
    private val weatherSensorRepository: WeatherSensorRepository
) : WeatherRepository {

    override suspend fun getWeatherData(lat: Double?, lon: Double?): Weather {
        val sensorWeather = weatherSensorRepository.getWeatherFromSensor()

        return if (sensorWeather.isSensorAvailable) {
            sensorWeather
        } else {
            weatherApiRepository.getWeatherData(lat!!, lon!!)
        }
    }
}
