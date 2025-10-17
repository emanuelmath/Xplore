package com.example.xplore.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.widget.Toast
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.xplore.utils.getWeatherDescription
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


@Composable
fun HomeScreen(navController: NavHostController, mainViewModel: MainViewModel) {
    val context = LocalContext.current
    val uiState = mainViewModel.uiState
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    var hasPermission by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        hasPermission = perms[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                perms[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (hasPermission) {
            coroutineScope.launch {
                requestLocation(mainViewModel, fusedLocationClient)
            }
        } else {
            mainViewModel.setErrorMessage("Permiso de ubicación denegado.")
        }
    }

    LaunchedEffect(Unit) {
        mainViewModel.getAllSensors()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        if (uiState.normalMessage.isNotEmpty()) {
            Text(uiState.normalMessage, color = Color.Blue, fontSize = 14.sp)
        }
        if (uiState.errorMessage.isNotEmpty()) {
            Text(uiState.errorMessage, color = Color.Red, fontSize = 14.sp)
        }

        Spacer(Modifier.height(16.dp))

        Text("Clima (repositorio automático)")
            Text("Temp: ${uiState.weather?.temperature} °C")
            Text("Humedad: ${uiState.weather?.humidity} %")
            Text("Presión: ${uiState.weather?.pressure} hPa")
            Text("Viento: ${uiState.weather?.windSpeed} km/h - ${uiState.weather?.windDirection}°")
            Text("Ubicación: ${uiState.weather?.locationName}")
            Text("Usó sensor: ${uiState.weather?.isSensorAvailable}")


        Spacer(Modifier.height(16.dp))

        Text("Clima (solo sensor)")
        if ( uiState.sensorWeather?.isSensorAvailable == true) { //uiState.sensorWeather != null &&
            Text("Temp: ${uiState.sensorWeather.temperature} °C")
            Text("Humedad: ${uiState.sensorWeather.humidity} %")
            Text("Presión: ${uiState.sensorWeather.pressure} hPa")
        } else {
            Text("Sensor no disponible o datos incompletos.")
        }

        Spacer(Modifier.height(16.dp))

        Text("Clima (solo API)")
        if (uiState.apiWeather != null) {
            Text("Temp: ${uiState.apiWeather.temperature} °C")
            Text("Humedad: ${uiState.apiWeather.humidity} %")
            Text("Presión: ${uiState.apiWeather.pressure} hPa")
            Text("Viento: ${uiState.apiWeather.windSpeed} km/h - ${uiState.apiWeather.windDirection}°")
            Text("Ubicación: ${uiState.apiWeather.locationName}")
            Text("Estado: ${getWeatherDescription(uiState.apiWeather.weatherCode)}")
        } else {
            Text("Datos de API no disponibles aún.")
        }

        Spacer(Modifier.height(24.dp))

        if (!hasPermission) {
            Text("Para obtener clima real, otorga permiso de ubicación.")
            Button(onClick = {
                permissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }) {
                Text("Permitir ubicación")
            }
        } else {
            Button(onClick = {
                coroutineScope.launch {
                    requestLocation(mainViewModel, fusedLocationClient)
                }
            }) {
                Text("Obtener clima con ubicación")
            }
        }

        Spacer(Modifier.height(24.dp))

        Text("Brújula")
        Text("Dirección cardinal: ${uiState.compass?.cardinalPoint}")
        Text("Sensor disponible: ${uiState.compass?.isSensorAvailable}")
        Text("Norte a: ${uiState.compass?.direction?.let { 360 - it } ?: "-"}°")

        Spacer(Modifier.height(16.dp))

        Text(if(uiState.proximity?.isNear == true) "🟣 Proximidad" else "🔴 Proximidad")
        Text("Está cerca: ${uiState.proximity?.isNear}")
        Text("Distancia: ${uiState.proximity?.distance}")

        Spacer(Modifier.height(16.dp))

        Text("Luz")
        Text("Lux actual: ${uiState.light?.lux}")
        Text("Modo Claro/Oscuro: ${uiState.light?.currentState}")
    }
}


private suspend fun requestLocation(
    vm: MainViewModel,
    fusedLocationClient: FusedLocationProviderClient
) {
    try {
        val loc = fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null).await()
        if (loc != null) {
            vm.setNormalMessage("Ubicación: ${loc.latitude}, ${loc.longitude}")
            vm.onLocationReceived(loc.latitude, loc.longitude) // Esto obtiene automático
            vm.getWeatherFromSensor()
            vm.getWeatherFromApi(loc.latitude, loc.longitude)
        } else {
            vm.setErrorMessage("No se pudo obtener la ubicación.")
        }
    } catch (e: SecurityException) {
        e.printStackTrace()
        vm.setErrorMessage("Error de permisos: ${e.message}")
    } catch (e: Exception) {
        e.printStackTrace()
        vm.setErrorMessage("Error inesperado: ${e.message}")
    }
}







/*@Composable
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
                    coroutineScope.launch {
                        mainViewModel.sendLocationData(it.latitude, it.longitude)
                    }
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
}*/

