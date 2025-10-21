package com.example.xplore.ui.screens


import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.xplore.R
import com.example.xplore.ui.viewmodels.MainViewModel
import com.example.xplore.utils.getCardinalPointName
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import com.example.xplore.utils.getWeatherDescription
import com.example.xplore.utils.getWeatherDescriptionImage

//@Preview
@Composable
fun Home2Screen(navController: NavHostController, mainViewModel: MainViewModel) {

    val uiState = mainViewModel.uiState
    val context = LocalContext.current
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

    var backgroundColor by remember { mutableStateOf(Color.Black) }
    var fontsColor by remember { mutableStateOf(Color.White) }
    if(uiState.light != null && uiState.light.isSensorAvailable) {
        backgroundColor = if(uiState.light.currentState == "Dark Mode") Color.Black else Color.White
        fontsColor = if(uiState.light.currentState == "Dark Mode") Color.White else Color.Black
    }

    var showCompassDialog by remember { mutableStateOf(false) }
    var showWeatherAPIDialog by remember { mutableStateOf(false) }
    var showWeatherSensorDialog by remember { mutableStateOf(false) }
    var showProximityDialog by remember { mutableStateOf(false) }
    var showLightDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(horizontal = 20.dp, vertical = 40.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            // Logo y título
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.xplorelogo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(55.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "HERRAMIENTAS",
                    color = fontsColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))


            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (uiState.compass != null && uiState.compass.isSensorAvailable) {
                    ToolCard(
                        title = "BRÚJULA",
                        subtitle = getCardinalPointName(uiState.compass.cardinalPoint),
                        backgroundColor = Color(0xFF0E6E2E),
                        icon = R.drawable.brujulasinaguja,
                        modifier = Modifier
                            .weight(1f)
                            .height(190.dp),
                        verticalLayout = true,
                        customContent = {
                            CompassView(direction = uiState.compass.direction)
                        },
                        onClick = { showCompassDialog = true }
                    )

                    if (showCompassDialog) {
                        AlertDialog(
                            onDismissRequest = { showCompassDialog = false },
                            confirmButton = {
                                TextButton(onClick = { showCompassDialog = false }) {
                                    Text("Cerrar")
                                }
                            },
                            text = {
                                BrujulaScreen(compassView =
                                    { CompassView(uiState.compass.direction) },
                                direction = uiState.compass.direction,
                                cardinalPoint = getCardinalPointName(uiState.compass.cardinalPoint),
                                    backgroundColor = backgroundColor
                                )
                            }
                        )
                    }


                } else {
                    ToolCard(
                        title = "BRÚJULA",
                        subtitle = "NO DISPONIBLE",
                        backgroundColor = Color(0xFF0E6E2E),
                        icon = R.drawable.brujulanodisponible,
                        modifier = Modifier
                            .weight(1f)
                            .height(190.dp),
                        verticalLayout = true
                    )
                }


                if(uiState.sensorWeather != null && uiState.sensorWeather.isSensorAvailable) {
                    val temperatureFormat = "%.2f".format(uiState.sensorWeather.temperature)
                    ToolCard(
                        title = "CLIMA",
                        subtitle = "$temperatureFormat °C",
                        backgroundColor = Color(0xFF263C5C),
                        icon = R.drawable.clima,
                        modifier = Modifier
                            .weight(1f)
                            .height(190.dp),
                        verticalLayout = true
                    )

                    if(showWeatherSensorDialog) {
                        if (showWeatherAPIDialog && uiState.apiWeather != null) {
                            AlertDialog(
                                onDismissRequest = { showWeatherSensorDialog = false },
                                confirmButton = {
                                    TextButton(onClick = { showWeatherSensorDialog = false }) {
                                        Text("Cerrar")
                                    }
                                },
                                text = {
                                    Column(modifier = Modifier
                                        .fillMaxWidth()
                                        .heightIn(max = 500.dp)
                                    ) {
                                        EstacionSensorScreen(
                                            uiState.sensorWeather.temperature.toFloat(),
                                            uiState.sensorWeather.humidity.toFloat(),
                                            uiState.sensorWeather.pressure.toFloat(),
                                            backgroundColor
                                        )
                                    }
                                }
                            ) }
                    }
                } else {
                    ToolCard(title = "CLIMA",
                        subtitle = "${uiState.apiWeather?.temperature ?: "Desconocida"} °C",
                        backgroundColor = Color(0xFF263C5C),
                        icon = R.drawable.clima,
                        modifier = Modifier
                            .weight(1f)
                            .height(190.dp),
                        verticalLayout = true,
                        onClick = { showWeatherAPIDialog = true }
                        )


                    if (showWeatherAPIDialog && uiState.apiWeather != null) {
                        AlertDialog(
                            onDismissRequest = { showWeatherAPIDialog = false },
                            confirmButton = {
                                TextButton(onClick = { showWeatherAPIDialog = false }) {
                                    Text("Cerrar")
                                }
                            },
                            text = {
                                Column(modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = 500.dp)
                                ) {
                                EstacionScreen(
                                    uiState.apiWeather.temperature.toFloat(),
                                    uiState.apiWeather.humidity.toFloat(),
                                    uiState.apiWeather.pressure.toFloat(),
                                    uiState.apiWeather.windSpeed.toFloat(),
                                    uiState.apiWeather.windDirection.toFloat(),
                                    uiState.apiWeather.locationName,
                                    getWeatherDescription(uiState.apiWeather.weatherCode),
                                    getWeatherDescriptionImage(uiState.apiWeather.weatherCode),
                                    backgroundColor
                                )
                            }
                            })
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if(uiState.sensorWeather == null || !uiState.sensorWeather.isSensorAvailable) {
                if (!hasPermission) {
                    Text(
                        "Para obtener clima real, otorga permiso de ubicación.",
                        color = fontsColor,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            permissionLauncher.launch(
                                arrayOf(
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                )
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88E5)),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Permitir ubicación", color = Color.White)
                    }
                } else {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                requestLocation(mainViewModel, fusedLocationClient)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88E5)),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Obtener clima con ubicación", color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
            }


            if(uiState.proximity != null && uiState.proximity.isSensorAvailable) {
                ToolCard(
                    title = "DETECTOR DE\nPROXIMIDAD",
                    subtitle = if(uiState.proximity.isNear) "CERCA" else "LEJOS",
                    backgroundColor = if(!uiState.proximity.isNear) Color(0xFF263C5C) else Color(0xFF5C2626),
                    icon = R.drawable.aproximacion,
                    modifier = Modifier.height(150.dp),
                    verticalLayout = false,
                    onClick = { showProximityDialog = true }
                )
                if(showProximityDialog) {
                    AlertDialog(
                        onDismissRequest = { showProximityDialog = false },
                        confirmButton = {
                            TextButton(onClick = { showProximityDialog = false }) {
                                Text("Cerrar")
                            }
                        },
                        text = {
                            AproximacionScreen(uiState.proximity.distance, uiState.proximity.isNear, backgroundColor)
                        })

                }


            } else {
                ToolCard(
                    title = "DETECTOR DE\nPROXIMIDAD",
                    subtitle = "NO DISPONIBLE",
                    backgroundColor = Color(0xFF263C5C),
                    icon = R.drawable.aproximacion,
                    modifier = Modifier.height(150.dp),
                    verticalLayout = false
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            if(uiState.light != null && uiState.light.isSensorAvailable) {
                ToolCard(
                    title = "PANTALLA\nADAPTIVA",
                    subtitle = uiState.light.currentState,
                    backgroundColor = Color(0xFF0E6E2E),
                    icon = R.drawable.luz,
                    modifier = Modifier.height(150.dp),
                    verticalLayout = false,
                    onClick = { showLightDialog = true }
                )

                if(showLightDialog) {
                    AlertDialog(
                        onDismissRequest = { showLightDialog = false },
                        confirmButton = {
                            TextButton(onClick = { showLightDialog = false }) {
                                Text("Cerrar")
                            }
                        },
                        text = {
                            LuzScreen(uiState.light.lux, uiState.light.currentState, backgroundColor)
                        })

                }
            } else {
                ToolCard(
                    title = "PANTALLA\nADAPTIVA",
                    subtitle = "NO DISPONIBLE",
                    backgroundColor = Color(0xFF0E6E2E),
                    icon = R.drawable.luz,
                    modifier = Modifier.height(150.dp),
                    verticalLayout = false
                )
            }
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun ToolCard(
    title: String,
    subtitle: String,
    backgroundColor: Color,
    icon: Int,
    modifier: Modifier = Modifier,
    verticalLayout: Boolean,
    customContent: (@Composable () -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = modifier.clickable { onClick?.invoke() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        if (verticalLayout) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (customContent != null) {
                    customContent()
                } else {
                    Image(
                        painter = painterResource(id = icon),
                        contentDescription = title,
                        modifier = Modifier.size(100.dp)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    lineHeight = 28.sp,
                    textAlign = TextAlign.Center
                )
                if (subtitle.isNotEmpty()) {
                    Text(
                        text = subtitle,
                        color = Color.White,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = title,
                    modifier = Modifier.size(100.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = title,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        lineHeight = 24.sp,
                        textAlign = TextAlign.Center
                    )
                    if (subtitle.isNotEmpty()) {
                        Text(
                            text = subtitle,
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
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

@Composable
fun CompassView(direction: Float) {
    var previousRotation by rememberSaveable { mutableStateOf(direction) }

    val delta = ((direction - previousRotation + 540f) % 360f) - 180f
    val adjustedRotation = (previousRotation + delta + 360f) % 360f
    previousRotation = adjustedRotation

    val animatedRotation by animateFloatAsState(
        targetValue = -adjustedRotation,
        animationSpec = tween(durationMillis = 300, easing = LinearEasing),
        label = "compass_rotation"
    )

    Box(
        modifier = Modifier
            .size(100.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.brujula),
            contentDescription = "Brújula giratoria",
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    rotationZ = animatedRotation
                    transformOrigin = TransformOrigin.Center
                }
        )
    }
}



@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Home2ScreenPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 20.dp, vertical = 40.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxSize()
        ) {

            // Logo y título
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.xplorelogo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(55.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "HERRAMIENTAS",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
            }

            Spacer(modifier = Modifier.height(70.dp))


            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                ToolCard(
                    title = "BRUJULA",
                    subtitle = "NORTE",
                    backgroundColor = Color(0xFF0E6E2E),
                    icon = R.drawable.brujula,
                    modifier = Modifier
                        .weight(1f)
                        .height(190.dp),
                    verticalLayout = true
                )

                ToolCard(
                    title = "CLIMA",
                    subtitle = "25.6 °C",
                    backgroundColor = Color(0xFF263C5C),
                    icon = R.drawable.clima,
                    modifier = Modifier
                        .weight(1f)
                        .height(190.dp),
                    verticalLayout = true
                )
            }

            Spacer(modifier = Modifier.height(50.dp))


            ToolCard(
                title = "DETECTOR DE\nAPROXIMACIÓN",
                subtitle = "",
                backgroundColor = Color(0xFF263C5C),
                icon = R.drawable.aproximacion,
                modifier = Modifier.height(150.dp),
                verticalLayout = false
            )

            Spacer(modifier = Modifier.height(20.dp))

            ToolCard(
                title = "PANTALLA\nADAPTIVA",
                subtitle = "",
                backgroundColor = Color(0xFF0E6E2E),
                icon = R.drawable.luz,
                modifier = Modifier.height(150.dp),
                verticalLayout = false
            )
        }
    }
}