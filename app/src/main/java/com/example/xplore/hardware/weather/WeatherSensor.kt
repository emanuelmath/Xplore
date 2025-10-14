package com.example.xplore.hardware.weather

import com.example.xplore.domain.models.Weather

interface WeatherSensor {
    fun startListening(onWeatherChanged: (Weather) -> Unit)
    fun stopListening()
}