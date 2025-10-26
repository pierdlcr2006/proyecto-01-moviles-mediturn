package com.example.mediturn.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patients")
data class Patient(
    @PrimaryKey
    val id: String,
    val fullName: String,
    val email: String,
    val phone: String,
    val address: String,
    val profileImageUrl: String
)
