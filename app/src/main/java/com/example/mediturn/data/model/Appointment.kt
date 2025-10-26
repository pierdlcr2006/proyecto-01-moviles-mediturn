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
            entity = Patient::class,
            parentColumns = ["id"],
            childColumns = ["patientId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Appointment(
    @PrimaryKey
    val id: String,
    val doctorId: String,
    val patientId: String,
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
