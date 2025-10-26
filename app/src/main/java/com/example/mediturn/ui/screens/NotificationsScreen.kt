package com.example.mediturn.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.mediturn.data.model.Notification
import com.example.mediturn.ui.MediturnViewModel
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
            actions = {
                if (notifications.isNotEmpty()) {
                    TextButton(onClick = { viewModel.markAllNotificationsAsRead() }) {
                        Text(text = "Marcar leídas", color = MaterialTheme.colorScheme.primary)
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
        )

        if (notifications.isEmpty()) {
            EmptyNotifications()
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(notifications.sortedByDescending { it.createdAt }) { notification ->
                    NotificationCard(
                        notification = notification,
                        onMarkAsRead = { viewModel.markNotificationAsRead(notification.id) }
                    )
                }

                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}

@Composable
private fun NotificationCard(
    notification: Notification,
    onMarkAsRead: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (notification.isRead) Color.White else Color(0xFFE8F5E9)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = notification.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF212121)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = formatNotificationDate(notification.createdAt),
                        fontSize = 12.sp,
                        color = Color(0xFF757575)
                    )
                }
                if (!notification.isRead) {
                    TextButton(onClick = onMarkAsRead) {
                        Text(text = "Marcar leída", fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = notification.message,
                fontSize = 14.sp,
                color = Color(0xFF424242)
            )
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

private fun formatNotificationDate(timestamp: Long): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy • hh:mm a")
    return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).format(formatter)
}
