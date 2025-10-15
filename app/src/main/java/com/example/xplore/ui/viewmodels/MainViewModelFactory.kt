package com.example.xplore.ui.viewmodels

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.xplore.data.repositories.CompassRepositoryImpl
import com.example.xplore.data.repositories.ProximityRepositoryImpl
import com.example.xplore.data.repositories.WeatherApiRepositoryImpl
import com.example.xplore.data.repositories.WeatherRepositoryImpl
import com.example.xplore.data.repositories.WeatherSensorRepositoryImpl
import com.example.xplore.hardware.compass.CompassSensorImpl
import com.example.xplore.hardware.proximity.ProximitySensor
import com.example.xplore.hardware.proximity.ProximitySensorImpl
import com.example.xplore.hardware.weather.WeatherSensor
import com.example.xplore.hardware.weather.WeatherSensorImpl

class MainViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {

            return MainViewModel(
                CompassRepositoryImpl(CompassSensorImpl(context)),
                WeatherRepositoryImpl(
                    WeatherApiRepositoryImpl(),
                    WeatherSensorRepositoryImpl(WeatherSensorImpl(context))),
                ProximityRepositoryImpl(ProximitySensorImpl(context))
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class.")
    }
}
