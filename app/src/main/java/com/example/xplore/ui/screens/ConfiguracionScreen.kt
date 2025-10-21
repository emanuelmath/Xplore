package com.example.xplore.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun ConfiguracionScreen(backgroundColor: Color = Color.Black) {


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 50.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp)
            ) {

                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "CONFIGURACION",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
            }

            Spacer(modifier = Modifier.height(24.dp))


            Box(
                modifier = Modifier
                    .background(Color(0xFF0E6E2E), shape = RoundedCornerShape(10.dp))
                    .padding(24.dp)
                    .width(260.dp)
                    .height(400.dp),
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = "BIENVENIDO",
                        color = if(backgroundColor == Color.Black) Color.White else Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(Color.White, shape = RoundedCornerShape(10.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                            .fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = "Usuario",
                            onValueChange = {},
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.White,
                                unfocusedBorderColor = Color.LightGray,
                                cursorColor = Color.White,
                                focusedLabelColor = Color.White,
                                unfocusedLabelColor = Color.Gray,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.boton_editar),
                            contentDescription = "Editar",
                            tint = Color(0xFF5CB1FF),
                            modifier = Modifier
                                .size(36.dp)
                                .padding(start = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "ACTIVAR MODO CLARO/OSCURO DE FORMA MANUAL",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Si no tienes el sensor de luz, no podrás desactivar esta opción.",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )

                    var modoManual by remember { mutableStateOf(false) }

                    Switch(
                        checked = modoManual,
                        onCheckedChange = { modoManual = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color(0xFF00E676),
                            uncheckedThumbColor = Color.Gray
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "USAR API PARA CLIMA",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Si no tienes los sensores para clima no podrás desactivar esta opción.",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )

                    var usarApi by remember { mutableStateOf(false) }

                    Switch(
                        checked = modoManual,
                        onCheckedChange = { modoManual = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color(0xFF00E676),
                            uncheckedThumbColor = Color.Gray
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))




                    Spacer(modifier = Modifier.height(10.dp))

                }
            }

        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewConfiguracionScreen() {
  ConfiguracionScreen()
}