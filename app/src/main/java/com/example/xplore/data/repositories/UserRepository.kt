package com.example.xplore.data.repositories

import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserName(): Flow<String?>
    suspend fun saveUserName(name: String)
    suspend fun clearUserName()
}