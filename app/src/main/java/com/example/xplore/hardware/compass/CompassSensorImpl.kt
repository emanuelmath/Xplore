package com.example.xplore.hardware.compass

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class CompassSensorImpl(
    private val context: Context
) : SensorEventListener, CompassSensor {

    private val sensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private var accelerometerData: FloatArray? = null
    private var magnetometerData: FloatArray? = null

    private var onDirectionChanged: ((Float, Boolean) -> Unit)? = null
    private var sensorsAvailable: Boolean = false

    override fun startListening(onDirectionChanged: (Float, Boolean) -> Unit) {
        this.onDirectionChanged = onDirectionChanged

        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        sensorsAvailable = accelerometer != null && magnetometer != null

        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
        magnetometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        when (event.sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> accelerometerData = event.values.clone()
            Sensor.TYPE_MAGNETIC_FIELD -> magnetometerData = event.values.clone()
        }

        if (accelerometerData != null && magnetometerData != null) {
            val rotationMatrix = FloatArray(9)
            val orientationAngles = FloatArray(3)
            val success = SensorManager.getRotationMatrix(
                rotationMatrix, null, accelerometerData, magnetometerData
            )
            if (success) {
                SensorManager.getOrientation(rotationMatrix, orientationAngles)
                val azimuth = Math.toDegrees(orientationAngles[0].toDouble()).toFloat()
                val corrected = (azimuth + 360) % 360
                onDirectionChanged?.invoke(corrected, sensorsAvailable)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
