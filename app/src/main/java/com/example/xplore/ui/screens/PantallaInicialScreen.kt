package com.example.xplore.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.xplore.R
import com.example.xplore.ui.viewmodels.SplashViewModel
import kotlinx.coroutines.delay


@Composable
fun PantallaInicialScreen(
    navController: NavController,
    splashViewModel: SplashViewModel,
    onStartClick: () -> Unit
) {
    val uiState = splashViewModel.uiState

    LaunchedEffect(Unit) {
        delay(2000L)
        splashViewModel.getUserName()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(30.dp))

            Image(
                painter = painterResource(id = R.drawable.xplorelogo),
                contentDescription = "Logo XPLORE",
                modifier = Modifier.size(190.dp)
            )


            Text(
                text = "Bienvenido a XPLORE\n" +
                        uiState.name,
                color = Color.White,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Text(
                text = "Todo lo que necesitas, \n" +
                        "en tu bolsillo",
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))


            if(uiState.isLoading) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = onStartClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0E6E2E)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .height(55.dp)
                        .width(150.dp)
                ) {
                    Text(
                        text = "INICIAR",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }


            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PantallaInicialPreview() {
   // val nav = rememberNavController()
   // PantallaInicialScreen(nav, onStartClick = {})
}
