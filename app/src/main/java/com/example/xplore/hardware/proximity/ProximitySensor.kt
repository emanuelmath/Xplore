package com.example.xplore.hardware.proximity

interface ProximitySensor {
    fun startListening(onDistanceChanged: (Float, Boolean) -> Unit)
    fun stopListening()
    fun getMaximumRange(): Float
}