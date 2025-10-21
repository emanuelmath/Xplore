package com.example.xplore.data.repositories

import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun LightRepository.awaitLightSensorAvailable(): Boolean {
    delay(300)
    return suspendCancellableCoroutine { cont ->
        var resumed = false
        startLight { light ->
            if (!resumed) {
                resumed = true
                cont.resume(light.isSensorAvailable)
            }
        }
        cont.invokeOnCancellation { stopLight() }
    }
}

suspend fun WeatherSensorRepository.awaitWeatherSensorAvailable(): Boolean = getWeatherFromSensor().isSensorAvailable