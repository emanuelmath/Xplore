package com.example.xplore.data.repositories

import com.example.xplore.data.api.WeatherApiService
import com.example.xplore.domain.models.Weather

class WeatherRepositoryImpl(
    private val weatherApiRepository: WeatherApiRepository,
    private val weatherSensorRepository: WeatherSensorRepository
) : WeatherRepository {
    override suspend fun getWeatherData(lat: Double?, lon: Double?): Weather {
        val sensorWeather = weatherSensorRepository.getWeatherFromSensor()

        return if (sensorWeather.isSensorAvailable) {
            sensorWeather
        } else {
            weatherApiRepository.getWeatherData(lat!!, lon!!)
        }
    }

}

/*return try {
            if(weatherSensorRepository.getWeatherFromSensor().isSensorAvailable) {
                weatherSensorRepository.getWeatherFromSensor()
            } else {
                if(lat != null && lon != null) {
                    weatherApiRepository.getWeatherData(lat, lon)
                } else {
                    Weather(
                        temperature = 0.0,
                        humidity = 0.0,
                        pressure = 0.0,
                        windSpeed = 0.0,
                        windDirection = 0.0,
                        locationName = "Ubicación desconocida",
                        isSensorAvailable = false
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Weather(
                temperature = 0.0,
                humidity = 0.0,
                pressure = 0.0,
                windSpeed = 0.0,
                windDirection = 0.0,
                locationName = "Sin datos",
                isSensorAvailable = false
            )
        }
    }
return try {
            val sensorWeather = weatherSensorRepository.getWeatherFromSensor()
            val useSensor = sensorWeather.isSensorAvailable &&
                    sensorWeather.temperature != 0.0 &&
                    sensorWeather.pressure != 0.0 &&
                    sensorWeather.humidity != 0.0

            if (useSensor) {
                sensorWeather.copy(isSensorAvailable = true)
            } else if (lat != null && lon != null) {
                weatherApiRepository.getWeatherData(lat, lon).copy(isSensorAvailable = false)
            } else {
                Weather(
                    temperature = 0.0,
                    humidity = 0.0,
                    pressure = 0.0,
                    windSpeed = 0.0,
                    windDirection = 0.0,
                    locationName = "Sin datos",
                    isSensorAvailable = false
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Weather(
                temperature = 0.0,
                humidity = 0.0,
                pressure = 0.0,
                windSpeed = 0.0,
                windDirection = 0.0,
                locationName = "Sin datos",
                isSensorAvailable = false
            )
        }
    }*/