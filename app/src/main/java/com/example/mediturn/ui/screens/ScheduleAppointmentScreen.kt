package com.example.mediturn.ui.screens

import android.app.DatePickerDialog
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.mediturn.data.model.DayAvailability
import com.example.mediturn.data.model.TimeSlot
import com.example.mediturn.ui.MediturnViewModel
import com.example.mediturn.util.Destination
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ScheduleAppointmentScreen(
    navController: NavController,
    doctorId: String,
    appointmentId: String?,  // Nuevo parámetro opcional
    viewModel: MediturnViewModel
) {
    val doctorIdLong = doctorId.toLongOrNull() ?: -1L
    val appointmentIdLong = appointmentId?.toLongOrNull()
    val isRescheduling = appointmentIdLong != null
    
    val doctor by viewModel.doctor(doctorIdLong).collectAsStateWithLifecycle(initialValue = null)
    val existingAppointment by viewModel.appointment(appointmentIdLong ?: -1L)
        .collectAsStateWithLifecycle(initialValue = null)
    
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedTimeSlot by remember { mutableStateOf<TimeSlot?>(null) }
    var consultationType by remember { mutableStateOf("Presencial") } // "Presencial" o "Teleconsulta"
    var reason by remember { mutableStateOf("") }
    var isProcessing by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    
    // Cargar datos de la cita existente si es reprogramación
    LaunchedEffect(existingAppointment) {
        existingAppointment?.let { appointment ->
            val instant = java.time.Instant.ofEpochMilli(appointment.dateTime)
            val dateTime = java.time.LocalDateTime.ofInstant(instant, java.time.ZoneId.systemDefault())
            selectedDate = dateTime.toLocalDate()
            selectedTimeSlot = TimeSlot(
                start = dateTime.toLocalTime().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")),
                end = dateTime.plusMinutes(45).toLocalTime().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"))
            )
            consultationType = appointment.consultationType
            reason = appointment.reason
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
        TopAppBar(
            title = {
                Text(
                    text = if (isRescheduling) "Reprogramar Cita" else "Agendar Cita",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            actions = {
                // Spacer para equilibrar el navigationIcon y centrar el título
                Spacer(modifier = Modifier.width(48.dp))
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
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
            DoctorSummaryCard(
                doctorName = "Dr. ${doctor!!.name} ${doctor!!.lastname}",
                specialty = doctor!!.hospital
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Modalidad de consulta
            SectionHeader(
                icon = Icons.Filled.Home,
                title = "Modalidad de la consulta"
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ModalityChip(
                    text = "Presencial",
                    icon = Icons.Filled.Home,
                    selected = consultationType == "Presencial",
                    onClick = { consultationType = "Presencial" },
                    modifier = Modifier.weight(1f)
                )
                // Solo mostrar Teleconsulta si el doctor lo tiene disponible
                if (doctor!!.hasTeleconsultation) {
                    ModalityChip(
                        text = "Teleconsulta",
                        icon = Icons.Filled.Videocam,
                        selected = consultationType == "Teleconsulta",
                        onClick = { consultationType = "Teleconsulta" },
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    // Espacio vacío si no hay teleconsulta
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            // Fecha de la cita
            SectionHeader(
                icon = Icons.Filled.CalendarToday,
                title = "Fecha de la cita"
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = selectedDate?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) ?: "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("mm/dd/yyyy") },
                readOnly = true,
                trailingIcon = {
                    IconButton(
                        onClick = {
                            val calendar = Calendar.getInstance()
                            val year = calendar.get(Calendar.YEAR)
                            val month = calendar.get(Calendar.MONTH)
                            val day = calendar.get(Calendar.DAY_OF_MONTH)

                            DatePickerDialog(
                                context,
                                { _, selectedYear, selectedMonth, selectedDay ->
                                    val date = LocalDate.of(selectedYear, selectedMonth + 1, selectedDay)
                                    val dayOfWeek = date.dayOfWeek
                                    if (isDayAvailable(dayOfWeek, doctor!!.availability)) {
                                        selectedDate = date
                                    }
                                },
                                year,
                                month,
                                day
                            ).apply {
                                datePicker.minDate = System.currentTimeMillis()
                            }.show()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CalendarToday,
                            contentDescription = "Seleccionar fecha",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF00BCD4),
                    unfocusedBorderColor = Color(0xFFE0E0E0)
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Hora de la cita
            SectionHeader(
                icon = Icons.Filled.CalendarToday,
                title = "Hora de la cita"
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Chips de horarios en grid de 3 columnas
            val timeSlots = doctor!!.timeSlot
            timeSlots.chunked(3).forEach { rowSlots ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowSlots.forEach { slot ->
                        TimeChip(
                            timeSlot = slot,
                            selected = selectedTimeSlot == slot,
                            onClick = { selectedTimeSlot = slot },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    // Rellenar espacios vacíos
                    repeat(3 - rowSlots.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Motivo de la consulta
            SectionHeader(
                icon = Icons.Filled.EventNote,
                title = "Motivo de la consulta"
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = reason,
                onValueChange = { reason = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                placeholder = {
                    Text(
                        text = "Describe brevemente el motivo de tu\nconsulta...",
                        color = Color(0xFFBDBDBD)
                    )
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF00BCD4),
                    unfocusedBorderColor = Color(0xFFE0E0E0)
                ),
                maxLines = 5
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Resumen de la cita
            SummaryCard(
                date = selectedDate,
                timeSlot = selectedTimeSlot
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Mostrar mensaje de error si existe
            errorMessage?.let { error ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Warning,
                            contentDescription = "Error",
                            tint = Color(0xFFD32F2F),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = error,
                            fontSize = 13.sp,
                            color = Color(0xFFD32F2F)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            Button(
                onClick = {
                    val date = selectedDate ?: return@Button
                    val timeSlot = selectedTimeSlot ?: return@Button
                    
                    // Validar campos requeridos
                    if (reason.trim().length < 5) {
                        errorMessage = "El motivo debe tener al menos 5 caracteres"
                        return@Button
                    }
                    
                    if (reason.trim().length > 200) {
                        errorMessage = "El motivo no puede exceder 200 caracteres"
                        return@Button
                    }
                    
                    coroutineScope.launch {
                        isProcessing = true
                        errorMessage = null
                        
                        val dateTime = LocalDateTime.of(date, LocalTime.parse(timeSlot.start))
                        val millis = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
                        
                        // Validar fecha futura
                        if (millis <= System.currentTimeMillis()) {
                            isProcessing = false
                            errorMessage = "Debes seleccionar una fecha y hora futura"
                            return@launch
                        }
                        
                        if (isRescheduling && appointmentIdLong != null) {
                            // Reprogramar cita existente
                            viewModel.updateAppointment(
                                appointmentId = appointmentIdLong,
                                newDateTimeMillis = millis,
                                onSuccess = {
                                    coroutineScope.launch {
                                        delay(1500)
                                        isProcessing = false
                                        showSuccess = true
                                        delay(2000)
                                        navController.navigate(Destination.APPOINTMENTS) {
                                            popUpTo(Destination.HOME) { inclusive = false }
                                        }
                                    }
                                },
                                onError = { error ->
                                    isProcessing = false
                                    errorMessage = error
                                }
                            )
                        } else {
                            // Crear nueva cita
                            viewModel.scheduleAppointment(
                                doctorId = doctorIdLong,
                                dateTimeMillis = millis,
                                consultationType = consultationType,
                                reason = reason.trim(),
                                onSuccess = {
                                    coroutineScope.launch {
                                        delay(1500)
                                        isProcessing = false
                                        showSuccess = true
                                        delay(2000)
                                        navController.navigate(Destination.APPOINTMENTS) {
                                            popUpTo(Destination.HOME) { inclusive = false }
                                        }
                                    }
                                },
                                onError = { error ->
                                    isProcessing = false
                                    errorMessage = error
                                }
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = selectedDate != null && selectedTimeSlot != null && !isProcessing && !showSuccess,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00BCD4),
                    disabledContainerColor = Color(0xFFE0E0E0)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = if (isRescheduling) "Reprogramar cita" else "Reservar cita",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
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
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            }
        }
        
        // Overlay de confirmación - FUERA del Column principal
        if (isProcessing || showSuccess) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .padding(32.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        if (isProcessing) {
                            // Mostrar loading
                            CircularProgressIndicator(
                                modifier = Modifier.size(64.dp),
                                color = Color(0xFF00BCD4),
                                strokeWidth = 6.dp
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            Text(
                                text = if (isRescheduling) "Reprogramando tu cita..." else "Procesando tu cita...",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Por favor espera un momento",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        } else if (showSuccess) {
                            // Mostrar check de éxito turquesa
                            Icon(
                                imageVector = Icons.Filled.CheckCircle,
                                contentDescription = "Éxito",
                                tint = Color(0xFF00BCD4),
                                modifier = Modifier.size(80.dp)
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            Text(
                                text = if (isRescheduling) "¡Cita reprogramada!" else "¡Cita confirmada!",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = if (isRescheduling) "Tu cita ha sido reprogramada exitosamente" else "Tu cita ha sido agendada exitosamente",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DoctorSummaryCard(doctorName: String, specialty: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0F7FA)),
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
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = specialty,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun SummaryCard(
    date: LocalDate?,
    timeSlot: TimeSlot?
) {
    // Calcular duración en minutos
    val duration = if (timeSlot != null) {
        try {
            val start = LocalTime.parse(timeSlot.start)
            val end = LocalTime.parse(timeSlot.end)
            val durationMinutes = java.time.Duration.between(start, end).toMinutes()
            "$durationMinutes minutos"
        } catch (e: Exception) {
            "30 minutos"
        }
    } else {
        "No seleccionada"
    }

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

            SummaryRow(label = "Fecha", value = date?.format(dateFormatter) ?: "No seleccionada")
            Spacer(modifier = Modifier.height(8.dp))
            SummaryRow(label = "Hora", value = timeSlot?.let { "${it.start} - ${it.end}" } ?: "No seleccionada")
            Spacer(modifier = Modifier.height(8.dp))
            SummaryRow(label = "Duración", value = duration)
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
private fun SectionHeader(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF00BCD4),
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun ModalityChip(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .height(48.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        color = if (selected) Color(0xFFE0F7FA) else MaterialTheme.colorScheme.surface,
        border = BorderStroke(
            width = if (selected) 2.dp else 1.dp,
            color = if (selected) Color(0xFF00BCD4) else Color(0xFFE0E0E0)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (selected) Color(0xFF00BCD4) else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                color = if (selected) Color(0xFF00838F) else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun TimeChip(
    timeSlot: TimeSlot,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .height(48.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        color = if (selected) Color(0xFFE0F7FA) else MaterialTheme.colorScheme.surface,
        border = BorderStroke(
            width = if (selected) 2.dp else 1.dp,
            color = if (selected) Color(0xFF00BCD4) else Color(0xFFE0E0E0)
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${timeSlot.start}-${timeSlot.end}",
                fontSize = 14.sp,
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                color = if (selected) Color(0xFF00838F) else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
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
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")

private fun isDayAvailable(dayOfWeek: DayOfWeek, availability: List<DayAvailability>): Boolean {
    val dayAvailability = when (dayOfWeek) {
        DayOfWeek.MONDAY -> DayAvailability.LUNES
        DayOfWeek.TUESDAY -> DayAvailability.MARTES
        DayOfWeek.WEDNESDAY -> DayAvailability.MIERCOLES
        DayOfWeek.THURSDAY -> DayAvailability.JUEVES
        DayOfWeek.FRIDAY -> DayAvailability.VIERNES
        DayOfWeek.SATURDAY -> DayAvailability.SABADO
        DayOfWeek.SUNDAY -> DayAvailability.DOMINGO
    }
    return availability.contains(dayAvailability)
}
