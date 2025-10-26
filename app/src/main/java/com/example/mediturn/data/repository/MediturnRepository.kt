package com.example.mediturn.data.repository

import com.example.mediturn.data.dao.AppointmentDao
import com.example.mediturn.data.dao.DoctorDao
import com.example.mediturn.data.dao.NotificationDao
import com.example.mediturn.data.dao.PatientDao
import com.example.mediturn.data.dao.SpecialtyDao
import com.example.mediturn.data.model.Appointment
import com.example.mediturn.data.model.AppointmentStatus
import com.example.mediturn.data.model.DayAvailability
import com.example.mediturn.data.model.DoctorEntity
import com.example.mediturn.data.model.EspecialityEntity
import com.example.mediturn.data.model.Notification
import com.example.mediturn.data.model.PatientEntity
import com.example.mediturn.data.model.TimeSlot
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class MediturnRepository(
    private val patientDao: PatientDao,
    private val doctorDao: DoctorDao,
    private val specialtyDao: SpecialtyDao,
    private val appointmentDao: AppointmentDao,
    private val notificationDao: NotificationDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun seedDatabaseIfEmpty(): SeedResult = withContext(dispatcher) {
        val existingPatients = patientDao.count()
        val existingSpecialties = specialtyDao.count()
        val existingDoctors = doctorDao.count()
        val existingAppointments = appointmentDao.count()
        val existingNotifications = notificationDao.count()

        if (
            existingPatients > 0 &&
            existingSpecialties > 0 &&
            existingDoctors > 0 &&
            existingAppointments > 0 &&
            existingNotifications > 0
        ) {
            return@withContext SeedResult.AlreadySeeded
        }

        val patientId = seedPatients()
        val specialties = seedSpecialties()
        val doctors = seedDoctors(specialties)
        seedAppointments(patientId, doctors)
        seedNotifications(patientId, doctors)

        SeedResult.Seeded(patientId = patientId)
    }

    fun observePatient(patientId: Long): Flow<PatientEntity?> =
        patientDao.observeById(patientId)

    fun observePatients(): Flow<List<PatientEntity>> = patientDao.observePatients()

    fun observeSpecialties(): Flow<List<EspecialityEntity>> = specialtyDao.observeSpecialties()

    fun observeDoctors(): Flow<List<DoctorEntity>> = doctorDao.observeDoctors()

    fun observeDoctor(doctorId: Long): Flow<DoctorEntity?> = doctorDao.observeById(doctorId)

    fun observeTopDoctors(limit: Int = 5): Flow<List<DoctorEntity>> = doctorDao.observeTopRated(limit)

    fun observeUpcomingAppointments(patientId: Long): Flow<List<Appointment>> =
        appointmentDao.observeUpcomingByPatient(patientId)

    fun observePastAppointments(patientId: Long): Flow<List<Appointment>> =
        appointmentDao.observePastByPatient(patientId)

    fun observeNotifications(patientId: Long): Flow<List<Notification>> =
        notificationDao.observeByPatient(patientId)

    fun observeUnreadNotificationsCount(): Flow<Int> = notificationDao.observeUnreadCount()

    suspend fun scheduleAppointment(
        patientId: Long,
        doctorId: Long,
        dateTimeMillis: Long
    ) = withContext(dispatcher) {
        val appointment = Appointment(
            doctorId = doctorId,
            patientId = patientId,
            dateTime = dateTimeMillis,
            status = AppointmentStatus.CONFIRMED,
            isPast = false
        )
        appointmentDao.upsert(appointment)
    }

    suspend fun updateAppointmentStatus(
        appointmentId: Long,
        status: AppointmentStatus,
        isPast: Boolean
    ) = withContext(dispatcher) {
        appointmentDao.updateStatus(appointmentId, status, isPast)
    }

    suspend fun deleteAppointment(appointmentId: Long) = withContext(dispatcher) {
        appointmentDao.deleteById(appointmentId)
    }

    suspend fun markNotificationAsRead(notificationId: Long) = withContext(dispatcher) {
        notificationDao.markAsRead(notificationId)
    }

    suspend fun markAllNotificationsAsRead(patientId: Long) = withContext(dispatcher) {
        notificationDao.markAllAsRead(patientId = patientId, doctorId = null)
    }

    private suspend fun seedPatients(): Long {
        val patient = PatientEntity(
            fullName = "María González",
            email = "maria.gonzalez@email.com",
            phone = "+51 992 555 123",
            address = "Av. Los Héroes 123, Lima",
            profileImageUrl = ""
        )
        return patientDao.upsert(patient)
    }

    private suspend fun seedSpecialties(): List<EspecialitySeed> {
        val specialties = listOf(
            EspecialityEntity(
                name = "Cardiología",
                iconUrl = "",
                description = "Especialistas en el diagnóstico y tratamiento del corazón."
            ),
            EspecialityEntity(
                name = "Dermatología",
                iconUrl = "",
                description = "Cuidado de la piel, cabello y uñas."
            ),
            EspecialityEntity(
                name = "Pediatría",
                iconUrl = "",
                description = "Atención médica para niños y adolescentes."
            ),
            EspecialityEntity(
                name = "Odontología",
                iconUrl = "",
                description = "Salud integral y estética dental."
            ),
            EspecialityEntity(
                name = "Medicina General",
                iconUrl = "",
                description = "Atención integral y preventiva."
            )
        )

        return specialties.map { specialty ->
            val id = specialtyDao.upsert(specialty)
            EspecialitySeed(id = id, name = specialty.name)
        }
    }

    private suspend fun seedDoctors(specialties: List<EspecialitySeed>): List<DoctorSeed> {
        val cardiologyId = specialties.first { it.name == "Cardiología" }.id
        val dermatologyId = specialties.first { it.name == "Dermatología" }.id
        val pediatricsId = specialties.first { it.name == "Pediatría" }.id
        val dentistryId = specialties.first { it.name == "Odontología" }.id
        val generalId = specialties.first { it.name == "Medicina General" }.id

        val doctors = listOf(
            DoctorEntity(
                name = "María",
                lastname = "González",
                specialtyId = cardiologyId,
                hospital = "Clínica Internacional",
                location = "Miraflores, Lima",
                rating = 4.9,
                profileImageUrl = "",
                about = "Cardióloga con 10 años de experiencia en prevención y tratamiento de enfermedades cardiovasculares.",
                phone = "+51 955 111 222",
                availability = listOf(
                    DayAvailability.LUNES,
                    DayAvailability.MIERCOLES,
                    DayAvailability.VIERNES
                ),
                timeSlot = listOf(
                    TimeSlot("09:00", "09:30"),
                    TimeSlot("10:30", "11:00"),
                    TimeSlot("15:00", "15:30")
                ),
                hasTeleconsultation = true
            ),
            DoctorEntity(
                name = "Carlos",
                lastname = "Ruiz",
                specialtyId = generalId,
                hospital = "Centro Médico San Rafael",
                location = "San Isidro, Lima",
                rating = 4.7,
                profileImageUrl = "",
                about = "Médico general enfocado en medicina preventiva y seguimiento de pacientes crónicos.",
                phone = "+51 955 333 444",
                availability = listOf(
                    DayAvailability.MARTES,
                    DayAvailability.JUEVES,
                    DayAvailability.SABADO
                ),
                timeSlot = listOf(
                    TimeSlot("08:30", "09:00"),
                    TimeSlot("11:00", "11:30"),
                    TimeSlot("16:00", "16:30")
                ),
                hasTeleconsultation = true
            ),
            DoctorEntity(
                name = "Ana",
                lastname = "López",
                specialtyId = dentistryId,
                hospital = "DentalCare Premium",
                location = "Santiago de Surco, Lima",
                rating = 4.6,
                profileImageUrl = "",
                about = "Odontóloga especialista en estética dental y ortodoncia.",
                phone = "+51 955 555 666",
                availability = listOf(
                    DayAvailability.MARTES,
                    DayAvailability.VIERNES
                ),
                timeSlot = listOf(
                    TimeSlot("09:00", "09:45"),
                    TimeSlot("11:30", "12:15"),
                    TimeSlot("15:30", "16:15")
                ),
                hasTeleconsultation = false
            ),
            DoctorEntity(
                name = "Isabel",
                lastname = "Fernández",
                specialtyId = dermatologyId,
                hospital = "DermaLima",
                location = "La Molina, Lima",
                rating = 4.8,
                profileImageUrl = "",
                about = "Dermatóloga especializada en tratamientos de rejuvenecimiento y cuidado de la piel.",
                phone = "+51 955 777 888",
                availability = listOf(
                    DayAvailability.LUNES,
                    DayAvailability.MIERCOLES,
                    DayAvailability.SABADO
                ),
                timeSlot = listOf(
                    TimeSlot("10:00", "10:45"),
                    TimeSlot("13:00", "13:45"),
                    TimeSlot("16:00", "16:45")
                ),
                hasTeleconsultation = true
            ),
            DoctorEntity(
                name = "Javier",
                lastname = "Torres",
                specialtyId = pediatricsId,
                hospital = "Clínica Niño Feliz",
                location = "Jesús María, Lima",
                rating = 4.9,
                profileImageUrl = "",
                about = "Pediatra apasionado por el seguimiento del desarrollo infantil y vacunación.",
                phone = "+51 955 999 000",
                availability = listOf(
                    DayAvailability.MARTES,
                    DayAvailability.JUEVES,
                    DayAvailability.SABADO
                ),
                timeSlot = listOf(
                    TimeSlot("09:30", "10:00"),
                    TimeSlot("12:00", "12:30"),
                    TimeSlot("15:30", "16:00")
                ),
                hasTeleconsultation = false
            )
        )

        return doctors.map { doctor ->
            val id = doctorDao.upsert(doctor)
            DoctorSeed(id = id, name = "${doctor.name} ${doctor.lastname}")
        }
    }

    private suspend fun seedAppointments(
        patientId: Long,
        doctors: List<DoctorSeed>
    ) {
        if (doctors.isEmpty()) return
        val now = System.currentTimeMillis()
        val upcoming = Appointment(
            doctorId = doctors[0].id,
            patientId = patientId,
            dateTime = now + TimeUnit.DAYS.toMillis(3) + TimeUnit.HOURS.toMillis(2),
            status = AppointmentStatus.CONFIRMED,
            isPast = false
        )

        val rescheduled = Appointment(
            doctorId = doctors[1].id,
            patientId = patientId,
            dateTime = now + TimeUnit.DAYS.toMillis(7) + TimeUnit.HOURS.toMillis(5),
            status = AppointmentStatus.RESCHEDULED,
            isPast = false
        )

        val past = Appointment(
            doctorId = doctors[2].id,
            patientId = patientId,
            dateTime = now - TimeUnit.DAYS.toMillis(10),
            status = AppointmentStatus.COMPLETED,
            isPast = true
        )

        appointmentDao.upsertAll(listOf(upcoming, rescheduled, past))
    }

    private suspend fun seedNotifications(
        patientId: Long,
        doctors: List<DoctorSeed>
    ) {
        if (doctors.isEmpty()) return
        val now = System.currentTimeMillis()
        val notifications = listOf(
            Notification(
                patientId = patientId,
                doctorId = doctors[0].id,
                title = "Recordatorio de cita",
                message = "Tu cita con ${doctors[0].name} es en 3 días. No olvides llegar 15 minutos antes.",
                createdAt = now - TimeUnit.HOURS.toMillis(5),
                isRead = false
            ),
            Notification(
                patientId = patientId,
                doctorId = doctors[1].id,
                title = "Resultados disponibles",
                message = "Los resultados de tus exámenes con ${doctors[1].name} ya están listos.",
                createdAt = now - TimeUnit.DAYS.toMillis(1),
                isRead = false
            ),
            Notification(
                patientId = patientId,
                doctorId = doctors[2].id,
                title = "Seguimiento post consulta",
                message = "¿Cómo te sientes luego de tu cita? Recuerda registrar tus síntomas.",
                createdAt = now - TimeUnit.DAYS.toMillis(2),
                isRead = true
            )
        )
        notificationDao.upsertAll(notifications)
    }

    data class EspecialitySeed(
        val id: Long,
        val name: String
    )

    data class DoctorSeed(
        val id: Long,
        val name: String
    )

    sealed interface SeedResult {
        data class Seeded(val patientId: Long) : SeedResult
        data object AlreadySeeded : SeedResult
    }
}
