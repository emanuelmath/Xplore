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
    val repo = remember { CompassRepositoryImpl(CompassSensorImpl(context)) }

    LaunchedEffect(Unit) {
        repo.startCompass { compass ->
            hayB = compass.isSensorAvailable
            cardinalPoint = compass.cardinalPoint
        }
    }

    Column(modifier = Modifier.padding(top = 50.dp)) {
        Text("HomePrueba")
        Text(cardinalPoint)
        Text("$hayB")
    }
}

