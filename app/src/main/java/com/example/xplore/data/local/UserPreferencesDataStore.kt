package com.example.xplore.data.local

import androidx.datastore.preferences.preferencesDataStore
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val DATASTORE_NAME = "user_prefs"
val Context.dataStore by preferencesDataStore(name = DATASTORE_NAME)

class UserPreferencesDataStore(private val context: Context) {

    companion object {
        private val USER_NAME_KEY = stringPreferencesKey("user_name")
        private val LIGHT_DARK_MODE_MANUALLY_KEY = booleanPreferencesKey("light_dark_mode_manually")
        private val USE_WEATHERAPI_KEY = booleanPreferencesKey("use_weatherapi")
    }

    val userName: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[USER_NAME_KEY] ?: "Usuario"
    }

    val useLightDarkModeManually: Flow<Boolean?> = context.dataStore.data.map { prefs ->
        prefs[LIGHT_DARK_MODE_MANUALLY_KEY] ?: true
    }

    val useWeatherAPI: Flow<Boolean?> = context.dataStore.data.map { prefs ->
        prefs[USE_WEATHERAPI_KEY] ?: true
    }

    suspend fun saveUserName(name: String) {
        context.dataStore.edit { prefs ->
            prefs[USER_NAME_KEY] = name
        }
    }

    suspend fun clearUserName() {
        context.dataStore.edit { prefs ->
            prefs.remove(USER_NAME_KEY)
        }
    }

    suspend fun saveOptionLightDarkMode(option: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[LIGHT_DARK_MODE_MANUALLY_KEY] = option
        }
    }

    suspend fun clearOptionLightDarkMode() {
        context.dataStore.edit { prefs ->
            prefs.remove(LIGHT_DARK_MODE_MANUALLY_KEY)
        }
    }

    suspend fun saveOptionWeatherAPI(option: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[USE_WEATHERAPI_KEY] = option
        }
    }

    suspend fun clearOptionWeatherAPI() {
        context.dataStore.edit { prefs ->
            prefs.remove(USE_WEATHERAPI_KEY)
        }
    }

}