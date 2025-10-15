package com.example.xplore.data.repositories

import com.example.xplore.domain.models.Proximity

interface ProximityRepository {
    fun startProximity(onUpdate: (Proximity) -> Unit)
    fun stopProximity()
}