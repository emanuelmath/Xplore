package com.example.xplore.data.repositories

import com.example.xplore.domain.models.Light
import com.example.xplore.hardware.light.LightSensor

class LightRepositoryImpl(private val lightSensor: LightSensor): LightRepository {
    override fun startLight(onUpdate: (Light) -> Unit) {
        lightSensor.startListening {
            lux, isSensorAvailable ->
            val currentState = getLightMode(lux)
            onUpdate(
                Light(
                    lux = lux,
                    currentState = currentState,
                    isSensorAvailable = isSensorAvailable
                )
            )
        }
    }

    override fun stopLight() {
        lightSensor.stopListening()
    }

    fun getLightMode(lux: Float): String {
        if(lux < 120f) {
            return "Dark Mode"
        }
        return "Light Mode"
    }
}