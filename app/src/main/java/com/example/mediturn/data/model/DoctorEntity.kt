package com.example.mediturn.data.model

import androidx.room.ColumnInfo
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
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val lastname: String,
    val specialtyId: Long,
    val hospital: String,
    val location: String,
    val rating: Double,
    val profileImageUrl: String,
    val about: String,
    val phone: String,
    val availability: List<DayAvailability>,
    val timeSlot: List<TimeSlot>,
    val hasTeleconsultation: Boolean
)
enum class DayAvailability{
    LUNES,
    MARTES,
    MIERCOLES,
    JUEVES,
    VIERNES,
    SABADO,
    DOMINGO
}
