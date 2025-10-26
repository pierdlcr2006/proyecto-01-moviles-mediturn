package com.example.mediturn.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Data class para notificaciones
data class NotificationData(
    val id: String,
    val title: String,
    val time: String,
    val type: NotificationType,
    val isRead: Boolean = false
)

enum class NotificationType {
    APPOINTMENT, // Cita con doctor
    REMINDER,    // Recordatorio de medicamento
    FOLLOWUP     // Consulta de seguimiento
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(navController: NavController) {
    // TODO: Obtener notificaciones reales desde ViewModel
    var notifications by remember {
        mutableStateOf(
            listOf(
                NotificationData(
                    id = "1",
                    title = "Cita con Dr. Ramírez mañana a las 10:00 a.m.",
                    time = "Hace 2 horas",
                    type = NotificationType.APPOINTMENT,
                    isRead = false
                ),
                NotificationData(
                    id = "2",
                    title = "Recordatorio: Tomar medicamento Losartán 50mg",
                    time = "Hace 4 horas",
                    type = NotificationType.REMINDER,
                    isRead = false
                ),
                NotificationData(
                    id = "3",
                    title = "Consulta de seguimiento con Dra. González el viernes",
                    time = "Ayer",
                    type = NotificationType.FOLLOWUP,
                    isRead = true
                ),
                NotificationData(
                    id = "4",
                    title = "Consulta de seguimiento con Dra. González el viernes",
                    time = "Ayer",
                    type = NotificationType.FOLLOWUP,
                    isRead = true
                ),
                NotificationData(
                    id = "5",
                    title = "Consulta de seguimiento con Dra. González el viernes",
                    time = "Ayer",
                    type = NotificationType.FOLLOWUP,
                    isRead = true
                ),
                NotificationData(
                    id = "6",
                    title = "Consulta de seguimiento con Dra. González el viernes",
                    time = "Ayer",
                    type = NotificationType.FOLLOWUP,
                    isRead = true
                ),
                NotificationData(
                    id = "7",
                    title = "Cita con Dr. Ramírez mañana a las 10:00 a.m.",
                    time = "Hace 2 horas",
                    type = NotificationType.APPOINTMENT,
                    isRead = true
                )
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
    ) {
        // Header
        TopAppBar(
            title = {
                Text(
                    text = "Notificaciones",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF212121)
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color(0xFF212121)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Botón "Marcar todas como leídas"
            Button(
                onClick = {
                    notifications = notifications.map { it.copy(isRead = true) }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00BCD4)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Marcar todas como leídas",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de notificaciones
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(notifications) { notification ->
                    NotificationItem(
                        notification = notification,
                        onNotificationClick = {
                            // TODO: Navegar al detalle o marcar como leída
                            notifications = notifications.map {
                                if (it.id == notification.id) it.copy(isRead = true) else it
                            }
                        }
                    )
                }
                
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun NotificationItem(
    notification: NotificationData,
    onNotificationClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = onNotificationClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícono según el tipo de notificación
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(
                        when (notification.type) {
                            NotificationType.APPOINTMENT -> Color(0xFFB2EBF2)
                            NotificationType.REMINDER -> Color(0xFFFFE0B2)
                            NotificationType.FOLLOWUP -> Color(0xFFBBDEFB)
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when (notification.type) {
                        NotificationType.APPOINTMENT -> Icons.Filled.CalendarToday
                        NotificationType.REMINDER -> Icons.Filled.MedicalServices
                        NotificationType.FOLLOWUP -> Icons.Filled.Person
                    },
                    contentDescription = null,
                    tint = when (notification.type) {
                        NotificationType.APPOINTMENT -> Color(0xFF00BCD4)
                        NotificationType.REMINDER -> Color(0xFFFF9800)
                        NotificationType.FOLLOWUP -> Color(0xFF2196F3)
                    },
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Texto de la notificación
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = notification.title,
                    fontSize = 14.sp,
                    fontWeight = if (notification.isRead) FontWeight.Normal else FontWeight.SemiBold,
                    color = Color(0xFF212121),
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = notification.time,
                    fontSize = 12.sp,
                    color = Color(0xFF9E9E9E)
                )
            }

            // Indicador de no leída
            if (!notification.isRead) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF00BCD4))
                )
            }
        }
    }
}
