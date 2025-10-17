package com.example.xplore.hardware.weather

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.example.xplore.domain.models.Weather

class WeatherSensorImpl(private val context: Context) : WeatherSensor, SensorEventListener {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private var temperature: Double? = null
    private var humidity: Double? = null
    private var pressure: Double? = null
    private var sensorsAvailable = false
    private var onWeatherChanged: ((Weather) -> Unit)? = null

    override fun startListening(onWeatherChanged: (Weather) -> Unit) {
        this.onWeatherChanged = onWeatherChanged

        val tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        val humiditySensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)
        val pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)

        sensorsAvailable = tempSensor != null && humiditySensor != null && pressureSensor != null

        tempSensor?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI) }
        humiditySensor?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI) }
        pressureSensor?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI) }
    }

    override fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        when (event.sensor.type) {
            Sensor.TYPE_AMBIENT_TEMPERATURE -> temperature = event.values[0].toDouble()
            Sensor.TYPE_RELATIVE_HUMIDITY -> humidity = event.values[0].toDouble()
            Sensor.TYPE_PRESSURE -> pressure = event.values[0].toDouble()
        }

        onWeatherChanged?.invoke(
            Weather(
                temperature = temperature ?: 0.0,
                humidity = humidity ?: 0.0,
                pressure = pressure ?: 0.0,
                windSpeed = 0.0,
                windDirection = 0.0,
                locationName = "Sensor local",
                isSensorAvailable = sensorsAvailable
            )
        )
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
