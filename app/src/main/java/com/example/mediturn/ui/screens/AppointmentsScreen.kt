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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mediturn.data.model.Appointment
import com.example.mediturn.data.model.AppointmentStatus
import com.example.mediturn.data.model.DoctorEntity
import com.example.mediturn.data.model.EspecialityEntity
import com.example.mediturn.ui.MediturnViewModel
import com.example.mediturn.util.Destination
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AppointmentsScreen(
    navController: NavController,
    viewModel: MediturnViewModel,
    onScheduleClick: (Long) -> Unit
) {
    val upcomingAppointments by viewModel.upcomingAppointments.collectAsStateWithLifecycle()
    val pastAppointments by viewModel.pastAppointments.collectAsStateWithLifecycle()
    val doctors by viewModel.doctors.collectAsStateWithLifecycle()
    val specialties by viewModel.specialties.collectAsStateWithLifecycle()

    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Próximas", "Pasadas")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
    ) {
        TopAppBar(
            title = {
                BoxFilledCenter {
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
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
        )

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

        val specialtyMap = remember(specialties) { specialties.associateBy { it.id } }
        val doctorMap = remember(doctors) { doctors.associateBy { it.id } }

        when (selectedTab) {
            0 -> AppointmentList(
                appointments = upcomingAppointments,
                doctorMap = doctorMap,
                specialtyMap = specialtyMap,
                isUpcoming = true,
                onSchedule = onScheduleClick,
                onCancel = { appointmentId -> viewModel.cancelAppointment(appointmentId) }
            )
            else -> AppointmentList(
                appointments = pastAppointments,
                doctorMap = doctorMap,
                specialtyMap = specialtyMap,
                isUpcoming = false,
                onSchedule = {},
                onCancel = {}
            )
        }
    }
}

@Composable
private fun AppointmentList(
    appointments: List<Appointment>,
    doctorMap: Map<Long, DoctorEntity>,
    specialtyMap: Map<Long, EspecialityEntity>,
    isUpcoming: Boolean,
    onSchedule: (Long) -> Unit,
    onCancel: (Long) -> Unit
) {
    if (appointments.isEmpty()) {
        EmptyAppointmentsState(isUpcoming = isUpcoming)
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(appointments, key = { it.id }) { appointment ->
            val doctor = doctorMap[appointment.doctorId]
            val specialty = doctor?.let { specialtyMap[it.specialtyId]?.name }.orEmpty()
            AppointmentCard(
                appointment = appointment,
                doctor = doctor,
                specialtyName = specialty,
                isUpcoming = isUpcoming,
                onSchedule = onSchedule,
                onCancel = onCancel
            )
        }

        item { Spacer(modifier = Modifier.height(80.dp)) }
    }
}

@Composable
private fun AppointmentCard(
    appointment: Appointment,
    doctor: DoctorEntity?,
    specialtyName: String,
    isUpcoming: Boolean,
    onSchedule: (Long) -> Unit,
    onCancel: (Long) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE0F7FA)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Doctor",
                        tint = Color(0xFF00BCD4),
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = doctor?.let { "${it.name} ${it.lastname}" } ?: "Doctor no disponible",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF212121)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = specialtyName.ifBlank { "Especialidad pendiente" },
                        fontSize = 13.sp,
                        color = Color(0xFF757575)
                    )
                }

                StatusBadge(status = appointment.status)
            }

            Spacer(modifier = Modifier.height(12.dp))

            AppointmentDateRow(label = "Fecha", icon = Icons.Filled.CalendarToday, value = formatDate(appointment.dateTime))
            Spacer(modifier = Modifier.height(6.dp))
            AppointmentDateRow(label = "Hora", icon = Icons.Filled.AccessTime, value = formatTime(appointment.dateTime))

            if (isUpcoming && doctor != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { onSchedule(doctor.id) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Reprogramar",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Button(
                        onClick = { onCancel(appointment.id) },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5252)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Cancelar",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StatusBadge(status: AppointmentStatus) {
    val (background, textColor, label) = when (status) {
        AppointmentStatus.CONFIRMED -> Triple(Color(0xFFE0F7FA), Color(0xFF00897B), "Confirmada")
        AppointmentStatus.RESCHEDULED -> Triple(Color(0xFFFFE0B2), Color(0xFFE65100), "Reprogramada")
        AppointmentStatus.CANCELLED -> Triple(Color(0xFFFFCDD2), Color(0xFFD32F2F), "Cancelada")
        AppointmentStatus.COMPLETED -> Triple(Color(0xFFE8F5E9), Color(0xFF2E7D32), "Atendida")
    }

    SurfaceRounded(background) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = textColor,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

@Composable
private fun AppointmentDateRow(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color(0xFF9E9E9E),
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = value,
            fontSize = 13.sp,
            color = Color(0xFF757575)
        )
    }
}

@Composable
private fun EmptyAppointmentsState(isUpcoming: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (isUpcoming) "No tienes citas próximas" else "Sin citas pasadas",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = if (isUpcoming) "Agenda una nueva consulta desde la pantalla Buscar." else "Cuando completes una consulta, aparecerá aquí.",
            fontSize = 14.sp,
            color = Color(0xFF757575)
        )
    }
}

@Composable
private fun BoxFilledCenter(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        content()
    }
}

@Composable
private fun SurfaceRounded(color: Color, content: @Composable () -> Unit) {
    androidx.compose.material3.Surface(
        shape = RoundedCornerShape(8.dp),
        color = color
    ) {
        content()
    }
}

private fun formatDate(timestamp: Long): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).format(formatter)
}

private fun formatTime(timestamp: Long): String {
    val formatter = DateTimeFormatter.ofPattern("hh:mm a")
    return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).format(formatter)
}
