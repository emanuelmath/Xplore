package com.example.xplore.hardware.light

interface LightSensor {
    fun startListening(onLightChanged: (Float, Boolean) -> Unit)
    fun stopListening()
}