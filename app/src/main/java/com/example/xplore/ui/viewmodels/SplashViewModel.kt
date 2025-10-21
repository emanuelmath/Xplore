package com.example.xplore.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.xplore.data.repositories.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

class SplashViewModel(private val userRepository: UserRepository) : ViewModel() {

    var uiState by mutableStateOf(SplashUiState())
        private set

    suspend fun getUserName() {
        uiState = uiState.copy(name = userRepository.getUserName().first() ?: "Usuario")
        delay(2000L)
        uiState = uiState.copy(isLoading = false)
    }
}