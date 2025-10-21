package com.example.xplore

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.xplore.ui.components.XploreBottomBar
import com.example.xplore.ui.navigation.Screen
import com.example.xplore.ui.navigation.XploreNavHost
import com.example.xplore.ui.theme.XploreTheme
import com.example.xplore.ui.viewmodels.ConfigurationViewModel
import com.example.xplore.ui.viewmodels.MainViewModel
import com.example.xplore.ui.viewmodels.*
import com.example.xplore.ui.viewmodels.SplashViewModel
import com.example.xplore.ui.viewmodels.SplashViewModelFactory



class MainActivity : ComponentActivity() {
    lateinit var mainViewModel: MainViewModel
        private set

    lateinit var splashViewModel: SplashViewModel
        private set

    lateinit var configurationViewModel: ConfigurationViewModel
       private set

    override fun onCreate(savedInstanceState: Bundle?) {
        mainViewModel = ViewModelProvider(
            this,
            MainViewModelFactory(this))[MainViewModel::class.java]
        splashViewModel = ViewModelProvider(
            this,
            SplashViewModelFactory(this))[SplashViewModel::class.java]
        configurationViewModel = ViewModelProvider(
            this,
            ConfigurationViewModelFactory(this))[ConfigurationViewModel::class.java]



        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        enableEdgeToEdge()

        setContent {
            XploreTheme {
                XploreMainScreen(mainViewModel, splashViewModel, configurationViewModel)
            }
        }
    }
}

@Composable
fun XploreMainScreen(mainViewModel: MainViewModel, splashViewModel: SplashViewModel, configurationViewModel: ConfigurationViewModel) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val uiState = mainViewModel.uiState
    val isDarkMode = when {
        uiState.optionLightDarkMode == true && uiState.isManualDarkModeOn == true -> true
        uiState.optionLightDarkMode == true && uiState.isManualDarkModeOn == false -> false

        uiState.optionLightDarkMode == false && uiState.light?.currentState == "Dark Mode" -> true
        uiState.optionLightDarkMode == false && uiState.light?.currentState == "Light Mode" -> false

        else -> false
    }


    val backgroundColor = if (isDarkMode) Color.Black else Color.White
    val textColor = if (isDarkMode) Color.White else Color.Black


    val showBottomBar = when (currentRoute) {
        Screen.Splash.route -> false//, Screen.Dialog.route -> false
        else -> true
    }

    val bottomBarColor = if (isDarkMode) Color.Black else Color.White

    Scaffold(
        containerColor = if (isDarkMode) Color.Black else Color.White,
        bottomBar = {
            if (showBottomBar) {
                XploreBottomBar(
                    navController = navController,
                    isDarkMode = isDarkMode,
                    backgroundColor = bottomBarColor
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        ) {
            XploreNavHost(
                navHostController = navController,
                startDestination = Screen.Splash.route,
                mainViewModel = mainViewModel,
                splashViewModel = splashViewModel,
                configurationViewModel = configurationViewModel,
                backgroundColor = backgroundColor,
                textColor = textColor
            )
        }
    }
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