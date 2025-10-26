package com.example.mediturn.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mediturn.ui.screens.*
import com.example.mediturn.util.Destination

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Destination.HOME
    ) {
        // Pantallas principales con BottomBar
        composable(Destination.HOME) {
            HomeScreen(navController = navController)
        }

        composable(Destination.SEARCH) {
            SearchScreen(navController = navController)
        }

        composable(Destination.APPOINTMENTS) {
            AppointmentsScreen(navController = navController)
        }

        composable(Destination.PROFILE) {
            ProfileScreen(navController = navController)
        }

        // Pantallas secundarias
        composable(
            route = Destination.DOCTOR_DETAIL,
            arguments = listOf(navArgument("doctorId") { type = NavType.StringType })
        ) { backStackEntry ->
            val doctorId = backStackEntry.arguments?.getString("doctorId") ?: ""
            DoctorDetailScreen(navController = navController, doctorId = doctorId)
        }

        composable(
            route = Destination.SCHEDULE_APPOINTMENT,
            arguments = listOf(navArgument("doctorId") { type = NavType.StringType })
        ) { backStackEntry ->
            val doctorId = backStackEntry.arguments?.getString("doctorId") ?: ""
            ScheduleAppointmentScreen(navController = navController, doctorId = doctorId)
        }

        composable(Destination.EDIT_PROFILE) {
            EditProfileScreen(navController = navController)
        }

        composable(Destination.NOTIFICATIONS) {
            NotificationsScreen(navController = navController)
        }
    }
}
