package com.example.mediturn.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mediturn.util.Destination

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        BottomNavItem("Inicio", Destination.HOME, Icons.Filled.Home),
        BottomNavItem("Buscar", Destination.SEARCH, Icons.Filled.Search),
        BottomNavItem("Citas", Destination.APPOINTMENTS, Icons.Filled.CalendarToday),
        BottomNavItem("Perfil", Destination.PROFILE, Icons.Filled.Person)
    )
    
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    NavigationBar(
        containerColor = Color.White,
        contentColor = Color(0xFF00BCD4) // Color turquesa/teal
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(text = item.title)
                },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF00BCD4), // Turquesa para icono seleccionado
                    selectedTextColor = Color(0xFF00BCD4), // Turquesa para texto seleccionado
                    unselectedIconColor = Color(0xFF9E9E9E), // Gris para icono no seleccionado
                    unselectedTextColor = Color(0xFF9E9E9E), // Gris para texto no seleccionado
                    indicatorColor = Color.Transparent // Sin fondo en el item seleccionado
                )
            )
        }
    }
}
