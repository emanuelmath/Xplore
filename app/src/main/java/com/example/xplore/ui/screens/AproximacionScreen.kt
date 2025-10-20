package com.example.xplore.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun AproximacionScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxSize()
        ) {


            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.aproximacion),
                    contentDescription = "Icono aproximación",
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "DETECTOR DE\nAPROXIMACIÓN",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
            }

            Spacer(modifier = Modifier.height(24.dp))


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color(0xFF4C5B75), RoundedCornerShape(10.dp))
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            )
            {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "DISTANCIA:",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        //aca lo cambias por lo del sensor xd
                        Text(
                            text = "5 CM",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }

                    Button(
                        onClick = {  },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF136F2E)),
                        shape = RoundedCornerShape(6.dp),
                                modifier = Modifier
                                    .width(150.dp)
                            .height(50.dp)
                    ) {
                        Text(
                            text = "LEJOS",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }


                    Image(
                        painter = painterResource(id = R.drawable.icono_radar),
                        contentDescription = "Radar",
                        modifier = Modifier.size(100.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AproximacionScreenPreview() {
    AproximacionScreen()
}
