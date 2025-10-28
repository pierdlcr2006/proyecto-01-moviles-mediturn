# 📅 Día 1: Planificación y Diseño

> **Proyecto:** MediTurn - Sistema de Gestión de Citas Médicas  
> **Fecha:** Día 1 de desarrollo  
> **Estado:** ✅ Completado

---

## 🎯 Objetivo del Día

Definir el alcance del proyecto, estructurar las épicas e historias de usuario, asignar roles dentro del equipo y desarrollar el prototipo visual en **Figma** que representará el flujo principal de la aplicación **MediTurn**.

---

## Asignación de Roles del Equipo

| Integrante | Rol Principal | Responsabilidades |
|------------|---------------|-------------------|
| **Elver Antony Cholan García** | 🎨 Diseñador UI/UX | Encargado del diseño visual y la experiencia del usuario. Responsable del prototipo en Figma y del desarrollo de la interfaz en Jetpack Compose. |
| **Piero De La Cruz Palpa** | 💻 Líder Técnico y Desarrollador Backend | Responsable de la estructura técnica del proyecto, configuración de la base de datos, manejo de conexiones, lógica de negocio y supervisión de la integración general del sistema. |
| **Steven Manuel Saldaña Meléndez** | 🧠 Documentador y Tester | Encargado de la documentación técnica del proyecto y de la validación de funcionalidades mediante pruebas de calidad y usabilidad. |

---

## Enlaces Importantes

