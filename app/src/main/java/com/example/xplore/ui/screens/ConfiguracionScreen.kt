package com.example.xplore.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.xplore.R
import androidx.compose.ui.tooling.preview.Preview
import com.example.xplore.ui.viewmodels.ConfigurationViewModel

@Composable
fun ConfiguracionScreen(
    configurationViewModel: ConfigurationViewModel,
    backgroundColor: Color = Color.Black
) {

    val context = LocalContext.current
    val uiState = configurationViewModel.uiState
    val currentName = uiState.userName ?: "Usuario"
    var editableName by rememberSaveable { mutableStateOf(currentName) }
    var canEdit by remember { mutableStateOf(false) }
    var modoManual by remember { mutableStateOf(uiState.optionLightDarkMode == true) }
    var usarApi by remember { mutableStateOf(uiState.optionWeatherAPI == true) }
    val canChangeManuallyLightDarkMode = uiState.isLightSensorAvailable ?: false
    val canChangeApiUse = uiState.isWeatherSensorAvailable ?: false

    LaunchedEffect(Unit) {
        configurationViewModel.loadAllPreferences()
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
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
                    text = "CONFIGURACIÓN",
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
                    .height(440.dp)
            ) {
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
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
                            value = if (!canEdit) currentName else editableName,
                            onValueChange = {editableName = it},
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.White,
                                unfocusedBorderColor = Color.Gray,
                                cursorColor = Color.White,
                                focusedLabelColor = Color.White,
                                unfocusedLabelColor = Color.Gray,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White
                            ),
                            enabled = canEdit,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = {
                                if (canEdit) {
                                    if (editableName.isNotBlank()) {
                                        configurationViewModel.editUserName(editableName)
                                        Toast.makeText(context, "Nombre guardado.", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "Nombre inválido, no guardado.", Toast.LENGTH_SHORT).show()
                                    }
                                } else {
                                    editableName = uiState.userName ?: "Usuario"
                                }
                                canEdit = !canEdit
                            },
                            modifier = Modifier
                                .size(36.dp)
                                .padding(start = 4.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.boton_editar),
                                contentDescription = if (canEdit) "Guardar" else "Editar",
                                tint = if (!canEdit) Color(0xFF5CB1FF) else Color(0xFF8D6E63),
                            )
                        }
                    }


                        Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.Start
                        ) {
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
                                textAlign = TextAlign.Start
                            )
                        }

                        Switch(
                            checked = modoManual,
                            onCheckedChange = {
                                modoManual = it
                                configurationViewModel.setLightDarkMode(it)
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color(0xFF00E676),
                                uncheckedThumbColor = Color.Gray
                            ),
                            enabled = canChangeManuallyLightDarkMode
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.Start
                        ) {
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
                                textAlign = TextAlign.Start
                            )
                        }

                        Switch(
                            checked = usarApi,
                            onCheckedChange = {
                                usarApi = it
                                configurationViewModel.setWeatherAPIUsage(it)
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color(0xFF00E676),
                                uncheckedThumbColor = Color.Gray
                            ),
                            enabled = canChangeApiUse
                        )
                    }

                }
            }

        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewConfiguracionScreen() {
 // ConfiguracionScreen()
}