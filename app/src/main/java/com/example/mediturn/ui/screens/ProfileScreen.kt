package com.example.mediturn.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.mediturn.ui.MediturnViewModel
import kotlinx.coroutines.launch
import com.example.mediturn.util.Destination

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ProfileScreen(
    navController: NavController,
    viewModel: MediturnViewModel,
    onNotificationsClick: () -> Unit
) {
    val patient by viewModel.activePatient.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Mi Perfil",
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Foto de perfil
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

            Spacer(modifier = Modifier.height(20.dp))

            // Nombre
            Text(
                text = patient?.fullName.orEmpty().ifBlank { "Usuario MediTurn" },
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Email
            Text(
                text = patient?.email.orEmpty(),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Card de opciones
            ProfileMenuCard(
                onEditProfile = { navController.navigate(Destination.EDIT_PROFILE) },
                onSettings = { navController.navigate(Destination.SETTINGS) },
                onLogout = { /* TODO: Implementar cerrar sesión */ }
            )

            Spacer(modifier = Modifier.weight(1f))

            // Logo y versión
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF00BCD4)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "M",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.surface
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "MediTurn",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Versión 1.0.0",
                    fontSize = 12.sp,
                    color = Color(0xFF9E9E9E)
                )
            }
        }
    }
}

@Composable
private fun ProfileMenuCard(
    onEditProfile: () -> Unit,
    onSettings: () -> Unit,
    onLogout: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            ProfileMenuItem(
                icon = Icons.Filled.Person,
                iconColor = Color(0xFF00BCD4),
                title = "Editar perfil",
                onClick = onEditProfile
            )

            Divider(color = Color(0xFFEEEEEE), thickness = 1.dp)

            ProfileMenuItem(
                icon = Icons.Filled.Settings,
                iconColor = Color(0xFF00BCD4),
                title = "Configuración",
                onClick = onSettings
            )

            Divider(color = Color(0xFFEEEEEE), thickness = 1.dp)

            ProfileMenuItem(
                icon = Icons.Filled.ExitToApp,
                iconColor = Color(0xFFF44336),
                title = "Cerrar sesión",
                onClick = onLogout
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
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = iconColor,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = Icons.Filled.ArrowForward,
            contentDescription = null,
            tint = Color(0xFFBDBDBD),
            modifier = Modifier.size(20.dp)
        )
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
    var emailError by remember { mutableStateOf<String?>(null) }
    var phoneError by remember { mutableStateOf<String?>(null) }
    var showSuccess by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

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
            .background(MaterialTheme.colorScheme.surface)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Editar Perfil",
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Foto de perfil con botón de cámara
            Box(contentAlignment = Alignment.BottomEnd) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .border(4.dp, MaterialTheme.colorScheme.surface, CircleShape)
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
                
                // Botón de cámara
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF00BCD4))
                        .clickable { /* TODO: Implementar cambio de foto */ },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.CameraAlt,
                        contentDescription = "Cambiar foto",
                        tint = MaterialTheme.colorScheme.surface,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Campos de texto
            EditProfileTextField(
                label = "Nombre completo",
                value = fullName,
                onValueChange = { fullName = it },
                placeholder = "María González",
                enabled = false  // Nombre no editable
            )

            Spacer(modifier = Modifier.height(16.dp))

            EditProfileTextField(
                label = "Correo electrónico",
                value = email,
                onValueChange = { 
                    email = it
                    // Validar en tiempo real
                    emailError = if (it.isNotEmpty() && !it.contains("@")) {
                        "El correo debe contener @"
                    } else {
                        null
                    }
                },
                placeholder = "maria.gonzalez@email.com",
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Email,
                errorMessage = emailError
            )

            Spacer(modifier = Modifier.height(16.dp))

            EditProfileTextField(
                label = "Número de teléfono",
                value = phone,
                onValueChange = { newValue ->
                    // Solo permitir números y limitar a 9 dígitos
                    val filtered = newValue.filter { it.isDigit() }.take(9)
                    phone = filtered
                    
                    // Validar en tiempo real
                    phoneError = when {
                        filtered.isEmpty() -> null
                        !filtered.startsWith("9") -> "El teléfono debe empezar con 9"
                        filtered.length < 9 -> "El teléfono debe tener 9 dígitos"
                        else -> null
                    }
                },
                placeholder = "961345678",
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Phone,
                errorMessage = phoneError
            )

            Spacer(modifier = Modifier.height(16.dp))

            EditProfileTextField(
                label = "Dirección",
                value = address,
                onValueChange = { address = it },
                placeholder = "Calle Mayor 123, 4º B 28001 Lima, Perú",
                minLines = 3
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Mostrar mensaje de éxito si existe
            if (showSuccess) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7FA)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = "Éxito",
                            tint = Color(0xFF00BCD4),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "✓ Perfil actualizado",
                            fontSize = 13.sp,
                            color = Color(0xFF00BCD4),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Verificar si hay errores en los campos
            val hasValidationErrors = emailError != null || phoneError != null

            // Botón Guardar cambios
            Button(
                onClick = {
                    // Validar que no haya errores en los campos
                    var hasErrors = false
                    
                    // Validar correo
                    if (email.isEmpty() || !email.contains("@")) {
                        emailError = "El correo debe contener @"
                        hasErrors = true
                    }
                    
                    // Validar teléfono
                    if (phone.length != 9) {
                        phoneError = "El teléfono debe tener 9 dígitos"
                        hasErrors = true
                    } else if (!phone.startsWith("9")) {
                        phoneError = "El teléfono debe empezar con 9"
                        hasErrors = true
                    }
                    
                    // Si hay errores, no continuar
                    if (hasErrors) {
                        return@Button
                    }
                    
                    // Si todo está bien, guardar
                    viewModel.updatePatientProfile(
                        fullName = fullName,
                        email = email,
                        phone = phone,
                        address = address
                    )
                    
                    // Mostrar éxito
                    showSuccess = true
                    
                    // Navegar después de 1.5 segundos
                    coroutineScope.launch {
                        kotlinx.coroutines.delay(1500)
                        navController.popBackStack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .then(
                        if (hasValidationErrors) {
                            Modifier.border(2.dp, Color(0xFFD32F2F), RoundedCornerShape(12.dp))
                        } else {
                            Modifier
                        }
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (hasValidationErrors) Color(0xFFFFCDD2) else Color(0xFF00BCD4),
                    contentColor = if (hasValidationErrors) Color(0xFFD32F2F) else MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Guardar cambios",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Botón Cancelar
            OutlinedButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                Text(
                    text = "Cancelar",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun EditProfileTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    minLines: Int = 1,
    enabled: Boolean = true,
    keyboardType: androidx.compose.ui.text.input.KeyboardType = androidx.compose.ui.text.input.KeyboardType.Text,
    errorMessage: String? = null
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = androidx.compose.ui.text.input.ImeAction.Done
            ),
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color(0xFFBDBDBD),
                    fontSize = 14.sp
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (errorMessage != null) Color(0xFFD32F2F) else Color(0xFF00BCD4),
                unfocusedBorderColor = if (errorMessage != null) Color(0xFFD32F2F) else Color(0xFFE0E0E0),
                disabledBorderColor = Color(0xFFE0E0E0),
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                disabledContainerColor = Color(0xFFF5F5F5),
                disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            shape = RoundedCornerShape(12.dp),
            minLines = minLines,
            maxLines = if (minLines > 1) minLines else 1,
            singleLine = minLines == 1,
            isError = errorMessage != null
        )
        
        // Mostrar mensaje de error debajo del campo
        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorMessage,
                fontSize = 12.sp,
                color = Color(0xFFD32F2F)
            )
        }
    }
}

