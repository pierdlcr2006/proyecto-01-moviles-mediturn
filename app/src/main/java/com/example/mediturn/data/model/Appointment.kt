package com.example.mediturn.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "appointments",
    foreignKeys = [
        ForeignKey(
            entity = DoctorEntity::class,
            parentColumns = ["id"],
            childColumns = ["doctorId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PatientEntity::class,
            parentColumns = ["id"],
            childColumns = ["patientId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Appointment(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val doctorId: Long,
    val patientId: Long,
    val dateTime: Long, // Timestamp en milisegundos
    val status: AppointmentStatus,
    val isPast: Boolean
)

enum class AppointmentStatus {
    CONFIRMED,
    RESCHEDULED,
    CANCELLED,
    COMPLETED
}
