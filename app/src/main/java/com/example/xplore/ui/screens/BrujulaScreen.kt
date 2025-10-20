package com.example.xplore.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.xplore.R
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.text.style.TextAlign
import com.example.xplore.utils.getCardinalPointName


@Composable
fun BrujulaScreen(compassView: (@Composable () -> Unit),
                  direction: Float,
                  cardinalPoint: String,
                  backgroundColor: Color = Color.Black) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.brujula),
                    contentDescription = "Logo",
                    modifier = Modifier.size(55.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "BRUJULA",
                    color = if(backgroundColor == Color.Black) Color.White else Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }


            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .width(300.dp)
                        .height(600.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF0E6E2E)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        compassView()
                        Spacer(modifier = Modifier.height(16.dp))
                        val directionFormat =  "%.2f".format(direction)
                        Text(
                            text = "Dirección: ${directionFormat}°\nPunto: $cardinalPoint",
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }

                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewBrujulaScreen() {
   // BrujulaScreen()
}
