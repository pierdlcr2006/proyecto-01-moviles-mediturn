package com.example.mediturn.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AppointmentsScreen(
    navController: NavController,
    viewModel: MediturnViewModel,
    onScheduleClick: (Long, Long?) -> Unit  // doctorId, appointmentId?
) {
    val upcomingAppointments by viewModel.upcomingAppointments.collectAsStateWithLifecycle()
    val pastAppointments by viewModel.pastAppointments.collectAsStateWithLifecycle()
    val doctors by viewModel.doctors.collectAsStateWithLifecycle()
    val specialties by viewModel.specialties.collectAsStateWithLifecycle()

    val pagerState = rememberPagerState(pageCount = { 2 })
    val coroutineScope = rememberCoroutineScope()
    val tabs = listOf("Próximas", "Pasadas")
    
    var showCancelDialog by remember { mutableStateOf(false) }
    var appointmentToCancel by remember { mutableStateOf<Long?>(null) }
    
    var showRescheduleBlockedDialog by remember { mutableStateOf(false) }

    // Diálogo de cita ya reprogramada (no se puede volver a reprogramar)
    if (showRescheduleBlockedDialog) {
        AlertDialog(
            onDismissRequest = { showRescheduleBlockedDialog = false },
            title = { Text("No se puede reprogramar") },
            text = { Text("Ya has reprogramado esta cita. Solo se permite una reprogramación por cita. Si necesitas hacer cambios, por favor contacta directamente al consultorio.") },
            confirmButton = {
                TextButton(
                    onClick = { showRescheduleBlockedDialog = false },
                    colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF00BCD4))
                ) {
                    Text("Entendido")
                }
            }
        )
    }

    // Diálogo de confirmación para cancelar
    if (showCancelDialog) {
        AlertDialog(
            onDismissRequest = { showCancelDialog = false },
            title = { Text("Cancelar cita") },
            text = { Text("¿Estás seguro de que deseas cancelar esta cita? Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        appointmentToCancel?.let { viewModel.cancelAppointment(it) }
                        showCancelDialog = false
                        appointmentToCancel = null
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFFFF6B6B))
                ) {
                    Text("Cancelar cita")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showCancelDialog = false },
                    colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF00BCD4))
                ) {
                    Text("No, mantener")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Mis Citas",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
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

        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.White,
            contentColor = Color(0xFF00BCD4),
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    color = Color(0xFF00BCD4),
                    height = 3.dp
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(
                            text = title,
                            fontSize = 16.sp,
                            fontWeight = if (pagerState.currentPage == index) FontWeight.Bold else FontWeight.Normal,
                            color = if (pagerState.currentPage == index) Color(0xFF00BCD4) else Color(0xFF757575)
                        )
                    }
                )
            }
        }

        val specialtyMap = remember(specialties) { specialties.associateBy { it.id } }
        val doctorMap = remember(doctors) { doctors.associateBy { it.id } }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> AppointmentList(
                    appointments = upcomingAppointments,
                    doctorMap = doctorMap,
                    specialtyMap = specialtyMap,
                    isUpcoming = true,
                    onSchedule = { doctorId, appointmentId ->
                        // Validar si la cita ya fue reprogramada
                        val appointment = upcomingAppointments.firstOrNull { it.id == appointmentId }
                        if (appointment != null && appointment.rescheduleCount >= 1) {
                            showRescheduleBlockedDialog = true
                        } else {
                            onScheduleClick(doctorId, appointmentId)
                        }
                    },
                    onCancel = { appointmentId ->
                        appointmentToCancel = appointmentId
                        showCancelDialog = true
                    }
                )
                1 -> AppointmentList(
                    appointments = pastAppointments,
                    doctorMap = doctorMap,
                    specialtyMap = specialtyMap,
                    isUpcoming = false,
                    onSchedule = { _, _ -> },  // No permitir reprogramar citas pasadas
                    onCancel = {}
                )
            }
        }
    }
}

@Composable
private fun AppointmentList(
    appointments: List<Appointment>,
    doctorMap: Map<Long, DoctorEntity>,
    specialtyMap: Map<Long, EspecialityEntity>,
    isUpcoming: Boolean,
    onSchedule: (Long, Long?) -> Unit,  // doctorId, appointmentId?
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
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
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
    onSchedule: (Long, Long?) -> Unit,  // doctorId, appointmentId?
    onCancel: (Long) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Avatar con color según estado
                val avatarColor = when {
                    isUpcoming -> when (appointment.status) {
                        AppointmentStatus.CONFIRMED -> Color(0xFF00BCD4)
                        AppointmentStatus.RESCHEDULED -> Color(0xFF2196F3)
                        else -> Color(0xFFAB47BC)
                    }
                    else -> Color(0xFFE0E0E0)
                }
                
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(avatarColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Doctor",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = doctor?.let { "Dr. ${it.name} ${it.lastname}" } ?: "Doctor no disponible",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF212121)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = specialtyName.ifBlank { "Especialidad pendiente" },
                                fontSize = 14.sp,
                                color = Color(0xFF757575)
                            )
                        }
                        
                        StatusBadge(status = appointment.status)
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CalendarToday,
                            contentDescription = null,
                            tint = Color(0xFF9E9E9E),
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = formatDate(appointment.dateTime),
                            fontSize = 13.sp,
                            color = Color(0xFF757575)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Filled.AccessTime,
                            contentDescription = null,
                            tint = Color(0xFF9E9E9E),
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = formatTime(appointment.dateTime),
                            fontSize = 13.sp,
                            color = Color(0xFF757575)
                        )
                    }
                }
            }

            if (isUpcoming && doctor != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { onSchedule(doctor.id, appointment.id) },  // Pasar appointmentId
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BCD4)),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Text(
                            text = "Reprogramar",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }

                    Button(
                        onClick = { onCancel(appointment.id) },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6B6B)),
                        shape = RoundedCornerShape(24.dp)
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
        AppointmentStatus.CONFIRMED -> Triple(Color(0xFFD4F4DD), Color(0xFF00A86B), "Confirmada")
        AppointmentStatus.RESCHEDULED -> Triple(Color(0xFFFFE5CC), Color(0xFFFF8C00), "Reprogramada")
        AppointmentStatus.CANCELLED -> Triple(Color(0xFFFFE5E5), Color(0xFFFF0000), "Cancelada")
        AppointmentStatus.COMPLETED -> Triple(Color(0xFFD4F4DD), Color(0xFF00A86B), "Atendida")
    }

    androidx.compose.material3.Surface(
        shape = RoundedCornerShape(12.dp),
        color = background
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = textColor,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
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


private fun formatDate(timestamp: Long): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).format(formatter)
}

private fun formatTime(timestamp: Long): String {
    val formatter = DateTimeFormatter.ofPattern("hh:mm a")
    return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).format(formatter)
}
