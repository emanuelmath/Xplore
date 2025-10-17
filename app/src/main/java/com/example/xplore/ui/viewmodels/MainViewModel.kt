package com.example.xplore.ui.viewmodels

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xplore.data.repositories.CompassRepository
import com.example.xplore.data.repositories.CompassRepositoryImpl
import com.example.xplore.data.repositories.LightRepository
import com.example.xplore.data.repositories.ProximityRepository
import com.example.xplore.data.repositories.WeatherApiRepository
import com.example.xplore.data.repositories.WeatherRepository
import com.example.xplore.data.repositories.WeatherRepositoryImpl
import com.example.xplore.data.repositories.WeatherSensorRepository
import com.example.xplore.domain.models.Weather
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainViewModel(
    private val compassRepository: CompassRepository,
    private val weatherRepository: WeatherRepository,
    private val weatherApiRepository: WeatherApiRepository,
    private val weatherSensorRepository: WeatherSensorRepository,
    private val proximityRepository: ProximityRepository,
    private val lightRepository: LightRepository
) : ViewModel() {

    var uiState by mutableStateOf(MainUiState())
        private set

    fun getAllSensors() {
        compassRepository.startCompass { compass ->
            uiState = uiState.copy(compass = compass)
        }
        proximityRepository.startProximity { prox ->
            uiState = uiState.copy(proximity = prox)
        }
        lightRepository.startLight { light ->
            uiState = uiState.copy(light = light)
        }

        viewModelScope.launch {
            uiState = uiState.copy(sensorWeather = weatherSensorRepository.getWeatherFromSensor())
        }
    }

    fun onLocationReceived(lat: Double, lon: Double) {
        uiState = uiState.copy(lat = lat, lon = lon)
        getWeatherAuto(lat, lon)
        getWeatherFromApi(lat, lon)

    }

    fun setNormalMessage(msg: String) {
        uiState = uiState.copy(normalMessage = msg, errorMessage = "")
    }

    fun setErrorMessage(msg: String) {
        uiState = uiState.copy(errorMessage = msg, normalMessage = "")
    }

    override fun onCleared() {
        super.onCleared()
        compassRepository.stopCompass()
        proximityRepository.stopProximity()
        lightRepository.stopLight()
    }

    fun getWeatherFromApi(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                val weather = weatherApiRepository.getWeatherData(lat, lon)
                uiState = uiState.copy(apiWeather = weather)
            } catch (e: Exception) {
                uiState = uiState.copy(errorMessage = "Error en la API: ${e.message}")
            }
        }
    }

    fun getWeatherFromSensor() {
        viewModelScope.launch {
            try {
                val weather = weatherSensorRepository.getWeatherFromSensor()
                uiState = uiState.copy(sensorWeather = weather)
            } catch (e: Exception) {
                uiState = uiState.copy(errorMessage = "Error en el sensor: ${e.message}")
            }
        }
    }

    fun getWeatherAuto(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                val weatherAuto = weatherRepository.getWeatherData(lat, lon)
                uiState = uiState.copy(weather = weatherAuto)
            } catch (e: Exception) {
                uiState = uiState.copy(errorMessage = "Error en el auto: ${e.message}")
            }
        }
    }

}
