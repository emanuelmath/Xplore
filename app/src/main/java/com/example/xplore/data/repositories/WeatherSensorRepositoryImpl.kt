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
    /*override suspend fun getWeatherFromSensor(): Weather =
        suspendCancellableCoroutine { continuation ->
            var emitted = false
            weatherSensor.startListening { weather ->
                val available = weather.isSensorAvailable &&
                        weather.temperature != 0.0 &&
                        weather.pressure != 0.0 &&
                        weather.humidity != 0.0

                if (!emitted && available) {
                    emitted = true
                    continuation.resume(weather.copy(isSensorAvailable = true))
                    weatherSensor.stopListening()
                }
            }
        }*/
}

