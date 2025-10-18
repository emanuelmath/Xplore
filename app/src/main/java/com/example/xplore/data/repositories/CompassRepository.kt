package com.example.xplore.data.repositories

import com.example.xplore.domain.models.Compass

interface CompassRepository {
    fun startCompass(onUpdate: (Compass) -> Unit)
    fun stopCompass()
    fun updateLocation(lat: Double, lon: Double, alt: Double = 0.0)
}
