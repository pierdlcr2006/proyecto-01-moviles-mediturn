package com.example.mediturn.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mediturn.util.Destination

// Datos de ejemplo temporales para testing
data class DoctorData(
    val name: String,
    val specialty: String,
    val location: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    onBack: () -> Unit = {},
    onSearchTextChange: (String) -> Unit = {},
    onFilterClick: () -> Unit = {},
    onCategorySelected: (String) -> Unit = {},
    onDoctorClick: (String) -> Unit = {}
) {
    // Estado para el texto de búsqueda
    var searchText by remember { mutableStateOf("") }
    
    // Estado para la categoría seleccionada
    var selectedCategory by remember { mutableStateOf("Cardiología") }
    
    // TODO: Recibir estos datos desde ViewModel/Repository
    // val doctors by viewModel.doctors.collectAsState()
    val categories = listOf("Cardiología", "Dermatología", "Pediatría", "Odontología")
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
    ) {
        // Header con flecha de retroceso y título
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
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            )
        )
        
        // Barra de búsqueda con filtro
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Campo de búsqueda
            OutlinedTextField(
                value = searchText,
                onValueChange = { 
                    searchText = it
                    onSearchTextChange(it)
                },
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
                        tint = Color(0xFF9E9E9E)
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
            
            // Botón de filtro
            Button(
                onClick = onFilterClick,
                modifier = Modifier.size(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00BCD4)
                ),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.FilterList,
                    contentDescription = "Filtros",
                    tint = Color.White
                )
            }
        }
        
        // Chips de categorías
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            categories.forEach { category ->
                CategoryChip(
                    text = category,
                    selected = selectedCategory == category,
                    onClick = { 
                        selectedCategory = category
                        onCategorySelected(category)
                    }
                )
            }
        }
        
        // Lista de médicos
        // TODO: Reemplazar con datos reales desde ViewModel
        val doctors = listOf(
            DoctorData("Dr. María González", "Cardiología", "Hospital San Rafael, Madrid"),
            DoctorData("Dr. Carlos Ruiz", "Medicina General", "Clínica Central, Barcelona"),
            DoctorData("Dra. Ana López", "Odontología", "Centro Dental, Valencia"),
            DoctorData("Dr. Pedro Martín", "Pediatría", "Hospital Infantil, Sevilla"),
            DoctorData("Dra. Laura Rodríguez", "Dermatología", "Clínica Estética, Bilbao"),
            DoctorData("Dr. Miguel García", "Oftalmología", "Instituto Visual, Málaga")
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(doctors) { doctor ->
                DoctorCard(
                    doctorName = doctor.name,
                    specialty = doctor.specialty,
                    location = doctor.location,
                    onDetailClick = { navController.navigate(Destination.doctorDetail(doctor.name)) }
                )
            }
        }
    }
}

@Composable
fun CategoryChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = {
            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
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
            selected = selected,
            borderColor = if (selected) Color(0xFF00BCD4) else Color(0xFFE0E0E0),
            borderWidth = 1.dp
        )
    )
}

@Composable
fun DoctorCard(
    doctorName: String,
    specialty: String,
    location: String,
    onDetailClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Foto del médico (placeholder)
            // TODO: Reemplazar con imagen real desde URL o drawable
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0E0E0)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Foto del médico",
                    tint = Color(0xFF9E9E9E),
                    modifier = Modifier.size(32.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Información del médico
            Column(
                modifier = Modifier.weight(1f)
            ) {
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
                    color = Color(0xFF00BCD4),
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = location,
                    fontSize = 12.sp,
                    color = Color(0xFF757575)
                )
            }
        }
        
        // Botón "Ver detalle"
        Button(
            onClick = onDetailClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00BCD4)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Ver detalle",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
