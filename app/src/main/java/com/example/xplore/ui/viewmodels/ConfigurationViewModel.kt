package com.example.xplore.ui.viewmodels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xplore.data.repositories.LightRepository
import com.example.xplore.data.repositories.UserRepository
import com.example.xplore.data.repositories.WeatherSensorRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class ConfigurationViewModel(private val userRepository: UserRepository,
                            private val weatherSensorRepository: WeatherSensorRepository,
                            private val lightRepository: LightRepository
    ) : ViewModel() {

    var uiState by mutableStateOf(ConfigurationUiState())
        private set


    fun getUserName() {
        viewModelScope.launch {
            uiState = try {
                uiState.copy(
                    userName = userRepository.getUserName().first()
                )
            } catch (e: Exception) {
                uiState.copy(
                    errorMessage = e.message
                )
            }
        }
    }

    fun editUserName(newUserName: String) {
        viewModelScope.launch {
            try {
                userRepository.saveUserName(newUserName)
                getUserName()
            } catch(e: Exception) {
                uiState = uiState.copy(
                    errorMessage = e.message
                )
            }
        }
    }

    fun getAllSensors() {
        lightRepository.startLight { light ->
            uiState = uiState.copy(light = light)
            handleLightSensorAvailability()
        }

        viewModelScope.launch {
            uiState = uiState.copy(weather = weatherSensorRepository.getWeatherFromSensor())
            handleWeatherApiSensorAvailability()
        }

    }
    fun loadAllPreferences() {
        viewModelScope.launch {
            try {
                val userName = userRepository.getUserName().first()
                val lightDarkMode = userRepository.getLightDarkMode().first()
                val weatherAPI = userRepository.getWeatherAPI().first()

                uiState = uiState.copy(
                    userName = userName,
                    optionLightDarkMode = lightDarkMode,
                    optionWeatherAPI = weatherAPI,
                )
            } catch (e: Exception) {
                uiState = uiState.copy(errorMessage = e.message)
            }
        }
    }

    fun setLightDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            userRepository.saveOptionLightDarkMode(enabled)
            uiState = uiState.copy(optionLightDarkMode = enabled)
        }
    }

    fun setWeatherAPIUsage(enabled: Boolean) {
        viewModelScope.launch {
            userRepository.saveOptionWeatherAPI(enabled)
            uiState = uiState.copy(optionWeatherAPI = enabled)
        }
    }

    fun handleLightSensorAvailability() {
        viewModelScope.launch {
            val hasLightSensor = uiState.light?.isSensorAvailable ?: false
            if (!hasLightSensor) {
                userRepository.saveOptionLightDarkMode(true)
                uiState = uiState.copy(optionLightDarkMode = true)
            }
        }
    }

    fun handleWeatherApiSensorAvailability() {
        viewModelScope.launch {
            val hasWeatherSensor = uiState.weather?.isSensorAvailable ?: false

            val savedValue = userRepository.getWeatherAPI().firstOrNull()

            if (savedValue == null) {
                val initialValue = !hasWeatherSensor
                userRepository.saveOptionWeatherAPI(initialValue)
                uiState = uiState.copy(optionWeatherAPI = initialValue)
            } else {
                uiState = uiState.copy(optionWeatherAPI = savedValue)
            }
        }
    }



}