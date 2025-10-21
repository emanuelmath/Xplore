package com.example.xplore.data.local

import androidx.datastore.preferences.preferencesDataStore
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

private const val DATASTORE_NAME = "user_prefs"
val Context.dataStore by preferencesDataStore(name = DATASTORE_NAME)

class UserPreferencesDataStore(private val context: Context) {

    companion object {
        private val USER_NAME_KEY = stringPreferencesKey("user_name")
        private val LIGHT_DARK_MODE_MANUALLY_KEY = booleanPreferencesKey("light_dark_mode_manually")
        private val USE_WEATHERAPI_KEY = booleanPreferencesKey("use_weatherapi")

        private const val DEFAULT_USER_NAME = "Usuario"
        private const val DEFAULT_LIGHT_DARK_MODE = true
        private const val DEFAULT_USE_WEATHER_API = true
    }

    val userName: Flow<String> = context.dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { prefs ->
            prefs[USER_NAME_KEY] ?: DEFAULT_USER_NAME
        }

    val useLightDarkModeManually: Flow<Boolean> = context.dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { prefs ->
            prefs[LIGHT_DARK_MODE_MANUALLY_KEY] ?: DEFAULT_LIGHT_DARK_MODE
        }

    val useWeatherAPI: Flow<Boolean> = context.dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { prefs ->
            prefs[USE_WEATHERAPI_KEY] ?: DEFAULT_USE_WEATHER_API
        }

    // --- Escritura de preferencias ---
    suspend fun saveUserName(name: String) {
        context.dataStore.edit { prefs ->
            prefs[USER_NAME_KEY] = name.ifBlank { DEFAULT_USER_NAME }
        }
    }

    suspend fun saveOptionLightDarkMode(option: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[LIGHT_DARK_MODE_MANUALLY_KEY] = option
        }
    }

    suspend fun saveOptionWeatherAPI(option: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[USE_WEATHERAPI_KEY] = option
        }
    }

    suspend fun clearAll() {
        context.dataStore.edit { prefs -> prefs.clear() }
    }
}