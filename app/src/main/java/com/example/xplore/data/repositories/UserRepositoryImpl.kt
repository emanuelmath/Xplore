package com.example.xplore.data.repositories

import com.example.xplore.data.local.UserPreferencesDataStore
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(private val dataStore: UserPreferencesDataStore) : UserRepository {

    override fun getUserName(): Flow<String?> {
        return dataStore.userName
    }

    override suspend fun saveUserName(name: String) {
        dataStore.saveUserName(name)
    }

    override suspend fun clearUserName() {
        dataStore.clearUserName()
    }
}