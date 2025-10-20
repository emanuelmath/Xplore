package com.example.xplore.data.repositories

import com.example.xplore.domain.models.Proximity
import com.example.xplore.hardware.proximity.ProximitySensor

class ProximityRepositoryImpl(private val proximitySensor: ProximitySensor) : ProximityRepository {
    override fun startProximity(onUpdate: (Proximity) -> Unit) {
        proximitySensor.startListening { distance, isSensorAvailable ->
            val isNear = if (isSensorAvailable) {
                distance < proximitySensor.getMaximumRange()
            } else {
                false
            }
            onUpdate(
                Proximity(
                    isNear = isNear,
                    distance = distance,
                    isSensorAvailable = isSensorAvailable
                )
            )
        }
    }

    override fun stopProximity() {
        proximitySensor.stopListening()
    }
}