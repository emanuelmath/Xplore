package com.example.xplore.utils

import com.example.xplore.R
fun getWeatherDescription(code: Int?): String = when (code) {
    0 -> "Despejado"
    in 1..3 -> "Parcialmente nublado"
    45, 48 -> "Niebla"
    in 51..57 -> "Llovizna"
    in 61..67 -> "Lluvia"
    in 71..77 -> "Nieve"
    in 80..82 -> "Chubascos"
    in 95..99 -> "Tormenta eléctrica"
    else -> "Desconocido"
}

fun getWeatherDescriptionImage(code: Int?): Int = when (code) {
    0 -> R.drawable.despejado
    in 1..3 -> R.drawable.parcialmente_nublado
    45, 48 -> R.drawable.niebla
    in 51..57 -> R.drawable.llovizna
    in 61..67 -> R.drawable.lluvia
    in 71..77 -> R.drawable.nieve
    in 80..82 -> R.drawable.chubascos
    in 95..99 -> R.drawable.tormenta_electrica
    else -> R.drawable.despejado
}
