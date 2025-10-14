package com.example.xplore.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.xplore.data.repositories.CompassRepositoryImpl
import com.example.xplore.hardware.compass.CompassSensorImpl


@Composable
fun HomeScreen() {
    val context = LocalContext.current
    var hayB by remember { mutableStateOf(false) }
    var cardinalPoint by remember { mutableStateOf("") }
    var norteA by remember { mutableFloatStateOf(0.0f) }

    val compassRepo = remember { CompassRepositoryImpl(CompassSensorImpl(context)) }

    LaunchedEffect(Unit) {
        compassRepo.startCompass { compass ->
            hayB = compass.isSensorAvailable
            cardinalPoint = compass.cardinalPoint
            norteA = 360.0f - compass.direction
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Brújula")
        Text("Dirección cardinal: $cardinalPoint")
        Text("Sensor disponible: $hayB")
        Text("Norte a: $norteA")

    }
}
