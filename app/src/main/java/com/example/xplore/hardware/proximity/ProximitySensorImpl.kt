package com.example.xplore.hardware.proximity

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class ProximitySensorImpl(private val context: Context) : SensorEventListener, ProximitySensor {

    private val sensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private var isSensorAvailable: Boolean = false

    private var maximumRange: Float = 0f
    private var distance: Float = -1f
    private var onDistanceChanged: ((Float, Boolean) -> Unit)? = null

    override fun startListening(onDistanceChanged: (Float, Boolean) -> Unit) {
        this.onDistanceChanged = onDistanceChanged

        val proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        val available = proximity != null
        isSensorAvailable = available
        maximumRange = proximity?.maximumRange ?: 0f

        onDistanceChanged.invoke(-1f, available)

        proximity?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }


    override fun stopListening() {
        sensorManager.unregisterListener(this)
        onDistanceChanged = null
        distance = -1f
    }

    override fun onSensorChanged(event: SensorEvent) {
        if(event.sensor.type == Sensor.TYPE_PROXIMITY) {
            distance = event.values[0]
        }

        onDistanceChanged?.invoke(distance, isSensorAvailable)

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun getMaximumRange(): Float = maximumRange
}