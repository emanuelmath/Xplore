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

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp)
            ) {

                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "TUTORIAL",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
            }

            Spacer(modifier = Modifier.height(24.dp))


            Box(
                modifier = Modifier
                    .background(Color(0xFF1A4815), shape = RoundedCornerShape(10.dp))
                    .padding(24.dp)
                    .width(260.dp)
                    .height(650.dp),
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Box(
                    modifier = Modifier
                        .background(Color(0xFF228A57), shape = RoundedCornerShape(10.dp))
                        .padding(24.dp)
                        .width(260.dp)
                        .height(200.dp),
                    ){
                        Text(
                            text = "Tu Kit de Explorador Digital " +
                                    " Esta app convierte" +
                                    " tu dispositivo en una poderosa herramienta de exploración. Detecta," +
                                    " mide y te informa en tiempo real del entorno que te rodea:",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }


                    Spacer(modifier = Modifier.height(10.dp))

                }
            }

        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewTutorialScreen() {
    TutorialScreen()
}