package com.example.xplore.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.xplore.ui.navigation.Screen
import com.example.xplore.ui.viewmodels.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(navController: NavHostController, mainViewModel: MainViewModel) {
    val uiState = mainViewModel.uiState

    LaunchedEffect(Unit) {
        mainViewModel.getAllSensors()
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.padding(top = 25.dp).fillMaxSize()) {
        Text("Brújula")
        Text("Dirección cardinal: ${uiState.compass?.cardinalPoint ?: "-"}")
        Text("Sensor disponible: ${uiState.compass?.isSensorAvailable ?: false}")
        Text("Norte a: ${uiState.compass?.direction?.let { 360 - it } ?: "-"}")

        Text("Clima")
        Text("Temperatura: ${uiState.weather?.temperature}")
        Text("Presión atmosférica: ${uiState.weather?.pressure}")
        Text("Humedad: ${uiState.weather?.humidity}")

        Text("Proximidad")
        Text("Está cerca: ${uiState.proximity?.isNear}")
        Text("Distancia: ${uiState.proximity?.distance}")


    }
}