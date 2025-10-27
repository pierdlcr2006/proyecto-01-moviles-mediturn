package com.example.mediturn.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mediturn.ui.MediturnViewModel
import com.example.mediturn.ui.screens.AppointmentsScreen
import com.example.mediturn.ui.screens.DoctorDetailScreen
import com.example.mediturn.ui.screens.EditProfileScreen
import com.example.mediturn.ui.screens.HomeScreen
import com.example.mediturn.ui.screens.NotificationsScreen
import com.example.mediturn.ui.screens.ProfileScreen
import com.example.mediturn.ui.screens.ScheduleAppointmentScreen
import com.example.mediturn.ui.screens.SearchScreen
import com.example.mediturn.ui.screens.SpecialtiesScreen
import com.example.mediturn.util.Destination

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: MediturnViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Destination.HOME
    ) {
        composable(Destination.HOME) {
            HomeScreen(
                viewModel = viewModel,
                onNotificationsClick = { navController.navigate(Destination.NOTIFICATIONS) },
                onSearchClick = { navController.navigate(Destination.SEARCH) },
                onSpecialtiesClick = { navController.navigate(Destination.SPECIALTIES) },
                onDoctorClick = { doctorId ->
                    navController.navigate(Destination.doctorDetail(doctorId.toString()))
                }
            )
        }

        composable(Destination.SEARCH) {
            SearchScreen(
                navController = navController,
                viewModel = viewModel,
                onDoctorClick = { doctorId ->
                    navController.navigate(Destination.doctorDetail(doctorId.toString()))
                }
            )
        }

        composable(Destination.APPOINTMENTS) {
            AppointmentsScreen(
                navController = navController,
                viewModel = viewModel,
                onScheduleClick = { doctorId ->
                    navController.navigate(Destination.scheduleAppointment(doctorId.toString()))
                }
            )
        }

        composable(Destination.PROFILE) {
            ProfileScreen(
                navController = navController,
                viewModel = viewModel,
                onNotificationsClick = { navController.navigate(Destination.NOTIFICATIONS) }
            )
        }

        composable(
            route = Destination.DOCTOR_DETAIL,
            arguments = listOf(navArgument("doctorId") { type = NavType.StringType })
        ) { backStackEntry ->
            val doctorId = backStackEntry.arguments?.getString("doctorId") ?: ""
            DoctorDetailScreen(
                navController = navController,
                doctorId = doctorId,
                viewModel = viewModel,
                onScheduleAppointment = {
                    navController.navigate(Destination.scheduleAppointment(doctorId))
                }
            )
        }

        composable(
            route = Destination.SCHEDULE_APPOINTMENT,
            arguments = listOf(navArgument("doctorId") { type = NavType.StringType })
        ) { backStackEntry ->
            val doctorId = backStackEntry.arguments?.getString("doctorId") ?: ""
            ScheduleAppointmentScreen(
                navController = navController,
                doctorId = doctorId,
                viewModel = viewModel
            )
        }

        composable(Destination.SPECIALTIES) {
            SpecialtiesScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(Destination.EDIT_PROFILE) {
            EditProfileScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(Destination.NOTIFICATIONS) {
            NotificationsScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}
