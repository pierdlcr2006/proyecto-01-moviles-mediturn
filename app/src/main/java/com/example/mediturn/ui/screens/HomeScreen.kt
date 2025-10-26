package com.example.mediturn.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PanTool
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mediturn.R
import com.example.mediturn.data.model.Appointment
import com.example.mediturn.data.model.DoctorEntity
import com.example.mediturn.data.model.EspecialityEntity
import com.example.mediturn.ui.MediturnViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    viewModel: MediturnViewModel,
    onNotificationsClick: () -> Unit,
    onSearchClick: () -> Unit,
    onDoctorClick: (Long) -> Unit
) {
    val patient by viewModel.activePatient.collectAsStateWithLifecycle()
    val specialties by viewModel.specialties.collectAsStateWithLifecycle()
    val topDoctors by viewModel.topDoctors.collectAsStateWithLifecycle()
    val doctors by viewModel.doctors.collectAsStateWithLifecycle()
    val upcomingAppointments by viewModel.upcomingAppointments.collectAsStateWithLifecycle()
    val unreadNotifications by viewModel.unreadNotifications.collectAsStateWithLifecycle()

    val nextAppointment = upcomingAppointments.firstOrNull()
    val appointmentDoctor = nextAppointment?.let { appointment ->
        doctors.firstOrNull { it.id == appointment.doctorId }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
            .verticalScroll(rememberScrollState())
    ) {
        HeaderSection(
            userName = patient?.fullName.orEmpty().ifBlank { "Bienvenido" },
            unreadNotifications = unreadNotifications,
            onNotificationsClick = onNotificationsClick,
            onSearchClick = onSearchClick
        )

        SpecialtiesSection(
            specialties = specialties,
            onSeeAll = onSearchClick
        )

        CarruselSection()

        NextAppointmentCard(
            appointment = nextAppointment,
            doctor = appointmentDoctor
        )

        TipsCard()

        if (topDoctors.isNotEmpty()) {
            TopDoctorsSection(
                doctors = topDoctors,
                onDoctorClick = onDoctorClick
            )
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
private fun HeaderSection(
    userName: String,
    unreadNotifications: Int,
    onNotificationsClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = "MediTurn",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF00BCD4),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Hola, $userName",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Filled.PanTool,
                    contentDescription = null,
                    tint = Color(0xFFFFC107),
                    modifier = Modifier.size(24.dp)
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onSearchClick) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Buscar",
                        tint = Color(0xFF757575)
                    )
                }

                IconButton(onClick = onNotificationsClick) {
                    BadgedBox(
                        badge = {
                            if (unreadNotifications > 0) {
                                Badge(
                                    containerColor = Color(0xFFFF3D00)
                                ) {
                                    Text(
                                        text = unreadNotifications.coerceAtMost(9).toString(),
                                        color = Color.White,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Notifications,
                            contentDescription = "Notificaciones",
                            tint = Color(0xFF757575),
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SpecialtiesSection(
    specialties: List<EspecialityEntity>,
    onSeeAll: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(color = Color(0xFFE0F7FA), shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.MedicalServices,
                            contentDescription = "Especialidades",
                            tint = Color(0xFF00BCD4),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "Especialidades",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF212121)
                        )
                        Text(
                            text = "Explora áreas médicas disponibles",
                            fontSize = 12.sp,
                            color = Color(0xFF757575)
                        )
                    }
                }

                TextButtonSmall(
                    label = "Ver todas",
                    onClick = onSeeAll
                )
            }

            if (specialties.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    specialties.take(6).forEach { specialty ->
                        SpecialtyChip(name = specialty.name)
                    }
                }
            }
        }
    }
}

@Composable
private fun SpecialtyChip(name: String) {
    Surface(
        color = Color(0xFFE0F7FA),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = name,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF00838F)
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun CarruselSection() {
    val images = listOf(R.drawable.carrusel1, R.drawable.carrusel2, R.drawable.carrusel3)
    if (images.isEmpty()) return

    val pagerState = rememberPagerState(initialPage = 0)

    LaunchedEffect(pagerState) {
        while (true) {
            yield()
            delay(4000)
            val nextPage = (pagerState.currentPage + 1) % images.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .clip(RoundedCornerShape(16.dp)),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            HorizontalPager(state = pagerState, count = images.size) { page ->
                Image(
                    painter = painterResource(id = images[page]),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            activeColor = Color(0xFF212121),
            inactiveColor = Color(0xFFBDBDBD),
            indicatorWidth = 8.dp,
            indicatorHeight = 8.dp,
            spacing = 8.dp
        )
    }
}

@Composable
private fun NextAppointmentCard(
    appointment: Appointment?,
    doctor: DoctorEntity?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        if (appointment != null && doctor != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(color = Color(0xFFE0F7FA), shape = RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Notifications,
                        contentDescription = "Próxima cita",
                        tint = Color(0xFF00BCD4),
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Próxima cita",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF212121)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${doctor.name} ${doctor.lastname}",
                        fontSize = 14.sp,
                        color = Color(0xFF00838F),
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = formatAppointmentDate(appointment.dateTime),
                        fontSize = 13.sp,
                        color = Color(0xFF757575)
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Sin citas programadas",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF212121)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Agenda tu próxima consulta desde la sección Buscar",
                    fontSize = 13.sp,
                    color = Color(0xFF757575)
                )
            }
        }
    }
}

@Composable
private fun TipsCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
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
                    .size(48.dp)
                    .background(color = Color(0xFFE8F5E9), shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.PanTool,
                    contentDescription = null,
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = "Consejo del día",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Hidrátate bien y duerme lo suficiente para mantenerte saludable.",
                    fontSize = 13.sp,
                    color = Color(0xFF757575),
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
private fun TopDoctorsSection(
    doctors: List<DoctorEntity>,
    onDoctorClick: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Doctores destacados",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF212121)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            doctors.forEach { doctor ->
                DoctorSummaryCard(doctor = doctor, onDoctorClick = onDoctorClick)
            }
        }
    }
}

@Composable
private fun DoctorSummaryCard(
    doctor: DoctorEntity,
    onDoctorClick: (Long) -> Unit
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
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE0F7FA)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = doctor.name.take(1) + doctor.lastname.take(1),
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00BCD4)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "${doctor.name} ${doctor.lastname}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF212121)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = doctor.hospital,
                        fontSize = 13.sp,
                        color = Color(0xFF757575)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Rating: ${doctor.rating}",
                    fontSize = 13.sp,
                    color = Color(0xFF00838F)
                )
                TextButtonSmall(
                    label = "Ver detalle",
                    onClick = { onDoctorClick(doctor.id) }
                )
            }
        }
    }
}

@Composable
private fun TextButtonSmall(
    label: String,
    onClick: () -> Unit
) {
    androidx.compose.material3.TextButton(onClick = onClick) {
        Text(text = label, color = MaterialTheme.colorScheme.primary)
    }
}

private fun formatAppointmentDate(timestamp: Long): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy • hh:mm a")
    val zonedDateTime = Instant.ofEpochMilli(timestamp)
        .atZone(ZoneId.systemDefault())
    return formatter.format(zonedDateTime)
}
