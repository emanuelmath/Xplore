package com.example.xplore.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.xplore.ui.navigation.Screen

@Composable
fun XploreBottomBar(
    navController: NavHostController,
    isDarkMode: Boolean = false,
    backgroundColor: Color = Color.White
) {
    val items = listOf(
        Screen.Tutorial,
        Screen.Home,
        Screen.Settings
    )

    NavigationBar(
        containerColor = if (isDarkMode) Color.White else Color.Black,
        tonalElevation = 6.dp,
        modifier = Modifier
            .height(150.dp)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { screen ->
            val isSelected = currentRoute == screen.route

            NavigationBarItem(
                icon = {
                    Image(
                        painter = painterResource(
                            id = if (isDarkMode) screen.iconLight else screen.iconDark
                        ),
                        contentDescription = screen.title,
                        modifier = Modifier
                            .size(if (isSelected) 40.dp else 30.dp)
                    )
                },
                label = {
                    Text(
                        text = screen.title,
                        color = if (isSelected)
                            (if (isDarkMode) Color.Black else Color.White)
                        else
                            Color.Gray
                    )
                },
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navController.navigate(screen.route) {
                            popUpTo(Screen.Home.route)
                            launchSingleTop = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = if (isDarkMode)
                        Color(0xFF333333)
                    else
                        Color(0xFFE0E0E0)
                )
            )
        }
    }
}


