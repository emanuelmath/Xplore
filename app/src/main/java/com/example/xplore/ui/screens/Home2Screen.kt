package com.example.xplore.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.xplore.R

@Preview
@Composable
fun Home2Screen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 20.dp, vertical = 40.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxSize()
        ) {

            // Logo y título
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.xplorelogo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(55.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "HERRAMIENTAS",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
            }

            Spacer(modifier = Modifier.height(70.dp))


            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                ToolCard(
                    title = "BRUJULA",
                    subtitle = "NORTE",
                    backgroundColor = Color(0xFF0E6E2E),
                    icon = R.drawable.brujula,
                    modifier = Modifier
                        .weight(1f)
                        .height(190.dp),
                    verticalLayout = true
                )

                ToolCard(
                    title = "CLIMA",
                    subtitle = "25.6 °C",
                    backgroundColor = Color(0xFF263C5C),
                    icon = R.drawable.clima,
                    modifier = Modifier
                        .weight(1f)
                        .height(190.dp),
                    verticalLayout = true
                )
            }

            Spacer(modifier = Modifier.height(50.dp))


            ToolCard(
                title = "DETECTOR DE\nAPROXIMACION",
                subtitle = "",
                backgroundColor = Color(0xFF263C5C),
                icon = R.drawable.aproximacion,
                modifier = Modifier.height(150.dp),
                verticalLayout = false
            )

            Spacer(modifier = Modifier.height(20.dp))

            ToolCard(
                title = "PANTALLA\nADAPTIVA",
                subtitle = "",
                backgroundColor = Color(0xFF0E6E2E),
                icon = R.drawable.luz,
                modifier = Modifier.height(150.dp),
                verticalLayout = false
            )
        }
    }
}

@Composable
fun ToolCard(
    title: String,
    subtitle: String,
    backgroundColor: Color,
    icon: Int,
    modifier: Modifier = Modifier,
    verticalLayout: Boolean
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        if (verticalLayout) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = title,
                    modifier = Modifier.size(100.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    lineHeight = 28.sp,
                    textAlign = TextAlign.Center
                )
                if (subtitle.isNotEmpty()) {
                    Text(
                        text = subtitle,
                        color = Color.White,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = title,
                    modifier = Modifier.size(100.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = title,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        lineHeight = 24.sp,
                        textAlign = TextAlign.Center
                    )
                    if (subtitle.isNotEmpty()) {
                        Text(
                            text = subtitle,
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}
