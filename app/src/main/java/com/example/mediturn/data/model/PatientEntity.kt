package com.example.mediturn.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patients")
data class PatientEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val fullName: String,
    val email: String,
    val phone: String,
    val address: String,
    val profileImageUrl: String
)
