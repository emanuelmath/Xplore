package com.example.xplore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.xplore.ui.navigation.Screen
import com.example.xplore.ui.navigation.XploreNavHost
import com.example.xplore.ui.theme.XploreTheme
import com.example.xplore.ui.viewmodels.MainViewModel
import com.example.xplore.ui.viewmodels.MainViewModelFactory

class MainActivity : ComponentActivity() {
    lateinit var mainViewModel: MainViewModel
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        mainViewModel = ViewModelProvider(
            this,
            MainViewModelFactory(this))[MainViewModel::class.java]


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            XploreTheme {
                XploreMainScreen(mainViewModel)
            }
        }
    }
}

@Composable
fun XploreMainScreen(mainViewModel: MainViewModel) {
    val navController = rememberNavController()
    XploreNavHost(navController, Screen.Home.route, mainViewModel)
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    XploreTheme {
        Greeting("Android")
    }
}