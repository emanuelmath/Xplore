package com.example.xplore.data.repositories

import android.hardware.GeomagneticField
import com.example.xplore.domain.models.Compass
import com.example.xplore.hardware.compass.CompassSensor

class CompassRepositoryImpl(
    private val compassSensor: CompassSensor
) : CompassRepository {

    private var lastLat: Double? = null
    private var lastLon: Double? = null
    private var lastAlt: Double = 0.0

    override fun startCompass(onUpdate: (Compass) -> Unit) {
        compassSensor.startListening { degrees, sensorsAvailable ->
            val correctedDegrees = if (lastLat != null && lastLon != null) {
                val geoField = GeomagneticField(
                    lastLat!!.toFloat(),
                    lastLon!!.toFloat(),
                    lastAlt.toFloat(),
                    System.currentTimeMillis()
                )
                (degrees - geoField.declination + 360) % 360
            } else {
                degrees
            }

            val cardinal = getCardinalPoint(correctedDegrees)
            onUpdate(
                Compass(
                    direction = correctedDegrees,
                    cardinalPoint = cardinal,
                    isSensorAvailable = sensorsAvailable
                )
            )
        }
    }

    override fun updateLocation(lat: Double, lon: Double, alt: Double) {
        lastLat = lat
        lastLon = lon
        lastAlt = alt
    }

    override fun stopCompass() = compassSensor.stopListening()

    private fun getCardinalPoint(degrees: Float): String {
        return when (degrees) {
            in 337.5..360.0, in 0.0..22.5 -> "N"
            in 22.5..67.5 -> "NE"
            in 67.5..112.5 -> "E"
            in 112.5..157.5 -> "SE"
            in 157.5..202.5 -> "S"
            in 202.5..247.5 -> "SO"
            in 247.5..292.5 -> "O"
            in 292.5..337.5 -> "NO"
            else -> "N"
        }
    }
}
