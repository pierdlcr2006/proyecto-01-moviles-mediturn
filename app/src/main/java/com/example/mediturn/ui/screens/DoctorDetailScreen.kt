package com.example.mediturn.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mediturn.util.Destination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorDetailScreen(
    navController: NavController,
    doctorId: String,
    onBack: () -> Unit = {},
    onScheduleAppointment: () -> Unit = {},
    onTeleconsultation: () -> Unit = {},
    onCallPhone: (String) -> Unit = {}
) {
    // TODO: Cargar datos del médico desde ViewModel usando doctorId
    // val doctor by viewModel.getDoctorById(doctorId).collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
    ) {
        // Header
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
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            )
        )
        
        // Contenido scrolleable
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            
            // Foto del doctor (placeholder circular)
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0E0E0)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Foto del doctor",
                    tint = Color(0xFF9E9E9E),
                    modifier = Modifier.size(60.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Nombre del doctor
            Text(
                text = "Dra. María González",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF212121)
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            // Especialidad
            Text(
                text = "Cardiología",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF00BCD4)
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            // Hospital
            Text(
                text = "Hospital San Rafael",
                fontSize = 14.sp,
                color = Color(0xFF757575)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Rating con estrellas
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                repeat(5) { index ->
                    Icon(
                        imageVector = if (index < 4) Icons.Filled.Star else Icons.Filled.StarHalf,
                        contentDescription = null,
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "4.8",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121)
                )
                Text(
                    text = "(127 reseñas)",
                    fontSize = 13.sp,
                    color = Color(0xFF757575)
                )
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Botón Agendar Cita
            Button(
                onClick = { navController.navigate(Destination.scheduleAppointment(doctorId)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00BCD4)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Agendar Cita",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Acerca del doctor
            InfoSection(
                icon = Icons.Filled.Person,
                title = "Acerca del doctor",
                content = {
                    Text(
                        text = "Especialista en cardiología con más de 15 años de experiencia. Graduado de la Universidad Nacional con especialización en medicina cardiovascular intervencionista. Experta en tratamiento de enfermedades cardiovasculares y procedimientos como cateterismo cardiaco. Comprometida con brindar atención médica de calidad con un trato humano y personalizado.",
                        fontSize = 14.sp,
                        color = Color(0xFF757575),
                        lineHeight = 20.sp
                    )
                }
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Disponibilidad
            InfoSection(
                icon = Icons.Filled.CalendarToday,
                title = "Disponibilidad",
                content = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        AvailabilityItem(
                            day = "Lunes - Viernes",
                            hours = "8:00 AM - 6:00 PM",
                            isAvailable = true
                        )
                        AvailabilityItem(
                            day = "Sábados",
                            hours = "9:00 AM - 2:00 PM",
                            isAvailable = true
                        )
                        AvailabilityItem(
                            day = "Domingos",
                            hours = "Cerrado",
                            isAvailable = false
                        )
                    }
                }
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Teleconsulta
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                onClick = onTeleconsultation
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                color = Color(0xFFE0F7FA),
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Videocam,
                            contentDescription = "Teleconsulta",
                            tint = Color(0xFF00BCD4),
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Teleconsulta Disponible",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF212121)
                        )
                        Text(
                            text = "Consulta desde casa",
                            fontSize = 13.sp,
                            color = Color(0xFF757575)
                        )
                    }
                    
                    Icon(
                        imageVector = Icons.Filled.ChevronRight,
                        contentDescription = "Ver más",
                        tint = Color(0xFF757575),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Información de contacto
            InfoSection(
                icon = Icons.Filled.Phone,
                title = "Información de contacto",
                content = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Hospital
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.LocalHospital,
                                contentDescription = "Hospital",
                                tint = Color(0xFF757575),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Hospital San Rafael",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF212121)
                                )
                                Text(
                                    text = "Av. Los Héroes 123, Centro",
                                    fontSize = 13.sp,
                                    color = Color(0xFF757575)
                                )
                            }
                        }
                        
                        // Teléfono
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Phone,
                                contentDescription = "Teléfono",
                                tint = Color(0xFF757575),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "+51 992 555-123",
                                fontSize = 14.sp,
                                color = Color(0xFF00BCD4),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            )
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun InfoSection(
    icon: ImageVector,
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = Color(0xFFE0F7FA),
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = Color(0xFF00BCD4),
                        modifier = Modifier.size(22.dp)
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
fun AvailabilityItem(
    day: String,
    hours: String,
    isAvailable: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        color = if (isAvailable) Color(0xFF4CAF50) else Color(0xFFE0E0E0),
                        shape = CircleShape
                    )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = day,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF212121)
            )
        }
        
        Text(
            text = hours,
            fontSize = 14.sp,
            color = if (isAvailable) Color(0xFF757575) else Color(0xFFBDBDBD)
        )
    }
}
