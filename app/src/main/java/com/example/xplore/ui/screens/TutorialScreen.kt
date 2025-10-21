package com.example.xplore.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TutorialScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 40.dp)
        ) {
            Text(
                text = "TUTORIAL",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))


            Box(
                modifier = Modifier
                    .background(Color(0xFF1A4815), shape = RoundedCornerShape(16.dp))
                    .padding(20.dp)
                    .width(280.dp)
                    .height(650.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {

                    Text(
                        text = "¡BIENVENIDO A XPLORE!",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Tu kit de explorador digital. Esta app convierte " +
                                "tu dispositivo en una poderosa herramienta de exploración: detecta, " +
                                "mide e informa en tiempo real del entorno que te rodea.",
                        color = Color.White,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    TutorialItem("🌦 Clima al instante",
                        "Podrás revisar humedad y temperatura directamente con sensores de tu dispositivo (si los tiene). Ideal para conocer datos precisos del clima al momento."
                    )

                    TutorialItem("🧭 Brújula precisa",
                        "Usa los sensores internos para mostrar siempre el norte geográfico. ¡Ideal para orientarte en cualquier lugar!"
                    )

                    TutorialItem("📡 Dirección de cercanía",
                        "Con esta vista te alertará si un objeto o fuente activa se ubica frente al teléfono."
                    )

                    TutorialItem("💡 Pantalla adaptativa",
                        "Detecta la luz ambiental para cambiar automáticamente entre modo claro u oscuro, según la intensidad de luz. ¡Puedes configurarlo manualmente!"
                    )

                    TutorialItem("⚙️ En configuración puedes:",
                        "- Personalizar tu pantalla\n" +
                                "- Activar o desactivar modos según tus dispositivos"
                    )

                    TutorialItem("🗺 Y si gustas usar ubicación avanzada...",
                        "Activa la detección GPS para mayor precisión en tus experiencias."
                    )
                }
            }
        }
    }
}

@Composable
fun TutorialItem(title: String, description: String) {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(
            text = title,
            color = Color(0xFFB6FFAB),
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )
        Text(
            text = description,
            color = Color.White,
            fontSize = 13.sp
        )
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewTutorialScreen() {
    TutorialScreen()
}