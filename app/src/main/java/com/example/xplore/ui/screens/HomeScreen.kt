package com.example.xplore.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.xplore.ui.navigation.Screen
import com.example.xplore.ui.viewmodels.MainViewModel
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.delay
import com.google.android.gms.location.Priority
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


@Composable
fun HomeScreen(navController: NavHostController, mainViewModel: MainViewModel) {
    val context = LocalContext.current
    val uiState = mainViewModel.uiState

    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }
    var hasPermission by remember { mutableStateOf(false) }
    var location by remember { mutableStateOf<Location?>(null) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        hasPermission = perms[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                perms[Manifest.permission.ACCESS_COARSE_LOCATION] == true
    }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    LaunchedEffect(hasPermission) {
        if (hasPermission) {
            try {
                val loc = fusedLocationClient.getCurrentLocation(
                    Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                    null
                ).await()
                location = loc
                loc?.let { mainViewModel.sendLocationData(it.latitude, it.longitude) }
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }
    }

    LaunchedEffect(Unit) {
        mainViewModel.getAllSensors()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(top = 25.dp)
            .fillMaxSize()
    ) {
        Text("Brújula")
        Text("Dirección cardinal: ${uiState.compass?.cardinalPoint ?: "-"}")
        Text("Sensor disponible: ${uiState.compass?.isSensorAvailable ?: false}")
        Text("Norte a: ${uiState.compass?.direction?.let { 360 - it } ?: "-"}")
        Spacer(Modifier.height(16.dp))

        Text("Clima")
        if(uiState.weather?.isSensorAvailable == true) {
            Text("Sensor disponible, leyendo del dispositivo")
        } else {
            Text("Usando datos de Open Meteo")
        }
        Text("Temperatura: ${uiState.weather?.temperature ?: "-"} °C")
        Text("Presión atmosférica: ${uiState.weather?.pressure ?: "-"} hPa")
        Text("Humedad: ${uiState.weather?.humidity ?: "-"} %")
        if (uiState.weather?.isSensorAvailable == false || uiState.weather == null) {
            Text("Velocidad del viento: ${uiState.weather?.windSpeed ?: "-"}")
            Text("Dirección del viento: ${uiState.weather?.windDirection ?: "-"}")
            Text("Ubicación: ${uiState.weather?.locationName ?: "-"} ")
            Spacer(Modifier.height(8.dp))
            Button(onClick = {
                location?.let {
                    //coroutineScope.launch {
                        mainViewModel.sendLocationData(it.latitude, it.longitude)
                    //}
                }
            }) {
                Text("Actualizar datos clima")
            }
        }

        Spacer(Modifier.height(16.dp))

        Text("Proximidad")
        Text("Está cerca: ${uiState.proximity?.isNear ?: "-"}")
        Text("Distancia: ${uiState.proximity?.distance ?: "-"} cm")
        Spacer(Modifier.height(16.dp))

        Text("Luz")
        Text("Lux actual: ${uiState.light?.lux ?: "-"}")
        Text("Modo Claro/Oscuro: ${uiState.light?.currentState ?: "-"}")
    }
}

