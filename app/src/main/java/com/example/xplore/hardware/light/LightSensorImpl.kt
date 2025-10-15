package com.example.xplore.hardware.light

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class LightSensorImpl(private val context: Context): SensorEventListener, LightSensor {

    private var onLightChanged: ((Float, Boolean) -> Unit)? = null
    private var isSensorAvailable = false
    private val sensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private var lux: Float = -1f

    override fun startListening(onLightChanged: (Float, Boolean) -> Unit) {
        this.onLightChanged = onLightChanged

        val light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        val available = light != null
        isSensorAvailable = available

        onLightChanged.invoke(-1f, available)

        light?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun stopListening() {
        sensorManager.unregisterListener(this)
        onLightChanged?.invoke(-1f, false)
        onLightChanged = null
        lux = -1f
    }

    override fun onSensorChanged(event: SensorEvent) {
        if(event.sensor.type == Sensor.TYPE_LIGHT) {
            lux = event.values[0]
            onLightChanged?.invoke(lux, isSensorAvailable)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}