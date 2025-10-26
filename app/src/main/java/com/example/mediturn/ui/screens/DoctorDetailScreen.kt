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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.mediturn.data.model.DayAvailability
import com.example.mediturn.data.model.DoctorEntity
import com.example.mediturn.ui.MediturnViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DoctorDetailScreen(
    navController: NavController,
    doctorId: String,
    viewModel: MediturnViewModel,
    onScheduleAppointment: () -> Unit,
    onTeleconsultation: () -> Unit = {},
    onCallPhone: (String) -> Unit = {}
) {
    val doctorIdLong = doctorId.toLongOrNull() ?: -1L
    val doctor by viewModel.doctor(doctorIdLong).collectAsStateWithLifecycle(initialValue = null)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Detalles del Doctor",
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
            EmptyDoctorState()
            return
        }

        DoctorDetailContent(
            doctor = doctor!!,
            onScheduleAppointment = onScheduleAppointment,
            onTeleconsultation = onTeleconsultation,
            onCallPhone = onCallPhone
        )
    }
}

@Composable
private fun DoctorDetailContent(
    doctor: DoctorEntity,
    onScheduleAppointment: () -> Unit,
    onTeleconsultation: () -> Unit,
    onCallPhone: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color(0xFFE0F7FA)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Foto del doctor",
                tint = Color(0xFF00BCD4),
                modifier = Modifier.size(60.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "${doctor.name} ${doctor.lastname}",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF212121)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = doctor.hospital,
            fontSize = 16.sp,
            color = Color(0xFF757575)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Rating",
                tint = Color(0xFFFFC107)
            )
            Text(
                text = "${doctor.rating}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF212121)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        InfoSection(
            icon = Icons.Filled.Info,
            title = "Sobre el doctor"
        ) {
            Text(
                text = doctor.about,
                fontSize = 14.sp,
                color = Color(0xFF424242)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        InfoSection(
            icon = Icons.Filled.LocationOn,
            title = "Ubicación"
        ) {
            Text(
                text = doctor.location,
                fontSize = 14.sp,
                color = Color(0xFF424242)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = doctor.phone,
                fontSize = 14.sp,
                color = Color(0xFF00BCD4)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        InfoSection(
            icon = Icons.Filled.CalendarToday,
            title = "Disponibilidad"
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                doctor.availability.forEach { day ->
                    AvailabilityItem(
                        day = day,
                        hours = doctor.timeSlot.joinToString { "${it.start} - ${it.end}" }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        InfoSection(
            icon = Icons.Filled.CheckCircle,
            title = "Servicios"
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Consulta presencial",
                    fontSize = 14.sp,
                    color = Color(0xFF424242)
                )
                if (doctor.hasTeleconsultation) {
                    Text(
                        text = "Teleconsulta disponible",
                        fontSize = 14.sp,
                        color = Color(0xFF424242)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (doctor.hasTeleconsultation) {
            TextButton(onClick = onTeleconsultation) {
                Text(
                    text = "Agendar teleconsulta",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Button(
            onClick = onScheduleAppointment,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
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

        OutlinedButton(
            onClick = { onCallPhone(doctor.phone) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Phone,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Llamar al consultorio", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun InfoSection(
    icon: ImageVector,
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(color = Color(0xFFE0F7FA), shape = RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = Color(0xFF00BCD4)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF212121)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
private fun AvailabilityItem(day: DayAvailability, hours: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = day.name.lowercase().replaceFirstChar { it.titlecase() },
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF212121)
        )
        Text(
            text = hours,
            fontSize = 13.sp,
            color = Color(0xFF757575)
        )
    }
}

@Composable
private fun EmptyDoctorState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No encontramos los datos del doctor",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Intenta nuevamente desde la lista de doctores.",
            fontSize = 14.sp,
            color = Color(0xFF757575)
        )
    }
}
