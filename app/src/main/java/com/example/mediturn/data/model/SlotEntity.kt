package com.example.mediturn.data.model

// Slot es una clase UI para mostrar horarios disponibles, no necesita ser entidad Room
data class SlotEntity(
    val time: String, // e.g., "10:30", "12:30", "14:00"
    val isAvailable: Boolean,
    val reason: String = "" // "No seleccionable", "Marca de acuerdo", etc.
)
