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
    private var rotationVectorData: FloatArray? = null

    private var onDirectionChanged: ((Float, Boolean) -> Unit)? = null
    private var sensorsAvailable: Boolean = false

    private var usingRotationVector = false

    override fun startListening(onDirectionChanged: (Float, Boolean) -> Unit) {
        this.onDirectionChanged = onDirectionChanged

        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        val rotationVector = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

        usingRotationVector = rotationVector != null

        sensorsAvailable = if (usingRotationVector) {
            true
        } else {
            accelerometer != null && magnetometer != null
        }

        if (usingRotationVector) {
            sensorManager.registerListener(this, rotationVector, SensorManager.SENSOR_DELAY_UI)
        } else {
            accelerometer?.let {
                sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
            }
            magnetometer?.let {
                sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
            }
        }
    }

    override fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        val rotationMatrix = FloatArray(9)
        val orientationAngles = FloatArray(3)
        var azimuth = 0f

        when (event.sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> accelerometerData = event.values.clone()
            Sensor.TYPE_MAGNETIC_FIELD -> magnetometerData = event.values.clone()
            Sensor.TYPE_ROTATION_VECTOR -> rotationVectorData = event.values.clone()
        }

        val success = if (usingRotationVector && rotationVectorData != null) {
            SensorManager.getRotationMatrixFromVector(rotationMatrix, rotationVectorData)
            true
        } else if (accelerometerData != null && magnetometerData != null) {
            SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerData, magnetometerData)
        } else {
            false
        }

        if (success) {
            SensorManager.getOrientation(rotationMatrix, orientationAngles)
            azimuth = Math.toDegrees(orientationAngles[0].toDouble()).toFloat()
            azimuth = (azimuth + 360) % 360

            onDirectionChanged?.invoke(azimuth, sensorsAvailable)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
