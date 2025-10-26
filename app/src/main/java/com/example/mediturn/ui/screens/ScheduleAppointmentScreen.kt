package com.example.mediturn.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.mediturn.data.model.TimeSlot
import com.example.mediturn.ui.MediturnViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ScheduleAppointmentScreen(
    navController: NavController,
    doctorId: String,
    viewModel: MediturnViewModel
) {
    val doctorIdLong = doctorId.toLongOrNull() ?: -1L
    val doctor by viewModel.doctor(doctorIdLong).collectAsStateWithLifecycle(initialValue = null)

    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedSlot by remember { mutableStateOf<TimeSlot?>(null) }
    val availableDates = remember { (0..6).map { LocalDate.now().plusDays(it.toLong()) } }

    LaunchedEffect(doctor) {
        selectedSlot = doctor?.timeSlot?.firstOrNull()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Agendar Cita",
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
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
        )

        if (doctor == null) {
            EmptyScheduleState()
            return
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            DoctorSummaryCard(doctorName = "${doctor!!.name} ${doctor!!.lastname}", specialty = "${doctor!!.hospital}")

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Selecciona una fecha",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF212121)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                availableDates.forEach { date ->
                    val isSelected = selectedDate == date
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { selectedDate = date },
                        color = if (isSelected) Color(0xFF00BCD4) else Color.White,
                        tonalElevation = if (isSelected) 4.dp else 0.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 14.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = dateFormatter.format(date),
                                fontWeight = FontWeight.SemiBold,
                                color = if (isSelected) Color.White else Color(0xFF212121)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = dayFormatter.format(date),
                                fontSize = 12.sp,
                                color = if (isSelected) Color.White.copy(alpha = 0.85f) else Color(0xFF757575)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Elige un horario",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF212121)
            )

            Spacer(modifier = Modifier.height(12.dp))

            doctor!!.timeSlot.chunked(2).forEach { rowSlots ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowSlots.forEach { slot ->
                        val isSelected = selectedSlot == slot
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { selectedSlot = slot },
                            color = if (isSelected) Color(0xFFE0F7FA) else Color.White,
                            tonalElevation = if (isSelected) 2.dp else 0.dp
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 14.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = slot.start,
                                    fontWeight = FontWeight.Medium,
                                    color = if (isSelected) Color(0xFF00838F) else Color(0xFF212121)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "${slot.start} - ${slot.end}",
                                    fontSize = 12.sp,
                                    color = Color(0xFF757575)
                                )
                            }
                        }
                    }
                    if (rowSlots.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            SummaryCard(
                doctorName = "${doctor!!.name} ${doctor!!.lastname}",
                date = selectedDate,
                slot = selectedSlot
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val slot = selectedSlot ?: return@Button
                    val dateTime = LocalDateTime.of(selectedDate, LocalTime.parse(slot.start))
                    val millis = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
                    viewModel.scheduleAppointment(
                        doctorId = doctorIdLong,
                        dateTimeMillis = millis
                    )
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = selectedSlot != null,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BCD4)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Reservar cita",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Lock,
                    contentDescription = null,
                    tint = Color(0xFF00BCD4),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Tus datos están protegidos con MediTurn",
                    fontSize = 12.sp,
                    color = Color(0xFF757575)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun DoctorSummaryCard(doctorName: String, specialty: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFB2EBF2)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Doctor",
                    tint = Color(0xFF00BCD4),
                    modifier = Modifier.size(30.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = doctorName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = specialty,
                    fontSize = 14.sp,
                    color = Color(0xFF757575)
                )
            }
        }
    }
}

@Composable
private fun SummaryCard(
    doctorName: String,
    date: LocalDate,
    slot: TimeSlot?
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7FA)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.EventNote,
                    contentDescription = null,
                    tint = Color(0xFF00BCD4)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Resumen de la cita",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00838F)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            SummaryRow(label = "Doctor", value = doctorName)
            Spacer(modifier = Modifier.height(8.dp))
            SummaryRow(label = "Fecha", value = dateFormatter.format(date))
            Spacer(modifier = Modifier.height(8.dp))
            SummaryRow(label = "Hora", value = slot?.start ?: "Selecciona un horario")
            Spacer(modifier = Modifier.height(8.dp))
            SummaryRow(label = "Duración", value = "30 minutos")
        }
    }
}

@Composable
private fun SummaryRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label:",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF00838F)
        )
        Text(
            text = value,
            fontSize = 14.sp,
            color = Color(0xFF00838F)
        )
    }
}

@Composable
private fun EmptyScheduleState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No se encontró la información del doctor",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Regresa y selecciona nuevamente el doctor.",
            fontSize = 14.sp,
            color = Color(0xFF757575)
        )
    }
}

private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM")
private val dayFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("EEE")
