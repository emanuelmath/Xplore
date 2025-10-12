package com.example.xplore.domain.models

data class Compass(
    var direction: Float = 0f,
    var cardinalPoint: String = "N", // N, S, E, y O
    var isSensorAvailable: Boolean = true
)