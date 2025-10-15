package com.example.xplore.data.repositories

import com.example.xplore.domain.models.Light

interface LightRepository {
    fun startLight(onUpdate: (Light) -> Unit)
    fun stopLight()
}