package com.example.mediturn.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "doctors",
    foreignKeys = [
        ForeignKey(
            entity = EspecialityEntity::class,
            parentColumns = ["id"],
            childColumns = ["specialtyId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DoctorEntity(
    @PrimaryKey
    val id: String,
    val fullName: String,
    val specialtyId: String,
    val hospital: String,
    val location: String,
    val rating: Double,
    val profileImageUrl: String,
    val about: String,
    val phone: String,
    val availability: List<DayAvailability>,
    val hasTeleconsultation: Boolean
)

data class DayAvailability(
    val day: DayOfWeek,
    val startTime: String, // e.g., "09:00" formato 24h
    val endTime: String // e.g., "17:00" formato 24h
)

enum class DayOfWeek {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;
    
    fun toSpanish(): String = when(this) {
        MONDAY -> "Lunes"
        TUESDAY -> "Martes"
        WEDNESDAY -> "Miércoles"
        THURSDAY -> "Jueves"
        FRIDAY -> "Viernes"
        SATURDAY -> "Sábado"
        SUNDAY -> "Domingo"
    }
}
