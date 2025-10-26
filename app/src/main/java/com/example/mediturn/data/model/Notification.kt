package com.example.mediturn.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "notifications",
    foreignKeys = [
        ForeignKey(
            entity = PatientEntity::class,
            parentColumns = ["id"],
            childColumns = ["patientId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = DoctorEntity::class,
            parentColumns = ["id"],
            childColumns = ["doctorId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Notification(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val patientId: Long?,
    val doctorId: Long?,
    val title: String,
    val message: String,
    val createdAt: Long,
    val isRead: Boolean = false
)
