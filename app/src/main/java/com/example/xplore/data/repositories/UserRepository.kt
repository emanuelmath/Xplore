package com.example.xplore.data.repositories

import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserName(): Flow<String?>
    suspend fun saveUserName(name: String)
    suspend fun clearUserName()
    fun getLightDarkMode(): Flow<Boolean?>
    fun getWeatherAPI(): Flow<Boolean?>
    suspend fun saveOptionLightDarkMode(option: Boolean)
    suspend fun saveOptionWeatherAPI(option: Boolean)
    fun getManualDarkMode(): Flow<Boolean>
    suspend fun saveManualDarkMode(isDark: Boolean)
}