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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContactPhone
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VideoCall
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
    val specialties by viewModel.specialties.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Detalles del Doctor",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
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
            EmptyDoctorState()
            return
        }

        val specialty = specialties.firstOrNull { it.id == doctor!!.specialtyId }?.name ?: ""

        DoctorDetailContent(
            doctor = doctor!!,
            specialty = specialty,
            onScheduleAppointment = onScheduleAppointment,
            onTeleconsultation = onTeleconsultation,
            onCallPhone = onCallPhone
        )
    }
}

@Composable
private fun DoctorDetailContent(
    doctor: DoctorEntity,
    specialty: String,
    onScheduleAppointment: () -> Unit,
    onTeleconsultation: () -> Unit,
    onCallPhone: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Card de perfil del doctor
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Foto del doctor
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE0E0E0)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Foto del doctor",
                        tint = Color(0xFF9E9E9E),
                        modifier = Modifier.size(70.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Nombre del doctor
                Text(
                    text = "${doctor.name} ${doctor.lastname}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Especialidad
                Text(
                    text = specialty,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF00BCD4)
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Hospital
                Text(
                    text = doctor.hospital,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Rating con estrellas
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    repeat(5) { index ->
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = if (index < doctor.rating.toInt()) Color(0xFFFFC107) else Color(0xFFE0E0E0),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${doctor.rating}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "(127 reseñas)",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Botón Agendar Cita
                Button(
                    onClick = onScheduleAppointment,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BCD4)),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Text(
                        text = "Agendar Cita",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.surface
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Sección: Acerca del doctor
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                SectionWithIcon(
                    icon = Icons.Filled.Info,
                    title = "Acerca del doctor"
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = doctor.about,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sección: Disponibilidad
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                SectionWithIcon(
                    icon = Icons.Filled.Business,
                    title = "Disponibilidad"
                )
                Spacer(modifier = Modifier.height(12.dp))
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (doctor.availability.isNotEmpty() && doctor.timeSlot.isNotEmpty()) {
                        val groupedDays = groupConsecutiveDays(doctor.availability)
                        val timeRange = if (doctor.timeSlot.isNotEmpty()) {
                            "${doctor.timeSlot.first().start} - ${doctor.timeSlot.last().end}"
                        } else ""
                        
                        groupedDays.forEach { (dayRange, days) ->
                            AvailabilityRow(dayRange, timeRange)
                        }
                    } else {
                        Text(
                            text = "No hay horarios disponibles",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Card de Teleconsulta
        TeleconsultationCard(
            available = doctor.hasTeleconsultation,
            onClick = onTeleconsultation
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Sección: Información de contacto
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                SectionWithIcon(
                    icon = Icons.Filled.ContactPhone,
                    title = "Información de contacto"
                )
                Spacer(modifier = Modifier.height(12.dp))
                
                ContactInfoRow(
                    icon = Icons.Filled.Business,
                    text = doctor.hospital,
                    subtitle = doctor.location
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                ContactInfoRow(
                    icon = Icons.Filled.Phone,
                    text = doctor.phone,
                    subtitle = null
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun SectionWithIcon(
    icon: ImageVector,
    title: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = Color(0xFF00BCD4),
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun AvailabilityRow(day: String, hours: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.CheckCircle,
            contentDescription = null,
            tint = Color(0xFF00BCD4),
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = day,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = hours,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun TeleconsultationCard(
    available: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (available) Color(0xFFE0F7FA) else Color(0xFFFFEBEE)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(if (available) Color(0xFF00BCD4) else Color(0xFFEF5350)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.VideoCall,
                    contentDescription = "Teleconsulta",
                    tint = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (available) "Teleconsulta Disponible" else "Teleconsulta no disponible",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (available) Color(0xFF00838F) else Color(0xFFC62828)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = if (available) "Servicio desde 9:00" else "Servicio no habilitado",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            if (available) {
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = "Ver más",
                    tint = Color(0xFF00838F),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
private fun ContactInfoRow(
    icon: ImageVector,
    text: String,
    subtitle: String?
) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(20.dp)
        )
        Column {
            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            if (subtitle != null) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

private fun groupConsecutiveDays(days: List<DayAvailability>): List<Pair<String, List<DayAvailability>>> {
    if (days.isEmpty()) return emptyList()
    
    val dayOrder = listOf(
        DayAvailability.LUNES,
        DayAvailability.MARTES,
        DayAvailability.MIERCOLES,
        DayAvailability.JUEVES,
        DayAvailability.VIERNES,
        DayAvailability.SABADO,
        DayAvailability.DOMINGO
    )
    
    val sortedDays = days.sortedBy { dayOrder.indexOf(it) }
    val groups = mutableListOf<Pair<String, List<DayAvailability>>>()
    var currentGroup = mutableListOf(sortedDays[0])
    
    for (i in 1 until sortedDays.size) {
        val prevIndex = dayOrder.indexOf(sortedDays[i - 1])
        val currIndex = dayOrder.indexOf(sortedDays[i])
        
        if (currIndex == prevIndex + 1) {
            currentGroup.add(sortedDays[i])
        } else {
            groups.add(formatDayRange(currentGroup) to currentGroup.toList())
            currentGroup = mutableListOf(sortedDays[i])
        }
    }
    groups.add(formatDayRange(currentGroup) to currentGroup.toList())
    
    return groups
}

private fun formatDayRange(days: List<DayAvailability>): String {
    if (days.isEmpty()) return ""
    if (days.size == 1) {
        return when (days[0]) {
            DayAvailability.LUNES -> "Lunes"
            DayAvailability.MARTES -> "Martes"
            DayAvailability.MIERCOLES -> "Miércoles"
            DayAvailability.JUEVES -> "Jueves"
            DayAvailability.VIERNES -> "Viernes"
            DayAvailability.SABADO -> "Sábados"
            DayAvailability.DOMINGO -> "Domingos"
        }
    }
    
    val firstDay = when (days.first()) {
        DayAvailability.LUNES -> "Lunes"
        DayAvailability.MARTES -> "Martes"
        DayAvailability.MIERCOLES -> "Miércoles"
        DayAvailability.JUEVES -> "Jueves"
        DayAvailability.VIERNES -> "Viernes"
        DayAvailability.SABADO -> "Sábados"
        DayAvailability.DOMINGO -> "Domingos"
    }
    
    val lastDay = when (days.last()) {
        DayAvailability.LUNES -> "Lunes"
        DayAvailability.MARTES -> "Martes"
        DayAvailability.MIERCOLES -> "Miércoles"
        DayAvailability.JUEVES -> "Jueves"
        DayAvailability.VIERNES -> "Viernes"
        DayAvailability.SABADO -> "Sábados"
        DayAvailability.DOMINGO -> "Domingos"
    }
    
    return "$firstDay - $lastDay"
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
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
