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
import com.example.xplore.R
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun EstacionScreen(
    temperature: Float,
    humity: Float,
    pressure: Float,
    windSpeed: Float,
    windDirecction: Float,
    location: String,
    stateOfWeather: String,
    intImage: Int,
    backgroundColor: Color = Color.Black
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
            .background(backgroundColor),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 40.dp)
                .verticalScroll(rememberScrollState())
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
                    text = "ESTACIÓN \n METEREOLÓGICA",
                    color = if(backgroundColor == Color.Black) Color.White else Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
            }

            Spacer(modifier = Modifier.height(24.dp))


            Box(
                modifier = Modifier
                    .background(Color(0xFF4B5D7C), shape = RoundedCornerShape(10.dp))
                    .padding(16.dp)
                    .width(260.dp)
                    .wrapContentHeight()
                    .heightIn(max = 400.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    Image(
                        painter = painterResource(intImage),
                        contentDescription = "Clima",
                        modifier = Modifier.size(80.dp)
                    )

                    Text(
                        text = "$stateOfWeather - $location",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Datos del clima
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        DatoClimaItem("TEMPERATURA", "%.2f °C".format(temperature))
                        DatoClimaItem("HUMEDAD", "%.2f %%".format(humity))
                        DatoClimaItem("PRESIÓN", "%.2f hPa".format(pressure))

                        Spacer(modifier = Modifier.height(16.dp))

                        DatoClimaItem("VIENTO", "%.2f km/h".format(windSpeed))
                        DatoClimaItem("DIRECCIÓN", "%.2f °".format(windDirecction))
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