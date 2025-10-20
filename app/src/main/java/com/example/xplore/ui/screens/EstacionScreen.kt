package com.example.xplore.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.xplore.R
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun EstacionScreen(
    temperature: Float,
    humity: Float,
    pressure: Float,
    windSpeed: Float,
    windDirecction: Float,
    darkMode: Boolean = true
) {
    //  Variables simuladas
    //val temperatura = "27°C"
    //val humedad = "65%"
    //val presion = "1012 hPa"
    //val velocidadViento = "12 km/h"
    //val direccionViento = "Noreste"



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

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.clima),
                    contentDescription = "Icono clima",
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "ESTACION \n METEREOLOGICA",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
            }

            Spacer(modifier = Modifier.height(24.dp))


            Box(
                modifier = Modifier
                    .background(Color(0xFF4B5D7C), shape = RoundedCornerShape(10.dp))
                    .padding(24.dp)
                    .width(260.dp)
                    .height(400.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.nublado),
                        contentDescription = "Clima nublado",
                        modifier = Modifier.size(100.dp)
                    )


                    Text(
                        text = "NUBLADO - SANTA ANA, EL SALVADOR",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))


                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        DatoClimaItem("TEMPERATURA", "%.2f".format(temperature))
                        DatoClimaItem("HUMEDAD", "%.2f %".format(humity))
                        DatoClimaItem("PRESION", "%.2f hPa".format(pressure))

                        Spacer(modifier = Modifier.height(50.dp))

                        DatoClimaItem("VELOCIDAD DEL VIENTO", "%.2f km/h".format(windSpeed))
                        DatoClimaItem("DIRECCION DEL VIENTO", "%.2f °".format(windDirecction))
                    }
                }
            }
        }
    }
}


@Composable
fun DatoClimaItem(label: String, value: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Text(
            text = value,
            color = Color.White,
            fontSize = 14.sp
        )
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewEstacionScreen() {
    //EstacionScreen()
}