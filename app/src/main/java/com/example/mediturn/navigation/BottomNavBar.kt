package com.example.mediturn.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mediturn.util.Destination
import kotlinx.coroutines.flow.StateFlow

@Composable
fun BottomNavBar(
    navController: NavController,
    unreadNotifications: StateFlow<Int>
) {
    val items = listOf(
        BottomNavItem("Inicio", Destination.HOME, Icons.Filled.Home),
        BottomNavItem("Buscar", Destination.SEARCH, Icons.Filled.Search),
        BottomNavItem("Citas", Destination.APPOINTMENTS, Icons.Filled.CalendarToday),
        BottomNavItem("Perfil", Destination.PROFILE, Icons.Filled.Person)
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val unread by unreadNotifications.collectAsStateWithLifecycle()

    NavigationBar(
        containerColor = Color.White,
        contentColor = Color(0xFF00BCD4)
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    if (item.route == Destination.PROFILE && unread > 0) {
                        BadgedBox(
                            badge = {
                                Badge(containerColor = Color(0xFFFF3D00)) {
                                    Text(text = unread.coerceAtMost(9).toString(), color = Color.White)
                                }
                            }
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title
                            )
                        }
                    } else {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                        )
                    }
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
                    selectedIconColor = Color(0xFF00BCD4),
                    selectedTextColor = Color(0xFF00BCD4),
                    unselectedIconColor = Color(0xFF9E9E9E),
                    unselectedTextColor = Color(0xFF9E9E9E),
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}