- **[Figma MediTurn](https://www.figma.com/design/y3Wko1GvMvOxZcUJGbpxxO/Proyecto--MediTurn-%E2%80%93-Citas-M%C3%A9dicas?node-id=0-1&p=f&t=cEaMzKkkebo39xds-0)**
- **[GitHub MediTurn](https://github.com/pierdlcr2006/proyecto-01-moviles-mediturn)**

---

## Épicas del Proyecto

| 🆔 Código | Descripción de la Épica | Prioridad | Objetivo Principal |
|-----------|-------------------------|-----------|-------------------|
| **EP-01** | Gestión de usuarios y acceso | Alta | Permitir que los pacientes puedan registrarse, iniciar sesión y gestionar su información personal dentro de la plataforma. |
| **EP-02** | Búsqueda y visualización de médicos | Alta | Facilitar al paciente la búsqueda de médicos por especialidad, nombre o disponibilidad, mostrando detalles relevantes de cada profesional. |
| **EP-03** | Gestión de citas médicas | Alta | Permitir al paciente agendar, modificar o cancelar citas médicas según su disponibilidad. |
| **EP-04** | Historial y recordatorios de citas | Media | Ofrecer un registro completo de citas pasadas y próximas, además de notificaciones recordatorias. |
| **EP-05** | Panel principal y navegación general | Media | Proporcionar un menú central que permita al paciente acceder a las secciones del sistema de manera intuitiva. |

---

## Historias de Usuario y Criterios de Aceptación

### EP-01: Gestión de Usuarios y Acceso

| 🆔 Código | Detalle | Prioridad | Esfuerzo | Criterio de Aceptación |
|-----------|---------|-----------|----------|------------------------|
| **HU-01** | Yo como paciente debo poder registrarme en la aplicación para acceder a los servicios médicos. | Alta | 8 | El sistema permite registrar usuarios nuevos y validar que no existan duplicados. |
| **HU-02** | Yo como paciente debo poder iniciar sesión con mis credenciales registradas. | Alta | 5 | El login valida credenciales y redirige correctamente al panel principal. |
| **HU-03** | Yo como paciente debo poder actualizar mis datos personales desde mi perfil. | Media | 3 | El sistema guarda los cambios del perfil y los muestra actualizados en la interfaz. |

### EP-02: Búsqueda y Visualización de Médicos

| 🆔 Código | Detalle | Prioridad | Esfuerzo | Criterio de Aceptación |
|-----------|---------|-----------|----------|------------------------|
| **HU-04** | Yo como paciente debo poder buscar médicos por especialidad o nombre. | Alta | 8 | El sistema filtra y muestra médicos según los criterios de búsqueda ingresados. |
| **HU-05** | Yo como paciente debo poder visualizar el perfil y disponibilidad de un médico seleccionado. | Alta | 5 | Al seleccionar un médico, se muestra su perfil, especialidad y horarios disponibles. |

### EP-03: Gestión de Citas Médicas

| 🆔 Código | Detalle | Prioridad | Esfuerzo | Criterio de Aceptación |
|-----------|---------|-----------|----------|------------------------|
| **HU-06** | Yo como paciente debo poder agendar una cita médica seleccionando fecha, hora y motivo. | Alta | 13 | El sistema registra citas válidas y muestra una confirmación exitosa. |
| **HU-07** | Yo como paciente debo poder reprogramar o cancelar una cita previamente agendada. | Alta | 8 | El sistema actualiza o elimina correctamente la cita y refleja los cambios en el calendario. |

### EP-04: Historial y Recordatorios de Citas

| 🆔 Código | Detalle | Prioridad | Esfuerzo | Criterio de Aceptación |
|-----------|---------|-----------|----------|------------------------|
| **HU-08** | Yo como paciente debo poder visualizar mis citas pasadas y próximas. | Media | 5 | El sistema muestra citas organizadas por estado (próximas y pasadas). |
| **HU-09** | Yo como paciente debo recibir recordatorios automáticos antes de mis citas. | Media | 3 | El sistema genera notificaciones previas con la fecha y hora de la cita. |

### EP-05: Panel Principal y Navegación General

| 🆔 Código | Detalle | Prioridad | Esfuerzo | Criterio de Aceptación |
|-----------|---------|-----------|----------|------------------------|
| **HU-10** | Yo como paciente debo poder acceder a todas las secciones del sistema desde un menú principal. | Media | 5 | El panel principal muestra accesos funcionales a Inicio, Búsqueda, Agendar Cita, Mis Citas y Perfil. |

---

## Prototipo en Figma

Se desarrolló el prototipo visual completo de la aplicación, incluyendo las siguientes pantallas principales:

- ✓ **Inicio** - Pantalla de bienvenida
- ✓ **Búsqueda** - Sistema de filtros por especialidad
- ✓ **Detalle del Médico** - Perfil y disponibilidad
- ✓ **Agendar Cita** - Formulario de reserva
- ✓ **Mis Citas** - Historial de citas
- ✓ **Perfil** - Datos del paciente

---

## Resultado del Día

Se completó exitosamente:

1. ✅ Definición de **5 épicas** principales del proyecto
2. ✅ Elaboración de **10 historias de usuario** con criterios de aceptación
3. ✅ Asignación clara de **roles y responsabilidades** del equipo
4. ✅ Creación del **repositorio en GitHub**
5. ✅ Desarrollo del **prototipo visual completo en Figma**
6. ✅ Diseño de todas las pantallas principales de la aplicación

---
---

# 📅 Día 2: Configuración del Proyecto y Estructura Base

> **Proyecto:** MediTurn - Sistema de Gestión de Citas Médicas  
> **Fecha:** Día 2 de desarrollo  
> **Estado:** ✅ Completado

---

## 🎯 Objetivo del Día

Establecer la base técnica del proyecto en **Kotlin con Jetpack Compose**, definiendo la estructura de paquetes y configurando la navegación principal entre pantallas. Organizar las ramas de trabajo por integrante y aplicar convenciones de commits.

---

## Tecnologías y Herramientas

| Categoría | Tecnología |
|-----------|------------|
| **Lenguaje** | Kotlin |
| **Framework UI** | Jetpack Compose |
| **Navegación** | Navigation Compose |
| **Control de Versiones** | Git / GitHub |

---

## 📁 Estructura de Paquetes

```
com.mediturn.app/
├── ui/              # Pantallas y componentes visuales
├── model/           # Modelos de datos
├── navigation/      # Sistema de navegación
├── data/            # Repositorios y fuentes de datos
└── util/            # Utilidades y helpers
```

---

## Estrategia de Ramas

### Rama: `develop_front`

**Contenido:**
- Pantallas: Home, Listado de médicos, Detalle, Agendar cita, Mis citas
- Componentes visuales según Figma
- LazyColumn con Cards

### Rama: `develop_back`

**Contenido:**
- Modelos: `Doctor`, `Slot`, `Appointment`, `Patient`
- Repositorio local con datos simulados
- Funciones: búsqueda, filtrado y gestión de citas

---

## Navegación Principal

**Rutas configuradas:**
- `/home` - Pantalla principal
- `/doctors` - Listado de médicos
- `/doctor_detail/{id}` - Detalle del médico
- `/schedule_appointment` - Agendar cita
- `/my_appointments` - Mis citas
- `/profile` - Perfil del usuario

---

## Resultado del Día

1. ✅ Proyecto Kotlin con Jetpack Compose configurado
2. ✅ Estructura de paquetes modular implementada
3. ✅ Sistema de navegación funcional
4. ✅ Ramas de desarrollo organizadas
5. ✅ Convenciones de commits establecidas
6. ✅ Modelos de datos definidos

---
---

# 📅 Día 3: Desarrollo de Interfaz (UI/UX)

> **Proyecto:** MediTurn - Sistema de Gestión de Citas Médicas  
> **Fecha:** Día 3 de desarrollo  
> **Estado:** ✅ Completado

---

## 🎯 Objetivo del Día

Implementar la interfaz de usuario conforme al prototipo visual elaborado en Figma, aplicando los lineamientos de **Material Design 3** para garantizar coherencia, accesibilidad y una experiencia moderna.

---

## Trabajo Realizado

Durante esta jornada se implementaron las pantallas principales del proyecto MediTurn, trasladando el diseño creado en Figma al entorno real del proyecto desarrollado con Kotlin y Jetpack Compose.

Se utilizaron componentes nativos de Compose y principios de diseño responsivo para mantener fidelidad con el prototipo visual, asegurando una navegación fluida entre vistas.

---

## Pantallas Desarrolladas

| Pantalla | Descripción | Componentes Principales |
|----------|-------------|-------------------------|
| **Home** | Pantalla principal con buscador y accesos directos | SearchBar, Cards de acceso rápido (Especialidades, Mis Citas, Perfil) |
| **Listado de Médicos** | Visualización de médicos disponibles | LazyColumn con DoctorCard, filtros por especialidad |
| **Detalle de Médico** | Información completa del doctor | Perfil del médico, disponibilidad horaria, opción de teleconsulta |
| **Agendar Cita** | Formulario de reserva de citas | DatePicker, TimePicker, TextField para motivo |
| **Mis Citas / Calendario** | Gestión de citas del paciente | Tabs (Próximas/Pasadas), Cards de citas |

---

## Estilo Global Aplicado

Se implementó **Material Design 3** para lograr una apariencia moderna y consistente en toda la aplicación:

**Elementos de diseño:**
- ✓ Paleta de colores definida en Figma
- ✓ Tipografía Material (headline, body, label)
- ✓ Espaciado consistente (8dp, 16dp, 24dp)
- ✓ Elevación y sombras según Material 3
- ✓ Componentes con esquinas redondeadas
- ✓ Iconografía coherente

**Componentes reutilizables:**
```kotlin
@Composable
fun DoctorCard(doctor: Doctor, onClick: () -> Unit)

@Composable
fun AppointmentCard(appointment: Appointment)

@Composable
fun CustomSearchBar(query: String, onQueryChange: (String) -> Unit)
```

---

## ✅ Pruebas y Validaciones (Testing)

Como parte del control de calidad, se realizaron pruebas funcionales y visuales:

### Pruebas Realizadas

| Área | Validación | Resultado |
|------|------------|-----------|
| **Navegación** | Verificación del flujo entre pantallas con Navigation Compose | ✅ Correcto |
| **Interactividad** | Funcionamiento de botones y buscador | ✅ Correcto |
| **Diseño Visual** | Alineado, espaciado y tipografía según Figma | ✅ Correcto |
| **Responsividad** | Adaptación a diferentes tamaños de pantalla | ✅ Correcto |

### Ajustes Realizados

- Pequeños ajustes de margen y espaciado en la pantalla Home
- Corrección de alineación en cards de médicos
- Optimización de padding en formularios

**No se detectaron errores funcionales relevantes.**

---

## Entregables

1. ✅ Interfaz completa navegable entre todas las pantallas principales
2. ✅ Implementación visual fiel al diseño de Figma
3. ✅ Componentes reutilizables documentados
4. ✅ Estilo global con Material Design 3 aplicado
5. ✅ Navegación fluida y funcional

---

##Resultado del Día

Se completó la construcción de todas las pantallas principales y su navegación, logrando:

- **Interfaz 100% navegable** entre todas las secciones
- **Fidelidad visual** con el diseño de Figma
- **Coherencia** con los lineamientos de Material Design 3
- **Componentes reutilizables** para facilitar el mantenimiento
- **Experiencia de usuario** fluida y moderna

---
---

# 📅 Día 4: Lógica y Datos Simulados

> **Proyecto:** MediTurn - Sistema de Gestión de Citas Médicas  
> **Fecha:** Día 4 de desarrollo  
> **Estado:** ✅ Completado

---

## 🎯 Objetivo del Día

Conectar la interfaz de usuario con modelos y repositorios locales, simulando la gestión de datos de doctores, citas y pacientes para validar el flujo funcional de la aplicación.

---

## Trabajo Realizado

Durante esta etapa se integró la lógica interna del proyecto con la interfaz desarrollada previamente. Se crearon los modelos de datos principales y un repositorio local con información simulada, permitiendo probar la funcionalidad general de búsqueda y reserva sin necesidad de conexión a una base de datos real.

---

## 🗂️ Modelos de Datos Definidos

Se implementaron los siguientes modelos principales:

| Modelo | Propósito | Campos Principales |
|--------|-----------|-------------------|
| **Doctor** | Información del médico | `id`, `name`, `specialty`, `city`, `isTeleconsultAvailable`, `rating` |
| **Slot** | Horarios disponibles | `id`, `doctorId`, `dateTime`, `isAvailable` |
| **Appointment** | Datos de las citas | `id`, `doctorId`, `patientId`, `dateTime`, `reason`, `status` |
| **Patient** | Información del usuario | `id`, `name`, `email`, `phone`, `age` |

### Estructura de Ejemplo

```kotlin
data class Doctor(
    val id: Long,
    val name: String,
    val specialty: String,
    val city: String,
    val isTeleconsultAvailable: Boolean,
    val rating: Float
)

data class Appointment(
    val id: Long,
    val doctorId: Long,
    val patientId: Long,
    val dateTime: Long,
    val reason: String,
    val status: AppointmentStatus
)
```

---

## Repositorio Local con Datos Simulados

Se implementó un repositorio local mediante una fuente de datos embebida (DataSource) que simula la información de médicos y citas.

### Funcionalidades Implementadas

| Función | Descripción |
|---------|-------------|
| `getAllDoctors()` | Retorna lista completa de médicos simulados |
| `searchDoctorsByName(query)` | Búsqueda dinámica por nombre |
| `filterBySpecialty(specialty)` | Filtrado por especialidad médica |
| `getDoctorById(id)` | Obtiene detalles de un médico específico |
| `getAvailableSlots(doctorId)` | Retorna horarios disponibles |
| `createAppointment()` | Simula la creación de una cita |
| `getPatientAppointments(patientId)` | Lista de citas del paciente |

### Ejemplo de Datos Simulados

```kotlin
object DoctorDataSource {
    val doctors = listOf(
        Doctor(1, "Dr. García", "Cardiología", "Lima", true, 4.8f),
        Doctor(2, "Dra. Martínez", "Pediatría", "Lima", false, 4.9f),
        Doctor(3, "Dr. López", "Dermatología", "Callao", true, 4.7f)
    )
}
```

---

## Integración UI - Datos

Se conectó el repositorio con las pantallas de UI mediante el patrón Repository:

**Flujo de datos implementado:**
```
ViewModel → Repository → DataSource → UI
```

**Ejemplo de uso:**
```kotlin
@Composable
fun DoctorsListScreen(viewModel: MediTurnViewModel) {
    val doctors by viewModel.doctors.collectAsState()
    
    LazyColumn {
        items(doctors) { doctor ->
            DoctorCard(doctor = doctor)
        }
    }
}
```

---

## Búsqueda Dinámica Implementada

Se implementó búsqueda en tiempo real con las siguientes características:

- ✓ Búsqueda por nombre de médico
- ✓ Filtrado por especialidad
- ✓ Actualización inmediata de resultados
- ✓ Manejo de estado vacío

**Lógica de búsqueda:**
```kotlin
fun searchDoctors(query: String) {
    _doctors.value = if (query.isEmpty()) {
        repository.getAllDoctors()
    } else {
        repository.searchDoctorsByName(query)
    }
}
```

---

## Flujo Completo de Reserva de Cita

Se simuló el flujo completo desde la selección del médico hasta la confirmación:

1. ✓ Usuario selecciona médico del listado
2. ✓ Navega al detalle del médico
3. ✓ Visualiza horarios disponibles
4. ✓ Selecciona fecha y hora
5. ✓ Ingresa motivo de la cita
6. ✓ Confirma la reserva
7. ✓ Sistema simula creación y muestra confirmación
8. ✓ Cita aparece en "Mis Citas"

---

## ✅ Pruebas y Validaciones (Testing)

Para garantizar la correcta integración de la lógica con la interfaz, se realizaron pruebas manuales:

| Área | Validación | Resultado |
|------|------------|-----------|
| **Carga de Datos** | Datos del repositorio se muestran correctamente en UI | ✅ Correcto |
| **Búsqueda** | Funcionamiento del buscador por nombre y especialidad | ✅ Correcto |
| **Filtrado** | Filtros actualizan la lista en tiempo real | ✅ Correcto |
| **Flujo de Reserva** | Simulación completa de reserva de cita | ✅ Correcto |
| **Estados** | Manejo de estados (pendiente, confirmada) | ✅ Correcto |
| **Navegación** | Coherencia entre detalle y reserva | ✅ Correcto |

**Los tests confirmaron que el sistema responde correctamente y mantiene la coherencia entre datos simulados e interfaz.**

---

## Entregables

1. ✅ Modelos de datos implementados (Doctor, Slot, Appointment, Patient)
2. ✅ Repositorio local con datos simulados
3. ✅ Búsqueda funcional por especialidad y nombre
4. ✅ Filtrado dinámico en tiempo real
5. ✅ Flujo completo de reserva de cita simulada
6. ✅ Integración UI-Datos funcional

---

## Resultado del Día

Se logró conectar la interfaz con la capa de datos locales, obteniendo:

- **Aplicación funcional** con flujo completo de reserva
- **Búsqueda operativa** mediante información simulada
- **Navegación fluida** entre listado, detalle y reserva
- **Base sólida** para futuras integraciones con base de datos real o backend

La app ya permite navegar, buscar médicos y reservar citas de manera fluida, sirviendo como base para la implementación de funcionalidades avanzadas.

---
---

# 📅 Día 5: Funcionalidades Clave y Validaciones

> **Proyecto:** MediTurn - Sistema de Gestión de Citas Médicas  
> **Fecha:** Día 5 de desarrollo  
> **Estado:** ✅ Completado

---

## 🎯 Objetivo del Día

Implementar búsquedas, filtros, validaciones y gestión completa de citas (crear, reprogramar, cancelar). Realizar pruebas exhaustivas y pulir la aplicación para asegurar calidad y robustez.

---

##Trabajo Completado

### 1. Modelo de Datos Mejorado

**Archivo modificado:** `Appointment.kt`

Se agregaron **3 campos nuevos** al modelo de citas:

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `consultationType` | String | Tipo de consulta ("Presencial" / "Teleconsulta") |
| `reason` | String | Motivo de la cita |
| `rescheduleCount` | Int | Contador de reprogramaciones (para validar límite) |

**Cambios en Base de Datos:**
- Valores por defecto incluidos para mantener compatibilidad con datos existentes
- Base de datos actualizada a **versión 2** con `fallbackToDestructiveMigration`

---

### 2. Validaciones de Citas Implementadas

**Archivo modificado:** `MediturnRepository.kt`

#### 2.1 Función `hasTimeConflict()`

Valida que no existan **solapes de horarios** al:
- Crear nueva cita
- Reprogramar cita existente

**Lógica:** Verifica que no haya citas con diferencia menor a 1 hora (ventana de 60 minutos).

#### 2.2 Función `canRescheduleAppointment()`

Valida **4 condiciones** antes de permitir reprogramación:

1. ✓ La cita existe
2. ✓ No está marcada como pasada (`isPast = false`)
3. ✓ No está cancelada (`status != CANCELLED`)
4. ✓ Límite de reprogramaciones: Máximo 1 vez (`rescheduleCount < 1`)
5. ✓ Ventana de tiempo: Mínimo 12 horas de anticipación

**Retorna:** `Pair<Boolean, String>` con resultado y mensaje de error descriptivo.

---

### 3. ViewModel con Callbacks de Validación

**Archivo modificado:** `MediturnViewModel.kt`

#### Función `scheduleAppointment()` actualizada

**Parámetros nuevos:**
```kotlin
fun scheduleAppointment(
    doctorId: Long,
    dateTimeMillis: Long,
    consultationType: String,
    reason: String,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
)
```

**Validaciones ejecutadas:**
- Verifica conflicto de horarios con `hasTimeConflict()`
- Mensaje de error si hay solapamiento

#### Función `updateAppointment()` actualizada

**Parámetros nuevos:**
```kotlin
fun updateAppointment(
    appointmentId: Long,
    newDateTimeMillis: Long,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
)
```

**Validaciones ejecutadas:**
- Verifica si se puede reprogramar con `canRescheduleAppointment()`
- Valida conflicto de horarios (excluyendo la cita actual)
- Mensajes descriptivos por cada tipo de error

---

### 4. DAO Actualizado con Lógica de Reprogramación

**Archivo modificado:** `AppointmentDao.kt`

#### Función `updateDateTime()` mejorada

**Query SQL actualizada:**
```sql
UPDATE appointments SET 
    dateTime = :newDateTimeMillis, 
    rescheduleCount = rescheduleCount + 1, 
    status = 'RESCHEDULED' 
WHERE id = :appointmentId
```

**Acciones automáticas:**
- ✓ Actualiza fecha/hora
- ✓ Incrementa contador de reprogramaciones
- ✓ Cambia estado a `RESCHEDULED`

---

### 5. Pantalla de Agendar/Reprogramar con Validaciones

**Archivo modificado:** `ScheduleAppointmentScreen.kt`

#### Validaciones de campos implementadas

**Motivo de cita:**
- Mínimo: 5 caracteres
- Máximo: 200 caracteres
- Se aplica `trim()`

**Fecha y hora:**
- Debe ser futura (validación en tiempo real)
- Mensaje: *"Debes seleccionar una fecha y hora futura"*

**Prefill en reprogramación:**
- Carga automática de fecha, hora, modalidad y motivo
- `LaunchedEffect` detecta `existingAppointment` y prellena campos

#### Manejo de errores

- Estado `errorMessage` para mostrar validaciones
- Card roja con ícono de advertencia
- Mensajes descriptivos desde backend

#### Flujo de éxito/error

- **onSuccess:** Animación cargando → check → navegación
- **onError:** Detiene overlay, muestra mensaje, permite reintentar

---

### 6. Diálogo de Confirmación para Cancelar Citas

**Archivo modificado:** `AppointmentsScreen.kt`

#### AlertDialog implementado

**Título:** "Cancelar cita"

**Mensaje:** *"¿Estás seguro de que deseas cancelar esta cita? Esta acción no se puede deshacer."*

**Botones:**
- 🔴 **"Cancelar cita"** - Ejecuta `viewModel.cancelAppointment()`
- 🔵 **"No, mantener"** - Cierra diálogo

**Estado:**
```kotlin
showCancelDialog: Boolean
appointmentToCancel: Long?
```

**Flujo:** Botón "Cancelar" → muestra diálogo → confirma → elimina cita → actualiza lista en tiempo real

---

## 🗂️Validaciones Implementadas por Pantalla

### Mis Citas (Appointments)

- ✓ Separación tabs "Próximas" / "Pasadas" funcional
- ✓ Solo citas próximas muestran botones de acción
- ✓ Diálogo de confirmación obligatorio para cancelar
- ✓ Navegación a reprogramar pasa `appointmentId` correcto

### Agendar Cita (Crear)

- ✓ Campos requeridos: fecha, hora, modalidad, motivo
- ✓ Validación longitud motivo (5-200 caracteres)
- ✓ Fecha futura obligatoria
- ✓ No permite solapes de horarios (ventana 1 hora)
- ✓ Mensajes de error descriptivos

### Reprogramar Cita (Editar)

- ✓ Prefill completo de datos actuales
- ✓ Límite: solo 1 reprogramación por cita
- ✓ Ventana: mínimo 12 horas de anticipación
- ✓ No permite si está cancelada o pasada
- ✓ No permite solapes de horarios
- ✓ Actualiza cita existente (no crea nueva)
- ✓ Incrementa `rescheduleCount` automáticamente
- ✓ Cambia status a `RESCHEDULED`

### Cancelar Cita

- ✓ Diálogo de confirmación obligatorio
- ✓ Solo disponible en citas próximas
- ✓ Actualiza lista en tiempo real

---

## ✅ Compilación y Pruebas

**Resultado:** `BUILD SUCCESSFUL in 3m 9s`

### Advertencias (no críticas)

- Deprecaciones de `HorizontalPager` (Accompanist → Foundation)
- Deprecaciones de íconos `AutoMirrored`
- Warnings de Room sobre índices en FKs

**Sin errores de compilación ni runtime**

---

##Resultado Funcional

El sistema ahora:

1. ✅ Valida conflictos de horarios al agendar/reprogramar
2. ✅ Limita reprogramaciones a 1 vez por cita
3. ✅ Requiere 12 horas de anticipación para reprogramar
4. ✅ Pide confirmación antes de cancelar
5. ✅ Guarda y prellena modalidad y motivo
6. ✅ Muestra mensajes de error descriptivos
7. ✅ Mantiene integridad de datos en BD
8. ✅ Compila sin errores

---
---
