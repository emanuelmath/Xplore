package com.example.xplore.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.xplore.data.repositories.CompassRepository
import com.example.xplore.data.repositories.CompassRepositoryImpl
import com.example.xplore.data.repositories.LightRepository
import com.example.xplore.data.repositories.ProximityRepository
import com.example.xplore.data.repositories.WeatherRepository
import com.example.xplore.data.repositories.WeatherRepositoryImpl

class MainViewModel(
    private val compassRepository: CompassRepository,
    private val weatherRepository: WeatherRepository,
    private val proximityRepository: ProximityRepository,
    private val lightRepository: LightRepository
) : ViewModel() {

    var uiState by mutableStateOf(MainUiState())
        private set

    suspend fun getAllSensors() {
        compassRepository.startCompass {
            compass ->
                uiState = uiState.copy(compass = compass)
            Log.d("MainViewModel", "Pasando brújula: $compass")
        }

        uiState = if(uiState.lat != null && uiState.lon != null) {
            uiState.copy(weather = weatherRepository.getWeatherData(uiState.lat!!, uiState.lon!!))
        } else {
            uiState.copy(weather = weatherRepository.getWeatherData())
        }

        proximityRepository.startProximity {
            proximity ->
            uiState = uiState.copy(proximity = proximity)
        }

        lightRepository.startLight {
            light ->
            uiState = uiState.copy(light = light)
        }
    }

    /*suspend*/ fun sendLocationData(lat: Double, lon: Double) {
        uiState = uiState.copy(
            lat = lat,
            lon = lon
        )
        //getAllSensors()
    }


    override fun onCleared() {
        super.onCleared()
        compassRepository.stopCompass()
        proximityRepository.stopProximity()
        lightRepository.stopLight()
    }

}