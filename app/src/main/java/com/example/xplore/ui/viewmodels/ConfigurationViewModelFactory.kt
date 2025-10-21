package com.example.xplore.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.xplore.data.local.UserPreferencesDataStore
import com.example.xplore.data.repositories.LightRepositoryImpl
import com.example.xplore.data.repositories.UserRepositoryImpl
import com.example.xplore.data.repositories.WeatherSensorRepositoryImpl
import com.example.xplore.hardware.light.LightSensorImpl
import com.example.xplore.hardware.weather.WeatherSensorImpl


class ConfigurationViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConfigurationViewModel::class.java)) {

            return ConfigurationViewModel(
                UserRepositoryImpl(UserPreferencesDataStore(context)),
                WeatherSensorRepositoryImpl(WeatherSensorImpl(context)),
                LightRepositoryImpl(LightSensorImpl(context))
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class.")
    }
}