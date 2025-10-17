package com.example.xplore.utils

fun getWeatherDescription(code: Int?): String = when (code) {
    0 -> "☀️ Despejado"
    in 1..3 -> "🌤️ Parcialmente nublado"
    45, 48 -> "🌫️ Niebla"
    in 51..57 -> "🌦️ Llovizna"
    in 61..67 -> "🌧️ Lluvia"
    in 71..77 -> "❄️ Nieve"
    in 80..82 -> "⛈️ Chubascos"
    in 95..99 -> "🌩️ Tormenta eléctrica"
    else -> "🤷‍♂️ Desconocido"
}
