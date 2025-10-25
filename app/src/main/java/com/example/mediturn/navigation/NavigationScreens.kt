package com.example.mediturn.navigation

import androidx.navigation.NavController
import com.example.mediturn.util.Destination

// Funciones de ayuda para navegación
fun NavController.navigateToHome() {
    navigate(Destination.HOME) {
        popUpTo(graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavController.navigateToSearch() {
    navigate(Destination.SEARCH) {
        popUpTo(graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavController.navigateToAppointments() {
    navigate(Destination.APPOINTMENTS) {
        popUpTo(graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavController.navigateToProfile() {
    navigate(Destination.PROFILE) {
        popUpTo(graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavController.navigateToDoctorDetail(doctorId: String) {
    navigate(Destination.doctorDetail(doctorId))
}

fun NavController.navigateToScheduleAppointment(doctorId: String) {
    navigate(Destination.scheduleAppointment(doctorId))
}

fun NavController.navigateToNotifications() {
    navigate(Destination.NOTIFICATIONS)
}
