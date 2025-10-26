package com.example.mediturn.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "specialties")
data class EspecialityEntity(
    @PrimaryKey
    val id: String,
    val name: String, // e.g., "Cardiología", "Dermatología", "Pediatría"
    val iconUrl: String,
    val description: String
)
