package com.example.xplore.domain.models

data class Light(
    val lux: Float = 0f,
    val currentState: String = "Light Mode",
    val isSensorAvailable: Boolean = true
) {
}