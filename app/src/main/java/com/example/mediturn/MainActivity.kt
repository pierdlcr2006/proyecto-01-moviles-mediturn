package com.example.mediturn

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
            MediTurnTheme {
                MediTurnApp(viewModel = mediturnViewModel)
            }
        }
    }
}

@Composable
fun MediTurnApp(viewModel: MediturnViewModel) {
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
                viewModel = viewModel
            )
        }
    }
}
