package com.example.xplore.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.xplore.data.repositories.CompassRepository
import com.example.xplore.data.repositories.CompassRepositoryImpl
import com.example.xplore.data.repositories.WeatherRepository
import com.example.xplore.data.repositories.WeatherRepositoryImpl

class MainViewModel(
    private val compassRepository: CompassRepository,
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    var uiState by mutableStateOf(MainUiState())
        private set

    suspend fun getAllSensors() {
        compassRepository.startCompass {
            compass ->
                uiState = uiState.copy(compass = compass)
            Log.d("MainViewModel", "Pasando brújula: $compass")
        }

        if(uiState.lat != null && uiState.lon != null) {
            uiState = uiState.copy(weather = weatherRepository.getWeatherData(uiState.lat!!, uiState.lon!!))
        } else {
            uiState = uiState.copy(weather = weatherRepository.getWeatherData())
        }
    }

    fun getWeatherWithAPI() {

    }

    override fun onCleared() {
        super.onCleared()
        compassRepository.stopCompass()
    }

}