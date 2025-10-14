package com.example.xplore.data.repositories

import android.content.Context
import com.example.xplore.domain.models.Weather
import com.example.xplore.hardware.weather.WeatherSensor
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class WeatherSensorRepositoryImpl(
    private val weatherSensor: WeatherSensor
) : WeatherSensorRepository {

    override suspend fun getWeatherFromSensor(): Weather =
        suspendCancellableCoroutine { continuation ->

            var hasReturned = false

            weatherSensor.startListening { weather ->
                if (!hasReturned) {
                    hasReturned = true
                    continuation.resume(weather)
                    weatherSensor.stopListening()
                }
            }
        }
}

