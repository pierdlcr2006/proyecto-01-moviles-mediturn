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
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mediturn.util.Destination

// Datos de ejemplo temporales para testing
data class AppointmentData(
    val doctorName: String,
    val specialty: String,
    val date: String,
    val time: String,
    val status: String,
    val isPast: Boolean,
    val appointmentId: String,
    val photoBackgroundColor: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentsScreen(
    navController: NavController,
    onBack: () -> Unit = {},
    onRescheduleAppointment: (String) -> Unit = {},
    onCancelAppointment: (String) -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Próximas", "Pasadas")
    
    // TODO: Recibir estos datos desde ViewModel/Repository
    // val upcomingAppointments by viewModel.upcomingAppointments.collectAsState()
    // val pastAppointments by viewModel.pastAppointments.collectAsState()

    // Datos de ejemplo temporales para testing
    val upcomingAppointments = listOf(
        AppointmentData("Dr. María González", "Cardiología", "15 Nov 2025", "10:30 AM", "CONFIRMADA", false, "1", Color(0xFF4DD0E1)),
        AppointmentData("Dr. Carlos Ruiz", "Medicina General", "18 Nov 2025", "2:00 PM", "REPROGRAMADA", false, "2", Color(0xFF42A5F5)),
        AppointmentData("Dra. Ana López", "Odontología", "22 Nov 2025", "4:15 PM", "CONFIRMADA", false, "3", Color(0xFFAB47BC))
    )

    val pastAppointments = listOf(
        AppointmentData("Dr. Ana García", "Cardiología", "15 Oct 2025", "10:30 AM", "ATENDIDA", true, "5", Color(0xFFEC407A)),
        AppointmentData("Dr. Carlos Méndez", "Oftalmología", "08 Oct 2025", "3:15 PM", "ATENDIDA", true, "6", Color(0xFF66BB6A))
    )
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
    ) {
        // Header
        TopAppBar(
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Mis Citas",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF212121)
                    )
                }
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
        
        // TabRow
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.White,
            contentColor = Color(0xFF00BCD4),
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = Color(0xFF00BCD4),
                    height = 3.dp
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            text = title,
                            fontSize = 16.sp,
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedTab == index) Color(0xFF00BCD4) else Color(0xFF757575)
                        )
                    }
                )
            }
        }
        
        // Lista de citas
        // TODO: Reemplazar con datos reales desde ViewModel
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 20.dp)
        ) {
            val appointments = if (selectedTab == 0) upcomingAppointments else pastAppointments

            items(appointments) { appointment ->
                AppointmentCard(
                    doctorName = appointment.doctorName,
                    specialty = appointment.specialty,
                    date = appointment.date,
                    time = appointment.time,
                    status = appointment.status,
                    isPast = appointment.isPast,
                    appointmentId = appointment.appointmentId,
                    photoBackgroundColor = appointment.photoBackgroundColor,
                    onReschedule = { navController.navigate(Destination.scheduleAppointment(appointment.appointmentId)) },
                    onCancel = { /* TODO: Implementar cancelar cita */ }
                )
            }
        }
    }
}

@Composable
fun AppointmentCard(
    doctorName: String,
    specialty: String,
    date: String,
    time: String,
    status: String, // "CONFIRMADA", "REPROGRAMADA", "ATENDIDA"
    isPast: Boolean = false,
    appointmentId: String,
    photoBackgroundColor: Color = Color(0xFF4DD0E1),
    onReschedule: () -> Unit,
    onCancel: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Foto del médico (placeholder circular con ícono)
                // TODO: Reemplazar con imagen real desde URL o drawable
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(photoBackgroundColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Foto del médico",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                // Información del médico
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = doctorName,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF212121)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = specialty,
                        fontSize = 13.sp,
                        color = Color(0xFF9E9E9E)
                    )
                }
                
                // Badge de estado
                StatusBadge(status = status)
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Fecha y hora
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.CalendarToday,
                        contentDescription = "Fecha",
                        tint = Color(0xFF9E9E9E),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = date,
                        fontSize = 13.sp,
                        color = Color(0xFF757575)
                    )
                }
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.AccessTime,
                        contentDescription = "Hora",
                        tint = Color(0xFF9E9E9E),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = time,
                        fontSize = 13.sp,
                        color = Color(0xFF757575)
                    )
                }
            }
            
            // Botones (solo para citas próximas)
            if (!isPast) {
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Botón Reprogramar
                    Button(
                        onClick = onReschedule,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE0F7FA),
                            contentColor = Color(0xFF00BCD4)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Reprogramar",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    
                    // Botón Cancelar
                    Button(
                        onClick = onCancel,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF5252),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Cancelar",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatusBadge(status: String) {
    val (backgroundColor, textColor, text) = when (status.uppercase()) {
        "CONFIRMADA" -> Triple(
            Color(0xFFE0F7FA),
            Color(0xFF00897B),
            "Confirmada"
        )
        "REPROGRAMADA" -> Triple(
            Color(0xFFFFE0B2),
            Color(0xFFE65100),
            "Reprogramada"
        )
        "ATENDIDA" -> Triple(
            Color(0xFFE8F5E9),
            Color(0xFF2E7D32),
            "Atendida"
        )
        else -> Triple(
            Color(0xFFE0E0E0),
            Color(0xFF757575),
            status
        )
    }
    
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = backgroundColor
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
    }
}
