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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.mediturn.data.model.EspecialityEntity
import com.example.mediturn.ui.MediturnViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpecialtiesScreen(
    navController: NavController,
    viewModel: MediturnViewModel
) {
    val specialties by viewModel.specialties.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
    ) {
        // Header
        TopAppBar(
            title = {
                Text(
                    text = "Especialidades",
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Descripción
            Text(
                text = "Explora nuestras especialidades médicas y encuentra el profesional ideal para ti",
                fontSize = 14.sp,
                color = Color(0xFF757575),
                lineHeight = 20.sp,
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Lista de especialidades
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(specialties, key = { it.id }) { specialty ->
                    SpecialtyItemCard(
                        specialty = specialty
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun SpecialtyItemCard(
    specialty: EspecialityEntity
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
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
            // Ícono de especialidad
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(getSpecialtyColor(specialty.name)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = getSpecialtyIcon(specialty.name),
                    contentDescription = specialty.name,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Información de la especialidad
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = specialty.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = getSpecialtyDescription(specialty.name),
                    fontSize = 13.sp,
                    color = Color(0xFF757575),
                    lineHeight = 18.sp
                )
            }
        }
    }
}

private fun getSpecialtyIcon(name: String): ImageVector {
    return when (name.lowercase()) {
        "cardiología", "cardiologia" -> Icons.Filled.Favorite
        "pediatría", "pediatria" -> Icons.Filled.ChildCare
        "dermatología", "dermatologia" -> Icons.Filled.Face
        "neurología", "neurologia" -> Icons.Filled.Psychology
        "odontología", "odontologia" -> Icons.Filled.MedicalServices
        "oftalmología", "oftalmologia" -> Icons.Filled.Visibility
        "traumatología", "traumatologia" -> Icons.Filled.Healing
        "ginecología", "ginecologia" -> Icons.Filled.FavoriteBorder
        else -> Icons.Filled.LocalHospital
    }
}

private fun getSpecialtyColor(name: String): Color {
    return when (name.lowercase()) {
        "cardiología", "cardiologia" -> Color(0xFFE91E63)
        "pediatría", "pediatria" -> Color(0xFF00BCD4)
        "dermatología", "dermatologia" -> Color(0xFF00BCD4)
        "neurología", "neurologia" -> Color(0xFF00BCD4)
        "odontología", "odontologia" -> Color(0xFF00BCD4)
        "oftalmología", "oftalmologia" -> Color(0xFF00BCD4)
        "traumatología", "traumatologia" -> Color(0xFF4CAF50)
        "ginecología", "ginecologia" -> Color(0xFF9C27B0)
        else -> Color(0xFF00BCD4)
    }
}

private fun getSpecialtyDescription(name: String): String {
    return when (name.lowercase()) {
        "cardiología", "cardiologia" -> "Atención integral del corazón y sistema circulatorio"
        "pediatría", "pediatria" -> "Cuidado médico especializado para niños y adolescentes"
        "dermatología", "dermatologia" -> "Tratamientos para la piel, cabello y uñas"
        "neurología", "neurologia" -> "Diagnóstico y tratamiento del sistema nervioso"
        "odontología", "odontologia" -> "Cuidado integral de la salud bucal y dental"
        "oftalmología", "oftalmologia" -> "Atención especializada para la salud visual"
        "traumatología", "traumatologia" -> "Tratamiento de lesiones músculo-esqueléticas"
        "ginecología", "ginecologia" -> "Salud integral de la mujer"
        "medicina general", "general" -> "Atención primaria y diagnóstico general"
        "psiquiatría", "psiquiatria" -> "Salud mental y bienestar emocional"
        else -> "Atención médica especializada"
    }
}
