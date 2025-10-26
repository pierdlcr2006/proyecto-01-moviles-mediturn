package com.example.mediturn.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
data class Notification(
    @PrimaryKey
    val id: String,
    val title: String,
    val message: String,
    val timestamp: Long, // Timestamp en milisegundos
    val isRead: Boolean,
    val type: NotificationType
)

enum class NotificationType {
    APPOINTMENT,
    REMINDER,
    CANCELLATION,
    CONFIRMATION,
    GENERAL
}