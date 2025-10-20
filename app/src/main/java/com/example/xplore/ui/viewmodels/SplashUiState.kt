package com.example.xplore.ui.viewmodels

import kotlinx.coroutines.flow.Flow

data class SplashUiState(
    val name: String = "Usuario", //Flow<String?> = "",
    val isLoading: Boolean = true
)
