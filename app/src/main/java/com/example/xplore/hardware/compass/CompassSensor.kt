package com.example.xplore.hardware.compass

interface CompassSensor {
    fun startListening(onDirectionChanged: (Float, Boolean) -> Unit)
    fun stopListening()
}