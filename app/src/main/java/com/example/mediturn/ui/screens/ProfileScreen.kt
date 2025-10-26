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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.navigation.NavController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mediturn.data.model.Appointment
import com.example.mediturn.data.model.DoctorEntity
import com.example.mediturn.ui.MediturnViewModel
import com.example.mediturn.util.Destination
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ProfileScreen(
    navController: NavController,
    viewModel: MediturnViewModel,
    onNotificationsClick: () -> Unit
) {
    val patient by viewModel.activePatient.collectAsStateWithLifecycle()
    val upcomingAppointments by viewModel.upcomingAppointments.collectAsStateWithLifecycle()
    val doctors by viewModel.doctors.collectAsStateWithLifecycle()

    val nextAppointment = upcomingAppointments.firstOrNull()
    val appointmentDoctor = nextAppointment?.let { appointment ->
        doctors.firstOrNull { it.id == appointment.doctorId }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Mi Perfil",
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
                IconButton(onClick = onNotificationsClick) {
                    Icon(
                        imageVector = Icons.Filled.CalendarToday,
                        contentDescription = "Notificaciones",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
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
                    contentDescription = "Foto de perfil",
                    tint = Color(0xFF00BCD4),
                    modifier = Modifier.size(60.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = patient?.fullName.orEmpty().ifBlank { "Usuario MediTurn" },
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF212121)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = patient?.email.orEmpty(),
                fontSize = 14.sp,
                color = Color(0xFF757575)
            )

            Spacer(modifier = Modifier.height(24.dp))

            ProfileActionsCard(
                onEditProfile = { navController.navigate(Destination.EDIT_PROFILE) },
                onNotifications = onNotificationsClick
            )

            Spacer(modifier = Modifier.height(24.dp))

            PersonalInfoCard(patient?.phone, patient?.address)

            Spacer(modifier = Modifier.height(24.dp))

            NextAppointmentSection(
                appointment = nextAppointment,
                doctor = appointmentDoctor,
                onSchedule = { doctorId ->
                    navController.navigate(Destination.scheduleAppointment(doctorId.toString()))
                }
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ProfileActionsCard(
    onEditProfile: () -> Unit,
    onNotifications: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            ProfileMenuItem(
                icon = Icons.Filled.Edit,
                iconColor = Color(0xFF00BCD4),
                title = "Editar perfil",
                onClick = onEditProfile
            )

            Divider(color = Color(0xFFF5F5F5), thickness = 1.dp)

            ProfileMenuItem(
                icon = Icons.Filled.CalendarToday,
                iconColor = Color(0xFF00BCD4),
                title = "Mis notificaciones",
                onClick = onNotifications
            )
        }
    }
}

@Composable
private fun ProfileMenuItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(iconColor.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = iconColor
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF212121)
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = null,
            tint = Color(0xFFBDBDBD),
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
private fun PersonalInfoCard(phone: String?, address: String?) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "Información personal",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF212121)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Phone,
                    contentDescription = null,
                    tint = Color(0xFF757575)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = phone.orEmpty().ifBlank { "Número no disponible" },
                    fontSize = 14.sp,
                    color = Color(0xFF212121)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = null,
                    tint = Color(0xFF757575)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = address.orEmpty().ifBlank { "Agrega una dirección" },
                    fontSize = 14.sp,
                    color = Color(0xFF212121)
                )
            }
        }
    }
}

@Composable
private fun NextAppointmentSection(
    appointment: Appointment?,
    doctor: DoctorEntity?,
    onSchedule: (Long) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "Próxima cita",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF212121)
            )
            Spacer(modifier = Modifier.height(12.dp))

            if (appointment != null && doctor != null) {
                Text(
                    text = "${doctor.name} ${doctor.lastname}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF00838F)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = formatAppointmentDate(appointment.dateTime),
                    fontSize = 14.sp,
                    color = Color(0xFF757575)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { onSchedule(doctor.id) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BCD4)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Reprogramar",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            } else {
                Text(
                    text = "Aún no tienes citas programadas.",
                    fontSize = 14.sp,
                    color = Color(0xFF757575)
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedButton(
                    onClick = { onSchedule(0L) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Agendar nueva cita", fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun EditProfileScreen(
    navController: NavController,
    viewModel: MediturnViewModel
) {
    val patient by viewModel.activePatient.collectAsStateWithLifecycle()

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    LaunchedEffect(patient) {
        patient?.let {
            fullName = it.fullName
            email = it.email
            phone = it.phone
            address = it.address
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Editar perfil",
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ProfileTextField(label = "Nombre completo", value = fullName, onValueChange = { fullName = it })
            ProfileTextField(label = "Correo electrónico", value = email, onValueChange = { email = it })
            ProfileTextField(label = "Número de teléfono", value = phone, onValueChange = { phone = it })
            ProfileTextField(
                label = "Dirección",
                value = address,
                onValueChange = { address = it },
                minLines = 3
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    viewModel.updatePatientProfile(
                        fullName = fullName,
                        email = email,
                        phone = phone,
                        address = address
                    )
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BCD4)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Guardar cambios",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            OutlinedButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "Cancelar", fontSize = 16.sp)
            }
        }
    }
}

@Composable
private fun ProfileTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    minLines: Int = 1
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF212121)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF00BCD4),
                unfocusedBorderColor = Color(0xFFE0E0E0),
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
            minLines = minLines,
            maxLines = if (minLines > 1) minLines else 1
        )
    }
}

private fun formatAppointmentDate(timestamp: Long): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy • hh:mm a")
    return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).format(formatter)
}
