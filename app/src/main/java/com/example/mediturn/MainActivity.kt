package com.example.mediturn

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.mediturn.navigation.BottomNavBar
import com.example.mediturn.navigation.NavGraph
import com.example.mediturn.ui.MediturnViewModel
import com.example.mediturn.ui.MediturnViewModelFactory
import com.example.mediturn.ui.theme.MediTurnTheme
import com.example.mediturn.util.Destination

class MainActivity : ComponentActivity() {

    private val mediturnViewModel: MediturnViewModel by viewModels {
        val app = application as MediturnApplication
        MediturnViewModelFactory(app.repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Obtener preferencia de tema desde SharedPreferences
            val sharedPrefs = getSharedPreferences("mediturn_prefs", Context.MODE_PRIVATE)
            var isDarkMode by remember { mutableStateOf(sharedPrefs.getBoolean("dark_mode", false)) }

            MediTurnTheme(darkTheme = isDarkMode) {
                MediTurnApp(
                    viewModel = mediturnViewModel,
                    isDarkMode = isDarkMode,
                    onThemeChange = { newDarkMode ->
                        isDarkMode = newDarkMode
                        sharedPrefs.edit().putBoolean("dark_mode", newDarkMode).apply()
                    }
                )
            }
        }
    }
}

@Composable
fun MediTurnApp(
    viewModel: MediturnViewModel,
    isDarkMode: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    // Mostrar bottom bar solo en HOME
    val showBottomBar = currentRoute == Destination.HOME

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(
                    navController = navController,
                    unreadNotifications = viewModel.unreadNotifications
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavGraph(
                navController = navController,
                viewModel = viewModel,
                isDarkMode = isDarkMode,
                onThemeChange = onThemeChange
            )
        }
    }
}
