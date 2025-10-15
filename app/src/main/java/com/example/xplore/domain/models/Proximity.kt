package com.example.xplore.domain.models

data class Proximity(
    val isNear: Boolean = false,
    val distance: Float? = null,
    val isSensorAvailable: Boolean = true
)