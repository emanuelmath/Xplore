package com.example.xplore.hardware.proximity

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class ProximitySensorImpl(private val context: Context) : SensorEventListener, ProximitySensor {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val proximity: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
    private val isSensorAvailable = proximity != null
    private val maximumRange: Float = proximity?.maximumRange ?: 0f
    private var currentDistance: Float = -1f
    private var onDistanceChanged: ((Float, Boolean) -> Unit)? = null

    override fun startListening(onDistanceChanged: (Float, Boolean) -> Unit) {
        this.onDistanceChanged = onDistanceChanged

        onDistanceChanged.invoke(currentDistance, isSensorAvailable)

        proximity?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }


    override fun stopListening() {
        sensorManager.unregisterListener(this)
        onDistanceChanged = null
        currentDistance = -1f
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_PROXIMITY) {
            val newDistance = event.values[0]
            if (newDistance != currentDistance) {
                currentDistance = newDistance
                onDistanceChanged?.invoke(currentDistance, isSensorAvailable)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun getMaximumRange(): Float = maximumRange
}