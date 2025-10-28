package com.example.mediturn.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.mediturn.data.model.Notification
import com.example.mediturn.ui.MediturnViewModel
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NotificationsScreen(
    navController: NavController,
    viewModel: MediturnViewModel
) {
    val notifications by viewModel.notifications.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.markAllNotificationsAsRead()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Notificaciones",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF212121),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
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
            actions = {
                // Spacer para equilibrar el navigationIcon y centrar el título
                Spacer(modifier = Modifier.width(48.dp))
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
        )

        if (notifications.isEmpty()) {
            EmptyNotifications()
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                // Botón "Marcar todas como leídas"
                Button(
                    onClick = { viewModel.markAllNotificationsAsRead() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00BCD4)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Marcar todas como leídas",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }

                // Lista de notificaciones
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(notifications.sortedByDescending { it.createdAt }) { notification ->
                        NotificationCard(notification = notification)
                    }

                    item { Spacer(modifier = Modifier.height(80.dp)) }
                }
            }
        }
    }
}

@Composable
private fun NotificationCard(notification: Notification) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Ícono a la izquierda
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(getNotificationIconColor(notification.title)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = getNotificationIcon(notification.title),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Contenido (título y tiempo)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = notification.message,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF212121),
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = getRelativeTime(notification.createdAt),
                    fontSize = 12.sp,
                    color = Color(0xFF9E9E9E)
                )
            }

            // Punto turquesa si no está leída
            if (!notification.isRead) {
                Spacer(modifier = Modifier.width(8.dp))
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

@Composable
private fun EmptyNotifications() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No tienes notificaciones",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Te avisaremos cuando haya algo nuevo.",
            fontSize = 14.sp,
            color = Color(0xFF757575)
        )
    }
}

private fun getNotificationIcon(title: String): androidx.compose.ui.graphics.vector.ImageVector {
    return when {
        title.contains("cita", ignoreCase = true) || 
        title.contains("appointment", ignoreCase = true) -> Icons.Filled.CalendarToday
        title.contains("medicamento", ignoreCase = true) || 
        title.contains("medicina", ignoreCase = true) ||
        title.contains("recordatorio", ignoreCase = true) -> Icons.Filled.MedicalServices
        title.contains("doctor", ignoreCase = true) || 
        title.contains("consulta", ignoreCase = true) -> Icons.Filled.Person
        else -> Icons.Filled.CalendarToday
    }
}

private fun getNotificationIconColor(title: String): Color {
    return when {
        title.contains("cita", ignoreCase = true) || 
        title.contains("appointment", ignoreCase = true) -> Color(0xFF00BCD4) // Turquesa
        title.contains("medicamento", ignoreCase = true) || 
        title.contains("medicina", ignoreCase = true) ||
        title.contains("recordatorio", ignoreCase = true) -> Color(0xFFFF9800) // Naranja
        title.contains("doctor", ignoreCase = true) || 
        title.contains("consulta", ignoreCase = true) -> Color(0xFF00BCD4) // Turquesa
        else -> Color(0xFF00BCD4)
    }
}

private fun getRelativeTime(timestamp: Long): String {
    val now = Instant.now()
    val then = Instant.ofEpochMilli(timestamp)
    val duration = Duration.between(then, now)
    
    return when {
        duration.toMinutes() < 1 -> "Justo ahora"
        duration.toMinutes() < 60 -> "Hace ${duration.toMinutes()} minutos"
        duration.toHours() < 24 -> "Hace ${duration.toHours()} horas"
        duration.toDays() == 1L -> "Ayer"
        duration.toDays() < 7 -> "Hace ${duration.toDays()} días"
        else -> {
            val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
            then.atZone(ZoneId.systemDefault()).format(formatter)
        }
    }
}
