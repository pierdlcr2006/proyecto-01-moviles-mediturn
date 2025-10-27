package com.example.mediturn.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mediturn.data.model.DoctorEntity
import com.example.mediturn.data.model.EspecialityEntity
import com.example.mediturn.ui.MediturnViewModel
import com.example.mediturn.util.Destination

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SearchScreen(
    navController: NavController,
    viewModel: MediturnViewModel,
    onDoctorClick: (Long) -> Unit
) {
    val doctors by viewModel.doctors.collectAsStateWithLifecycle()
    val specialties by viewModel.specialties.collectAsStateWithLifecycle()

    var searchText by remember { mutableStateOf("") }
    var selectedSpecialtyId by remember { mutableStateOf<Long?>(null) }
    var showFilterDialog by remember { mutableStateOf(false) }

    val filteredDoctors = remember(searchText, selectedSpecialtyId, doctors, specialties) {
        doctors.filter { doctor ->
            val matchesSpecialty = selectedSpecialtyId?.let { doctor.specialtyId == it } ?: true
            val matchesText = searchText.isBlank() ||
                "${doctor.name} ${doctor.lastname}".contains(searchText, ignoreCase = true) ||
                specialties.firstOrNull { it.id == doctor.specialtyId }
                    ?.name
                    ?.contains(searchText, ignoreCase = true) == true
            matchesSpecialty && matchesText
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
    ) {
        TopAppBar(
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "MediTurn",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00BCD4)
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text(
                        text = "Buscar médicos...",
                        color = Color(0xFF9E9E9E),
                        fontSize = 14.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Buscar",
                        tint = Color(0xFF9E9E9E),
                        modifier = Modifier.size(20.dp)
                    )
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF00BCD4),
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                singleLine = true
            )

            IconButton(
                onClick = { showFilterDialog = true },
                modifier = Modifier
                    .size(48.dp)
                    .background(color = Color(0xFF00BCD4), shape = RoundedCornerShape(12.dp))
            ) {
                Icon(
                    imageVector = Icons.Filled.FilterList,
                    contentDescription = "Filtrar",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (filteredDoctors.isEmpty()) {
            EmptyState()
        } else {
            DoctorsList(
                doctors = filteredDoctors,
                specialties = specialties,
                onDoctorClick = onDoctorClick
            )
        }
    }

    // Diálogo de filtros
    if (showFilterDialog) {
        FilterDialog(
            specialties = specialties,
            selectedSpecialtyId = selectedSpecialtyId,
            onSpecialtySelected = { specialtyId ->
                selectedSpecialtyId = specialtyId
            },
            onDismiss = { showFilterDialog = false },
            onClearFilter = {
                selectedSpecialtyId = null
                showFilterDialog = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilterDialog(
    specialties: List<EspecialityEntity>,
    selectedSpecialtyId: Long?,
    onSpecialtySelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
    onClearFilter: () -> Unit
) {
    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Filtrar por especialidad",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF212121)
            )
        },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(specialties, key = { it.id }) { specialty ->
                    FilterChip(
                        selected = specialty.id == selectedSpecialtyId,
                        onClick = {
                            onSpecialtySelected(specialty.id)
                            onDismiss()
                        },
                        label = {
                            Text(
                                text = specialty.name,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFFE0F7FA),
                            selectedLabelColor = Color(0xFF00838F),
                            containerColor = Color(0xFFF5F5F5),
                            labelColor = Color(0xFF757575)
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = specialty.id == selectedSpecialtyId,
                            borderColor = if (specialty.id == selectedSpecialtyId) Color(0xFF00BCD4) else Color(0xFFE0E0E0),
                            borderWidth = 1.dp
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        },
        confirmButton = {
            androidx.compose.material3.TextButton(
                onClick = onClearFilter
            ) {
                Text(
                    text = "Limpiar filtro",
                    color = Color(0xFF00BCD4),
                    fontWeight = FontWeight.Medium
                )
            }
        },
        dismissButton = {
            androidx.compose.material3.TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = "Cerrar",
                    color = Color(0xFF757575),
                    fontWeight = FontWeight.Medium
                )
            }
        },
        containerColor = Color.White,
        shape = RoundedCornerShape(20.dp)
    )
}

@Composable
private fun DoctorsList(
    doctors: List<DoctorEntity>,
    specialties: List<EspecialityEntity>,
    onDoctorClick: (Long) -> Unit
) {
    val specialtyMap = remember(specialties) {
        specialties.associateBy { it.id }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(doctors) { doctor ->
            DoctorCard(
                doctor = doctor,
                specialty = specialtyMap[doctor.specialtyId]?.name.orEmpty(),
                onDetailClick = { onDoctorClick(doctor.id) }
            )
        }

        item { Spacer(modifier = Modifier.height(80.dp)) }
    }
}

@Composable
private fun DoctorCard(
    doctor: DoctorEntity,
    specialty: String,
    onDetailClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0E0E0)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Foto del médico",
                    tint = Color(0xFF9E9E9E),
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Dr. ${doctor.name} ${doctor.lastname}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF212121),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = specialty,
                fontSize = 14.sp,
                color = Color(0xFF00BCD4),
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = doctor.location,
                fontSize = 13.sp,
                color = Color(0xFF757575),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onDetailClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BCD4)),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Ver detalle",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
                )
            }
        }
    }
}

@Composable
private fun EmptyState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No encontramos resultados",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Intenta con otra especialidad o doctor",
            fontSize = 14.sp,
            color = Color(0xFF757575),
            textAlign = TextAlign.Center
        )
    }
}
