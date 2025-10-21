package com.example.xplore.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.xplore.R

@Composable
fun TutorialScreen(
    backgroundColor: Color = Color.Black,
    textColor: Color = Color.White
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 40.dp)
        ) {
            Row {
              Image(
                  painterResource(R.drawable.tutoriallogo),
                  contentDescription = "Logo de tutorial",
                  modifier = Modifier.size(50.dp)
              )

                Text(
                    text = "TUTORIAL",
                    color = textColor,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }

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
                        "Podrás revisar humedad, presión y temperatura directamente con sensores de tu dispositivo (si los tiene). Ideal para conocer datos precisos del clima al momento." +
                                "Si no los posees, da permisos para ubicación precisa y con conexión a internet podrás recuperar esos datos."
                    )

                    TutorialItem("🧭 Brújula precisa",
                        "Usa los sensores internos para mostrar siempre el norte geográfico. ¡Ideal para orientarte en cualquier lugar!"
                    )

                    TutorialItem("📡 Dirección de cercanía",
                        "Con esta vista te alertará si un objeto o fuente activa se ubica frente al teléfono."
                    )

                    TutorialItem("💡 Pantalla adaptativa",
                        "Detecta la luz ambiental para cambiar automáticamente entre modo claro u oscuro, según la intensidad de luz. ¡También puedes configurarlo manualmente!"
                    )

                    TutorialItem("⚙️ En configuración puedes:",
                        "- Poner tu nombre de usuario.\n" +
                                "- Activar o desactivar opciones según tus sensores y su disponibilidad."
                    )

                    TutorialItem("🗺✨ Tip informativo",
                        "Si diste solo ubicación aproximada, ve a Configuración → Ubicación → y permite acceso preciso para una mejor experiencia."
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