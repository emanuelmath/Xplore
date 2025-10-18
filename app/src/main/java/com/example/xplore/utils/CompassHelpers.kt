package com.example.xplore.utils

fun getCardinalPointName(cardinalPoint: String): String {
    return when (cardinalPoint) {
        "N" -> "Norte"
        "NE" -> "Noreste"
        "E" -> "Este"
        "SE" -> "Sureste"
        "S" -> "Sur"
        "SO" -> "Suroeste"
        "O" -> "Oeste"
        "NO" -> "Noroeste"
        else -> "Norte"
    }
}