package com.example.xplore.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.xplore.data.local.UserPreferencesDataStore
import com.example.xplore.data.repositories.CompassRepositoryImpl
import com.example.xplore.data.repositories.LightRepositoryImpl
import com.example.xplore.data.repositories.ProximityRepositoryImpl
import com.example.xplore.data.repositories.UserRepository
import com.example.xplore.data.repositories.UserRepositoryImpl
import com.example.xplore.data.repositories.WeatherApiRepositoryImpl
import com.example.xplore.data.repositories.WeatherSensorRepositoryImpl
import com.example.xplore.hardware.compass.CompassSensorImpl
import com.example.xplore.hardware.light.LightSensorImpl
import com.example.xplore.hardware.proximity.ProximitySensorImpl
import com.example.xplore.hardware.weather.WeatherSensorImpl

class SplashViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {

            return SplashViewModel(
                userRepository = UserRepositoryImpl(UserPreferencesDataStore(context))
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class.")
    }
}