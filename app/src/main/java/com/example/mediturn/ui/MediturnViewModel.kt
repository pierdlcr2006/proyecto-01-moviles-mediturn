package com.example.mediturn.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mediturn.data.model.Appointment
import com.example.mediturn.data.model.DoctorEntity
import com.example.mediturn.data.model.EspecialityEntity
import com.example.mediturn.data.model.Notification
import com.example.mediturn.data.model.PatientEntity
import com.example.mediturn.data.repository.MediturnRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
class MediturnViewModel(
    private val repository: MediturnRepository
) : ViewModel() {

    val patient: Flow<PatientEntity?> = repository
        .observePatients()
        .map { patients -> patients.firstOrNull() }

    val activePatient = patient
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    val specialties = repository
        .observeSpecialties()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    val doctors = repository
        .observeDoctors()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    val topDoctors = repository
        .observeTopDoctors(limit = 5)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    private val patientIdFlow = activePatient
        .filterNotNull()
        .map { it.id }

    val upcomingAppointments = patientIdFlow
        .flatMapLatest { repository.observeUpcomingAppointments(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    val pastAppointments = patientIdFlow
        .flatMapLatest { repository.observePastAppointments(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    val notifications = patientIdFlow
        .flatMapLatest { repository.observeNotifications(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    val unreadNotifications = repository
        .observeUnreadNotificationsCount()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 0
        )

    fun doctor(doctorId: Long): Flow<DoctorEntity?> = repository.observeDoctor(doctorId)

    fun scheduleAppointment(
        doctorId: Long,
        dateTimeMillis: Long
    ) {
        val patientId = activePatient.value?.id ?: return
        viewModelScope.launch {
            repository.scheduleAppointment(
                patientId = patientId,
                doctorId = doctorId,
                dateTimeMillis = dateTimeMillis
            )
        }
    }

    fun cancelAppointment(appointmentId: Long) {
        viewModelScope.launch {
            repository.deleteAppointment(appointmentId)
        }
    }

    fun markNotificationAsRead(notificationId: Long) {
        viewModelScope.launch {
            repository.markNotificationAsRead(notificationId)
        }
    }

    fun markAllNotificationsAsRead() {
        val patientId = activePatient.value?.id ?: return
        viewModelScope.launch {
            repository.markAllNotificationsAsRead(patientId)
        }
    }

    fun updatePatientProfile(
        fullName: String,
        email: String,
        phone: String,
        address: String
    ) {
        val currentPatient = activePatient.value ?: return
        viewModelScope.launch {
            repository.updatePatient(
                currentPatient.copy(
                    fullName = fullName,
                    email = email,
                    phone = phone,
                    address = address
                )
            )
        }
    }
}

class MediturnViewModelFactory(
    private val repository: MediturnRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MediturnViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MediturnViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
