package com.example.xplore.data.repositories

import com.example.xplore.data.local.UserPreferencesDataStore
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(private val dataStore: UserPreferencesDataStore) : UserRepository {

    override fun getUserName(): Flow<String> = dataStore.userName

    override suspend fun saveUserName(name: String) {
        dataStore.saveUserName(name)
    }

    override suspend fun clearUserName() {
        dataStore.clearAll()
    }

    override fun getLightDarkMode(): Flow<Boolean> = dataStore.useLightDarkModeManually

    override fun getWeatherAPI(): Flow<Boolean> = dataStore.useWeatherAPI

    override suspend fun saveOptionLightDarkMode(option: Boolean) {
        dataStore.saveOptionLightDarkMode(option)
    }

    override suspend fun saveOptionWeatherAPI(option: Boolean) {
        dataStore.saveOptionWeatherAPI(option)
    }
}
