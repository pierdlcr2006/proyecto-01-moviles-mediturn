# 🏥 MediTurn – Día 1: Planificación y Diseño (Figma)

## 🎯 **Objetivo del día**
Definir el alcance del proyecto, estructurar las épicas e historias de usuario, asignar roles dentro del equipo y desarrollar el prototipo visual en **Figma** que representará el flujo principal de la aplicación **MediTurn**.

---

[Asignación de Roles del Equipo]

| Integrante | Rol Principal | Responsabilidades |
|---------------|------------------|-----------------------|
| **Elver Antony Cholan García** | 🎨 Diseñador UI/UX | Encargado del diseño visual y la experiencia del usuario. Responsable del prototipo en Figma y del desarrollo de la interfaz en Jetpack Compose. |
| **Piero De La Cruz Palpa** | 💻 Líder Técnico y Desarrollador Backend | Responsable de la estructura técnica del proyecto, configuración de la base de datos, manejo de conexiones, lógica de negocio y supervisión de la integración general del sistema. |
| **Steven Manuel Saldaña Meléndez** | 🧠 Documentador y Tester | Encargado de la documentación técnica del proyecto y de la validación de funcionalidades mediante pruebas de calidad y usabilidad. |

---

##**Enlaces Importantes**

- [Figma MediTurn] 
(https://www.figma.com/design/y3Wko1GvMvOxZcUJGbpxxO/Proyecto--MediTurn-%E2%80%93-Citas-M%C3%A9dicas?node-id=0-1&p=f&t=cEaMzKkkebo39xds-0)  

- [GitHub MediTurn]
(https://github.com/pierdlcr2006/proyecto-01-moviles-mediturn)

---

{Épicas del Proyecto}

| 🆔 CÓD. | Descripción de la Épica | Prioridad | Objetivo Principal |
|---------|-----------------------------|--------------|------------------------|
| **EP-01** | Gestión de usuarios y acceso | Alta | Permitir que los pacientes puedan registrarse, iniciar sesión y gestionar su información personal dentro de la plataforma. |
| **EP-02** | Búsqueda y visualización de médicos | Alta | Facilitar al paciente la búsqueda de médicos por especialidad, nombre o disponibilidad, mostrando detalles relevantes de cada profesional. |
| **EP-03** | Gestión de citas médicas | Alta | Permitir al paciente agendar, modificar o cancelar citas médicas según su disponibilidad. |
| **EP-04** | Historial y recordatorios de citas | Media | Ofrecer un registro completo de citas pasadas y próximas, además de notificaciones recordatorias. |
| **EP-05** | Panel principal y navegación general | Media | Proporcionar un menú central que permita al paciente acceder a las secciones del sistema de manera intuitiva. |

---

[Historias de Usuario y Criterios de Aceptación]

### **EP-01 – Gestión de Usuarios y Acceso**

| 🆔 Código | Detalle | Prioridad | Esfuerzo | Criterio de Aceptación |
|-----------|-------------|--------------|-------------|----------------------------|
| **HU-01** | Yo como paciente debo poder registrarme en la aplicación para acceder a los servicios médicos. | Alta | 8 | El sistema permite registrar usuarios nuevos y validar que no existan duplicados. |
| **HU-02** | Yo como paciente debo poder iniciar sesión con mis credenciales registradas. | Alta | 5 | El login valida credenciales y redirige correctamente al panel principal. |
| **HU-03** | Yo como paciente debo poder actualizar mis datos personales desde mi perfil. | Media | 3 | El sistema guarda los cambios del perfil y los muestra actualizados en la interfaz. |

---

### **EP-02 – Búsqueda y Visualización de Médicos**

| 🆔 Código | Detalle | Prioridad | Esfuerzo | Criterio de Aceptación |
|-----------|-------------|--------------|-------------|----------------------------|
| **HU-04** | Yo como paciente debo poder buscar médicos por especialidad o nombre. | Alta | 8 | El sistema filtra y muestra médicos según los criterios de búsqueda ingresados. |
| **HU-05** | Yo como paciente debo poder visualizar el perfil y disponibilidad de un médico seleccionado. | Alta | 5 | Al seleccionar un médico, se muestra su perfil, especialidad y horarios disponibles. |

---

### **EP-03 – Gestión de Citas Médicas**

| 🆔 Código | Detalle | Prioridad | Esfuerzo | Criterio de Aceptación |
|-----------|-------------|--------------|-------------|----------------------------|
| **HU-06** | Yo como paciente debo poder agendar una cita médica seleccionando fecha, hora y motivo. | Alta | 13 | El sistema registra citas válidas y muestra una confirmación exitosa. |
| **HU-07** | Yo como paciente debo poder reprogramar o cancelar una cita previamente agendada. | Alta | 8 | El sistema actualiza o elimina correctamente la cita y refleja los cambios en el calendario. |

---

### **EP-04 – Historial y Recordatorios de Citas**

| 🆔 Código |  Detalle | Prioridad | Esfuerzo | Criterio de Aceptación |
|-----------|-------------|--------------|-------------|----------------------------|
| **HU-08** | Yo como paciente debo poder visualizar mis citas pasadas y próximas. | Media | 5 | El sistema muestra citas organizadas por estado (próximas y pasadas). |
| **HU-09** | Yo como paciente debo recibir recordatorios automáticos antes de mis citas. | Media | 3 | El sistema genera notificaciones previas con la fecha y hora de la cita. |

---

### **EP-05 – Panel Principal y Navegación General**

| 🆔 Código |  Detalle | Prioridad | Esfuerzo | Criterio de Aceptación |
|-----------|-------------|--------------|-------------|----------------------------|
| **HU-10** | Yo como paciente debo poder acceder a todas las secciones del sistema desde un menú principal. | Media | 5 | El panel principal muestra accesos funcionales a Inicio, Búsqueda, Agendar Cita, Mis Citas y Perfil. |

---

 [✅ Resultado del Día]
Se completó la definición de las **épicas**, **historias de usuario** y **criterios de aceptación**.  
Además, se creó el **repositorio** y se avanzó en el **diseño del prototipo visual en Figma**, incluyendo las pantallas principales:  
**Inicio**, **Búsqueda**, **Detalle del Médico**, **Agendar Cita**, **Mis Citas** y **Perfil**.

-- --
-- --

## Día 2 – Configuración del Proyecto y Estructura Base

🎯[Objetivo del día]
Establecer la base técnica del proyecto en **Kotlin con Jetpack Compose**, definiendo la estructura de paquetes (`ui`, `model`, `navigation`, `data`, `util`) y configurando la navegación principal entre pantallas.  
Se organizaron las ramas de trabajo por integrante y se aplicaron convenciones de commits para un flujo de desarrollo ordenado.

[Proyecto en Kotlin]
- Lenguaje: Kotlin  
- UI: Jetpack Compose  
- Estructura de paquetes: `ui`, `model`, `navigation`, `data`, `util`  
- Navegación principal configurada entre pantallas  

[Ramas del Proyecto]

#### develop_front
- Contiene todas las pantallas y componentes visuales según Figma.  
- Pantallas principales:  
  - **Home:** buscador y atajos  
  - **Listado de médicos:** LazyColumn con Cards  
  - **Detalle de médico:** perfil, disponibilidad, teleconsulta  
  - **Agendar cita:** formularios, selector de fecha/hora  
  - **Mis citas / Calendario**

#### develop_back
- Contiene modelos, repositorios y simulación de datos.  
- Modelos: `Doctor`, `Slot`, `Appointment`, `Patient`  
- Repositorio local con datos simulados (JSON o DataSource)  
- Funciones: búsqueda, filtrado y gestión de citas

--
--

## DÍA 3 – Desarrollo de Interfaz (UI/UX)
🎯[Objetivo del día]

Implementar la interfaz de usuario conforme al prototipo visual elaborado en Figma, aplicando los lineamientos de Material Design 3 para garantizar coherencia, accesibilidad y una experiencia moderna.

[Descripción del trabajo realizado]

Durante esta jornada se implementaron las pantallas principales del proyecto MediTurn, trasladando el diseño creado en Figma al entorno real del proyecto desarrollado con Kotlin y Jetpack Compose.
Se utilizaron componentes nativos de Compose y principios de diseño responsivo para mantener fidelidad con el prototipo visual, asegurando una navegación fluida entre vistas.

[Pantallas desarrolladas]:

Home: contiene el buscador principal y accesos directos a Especialidades, Mis Citas y Perfil.

Listado de Médicos: implementado con LazyColumn y Card para mostrar datos básicos de cada médico.

Detalle de Médico: muestra información del doctor, su disponibilidad y opción de teleconsulta.

Agendar Cita: formulario con selector de fecha, hora y motivo de la cita.

Mis Citas / Calendario: permite visualizar citas próximas y pasadas.

Estilo global: se aplicó Material Design 3 para lograr una apariencia moderna y consistente.

El diseño final del Figma se implementó fielmente en Compose, respetando colores, tipografía y jerarquía visual definidos en el prototipo.

[Pruebas y validaciones (Testing)]

Como parte del control de calidad, se realizaron pruebas funcionales y visuales para asegurar la correcta interacción y consistencia del diseño:

Verificación de la navegación entre pantallas utilizando Navigation Compose.

Comprobación del funcionamiento de botones y buscador.

Validación del alineado, espaciado y tipografía según el diseño de Figma.

No se detectaron errores funcionales relevantes; únicamente se realizaron pequeños ajustes de margen y espaciado en la pantalla Home.

[Entregables]

Interfaz completa navegable entre pantallas principales.

Implementación visual basada en el diseño de Figma.

✅ RESULTADO DEL DÍA

Se completó la construcción de todas las pantallas principales y su navegación, logrando una interfaz totalmente navegable, fiel al diseño de Figma y visualmente coherente con los lineamientos de Material Design 3.

--
--

## DÍA 4 – Lógica y Datos Simulados
🎯[Objetivo]

Conectar la interfaz de usuario con modelos y repositorios locales, simulando la gestión de datos de doctores, citas y pacientes para validar el flujo funcional de la aplicación.

[Descripción del trabajo realizado]

Durante esta etapa se integró la lógica interna del proyecto con la interfaz desarrollada previamente.
Se crearon los modelos de datos principales y un repositorio local con información simulada, permitiendo probar la funcionalidad general de búsqueda y reserva sin necesidad de conexión a una base de datos real.

[Modelos definidos]:

Doctor → información del médico (nombre, especialidad, ciudad, teleconsulta).

Slot → horarios disponibles del doctor.

Appointment → datos de las citas (paciente, fecha, hora, motivo).

Patient → información básica del usuario/paciente.

[Principales implementaciones]:

Creación de un repositorio local mediante una fuente de datos (DataSource) o estructura JSON embebida.

Conexión del repositorio con las pantallas de UI mediante funciones que proveen los datos simulados.

Implementación de búsqueda dinámica por nombre y especialidad, actualizando los resultados en tiempo real.

Simulación del flujo completo de reserva de cita, desde la selección del médico hasta la confirmación del turno.

Esta etapa permitió validar el comportamiento funcional de la aplicación sin necesidad de usar bases de datos externas ni backend.


 [Pruebas y validaciones (Testing)]

Para garantizar la correcta integración de la lógica con la interfaz, se realizaron pruebas manuales y funcionales enfocadas en los siguientes aspectos:

Carga correcta de datos desde el repositorio local.

Funcionamiento del buscador por nombre y especialidad.

Fluidez del flujo de reserva de citas simuladas.

Comprobación del manejo básico de estados (por ejemplo, citas pendientes o confirmadas).

Navegación coherente entre pantallas de detalle y reserva.

Los tests confirmaron que el sistema responde correctamente a las búsquedas y mantiene la coherencia entre los datos simulados y la interfaz.

[Entregables]

Modelos de datos implementados (Doctor, Slot, Appointment, Patient).

Repositorio local con datos simulados.

Búsqueda funcional por especialidad y nombre.

Flujo completo de reserva de cita simulada.

✅ RESULTADO DEL DÍA

Se logró conectar la interfaz con la capa de datos locales, obteniendo una aplicación funcional con flujo completo de reserva y búsqueda operativa mediante información simulada.
La app ya permite navegar, buscar médicos y reservar citas de manera fluida, sirviendo como base para futuras integraciones con una base de datos real o backend.

Aplicación con estructura Material 3 sin conexión a datos reales.

-- --
-- --

# INFORME DÍA 5: FUNCIONALIDADES CLAVE Y PULIDO

## 🎯 OBJETIVO DEL DÍA 5
Implementar búsquedas, filtros, validaciones y gestión completa de citas (crear, reprogramar, cancelar).  
Probar la aplicación y revisar el código para asegurar calidad y robustez.

---

## 1. MODELO DE DATOS ACTUALIZADO
**Archivo:** `Appointment.kt`

- Agregados campos:
  - `consultationType` (Presencial / Teleconsulta)
  - `reason` (Motivo de la cita)
  - `rescheduleCount` (Contador de reprogramaciones)
- Valores por defecto configurados para mantener compatibilidad con la base de datos existente.  
- Versión de BD incrementada a **2** con migración destructiva automática.

---

## 2. VALIDACIONES DE CITAS

### 2.1 Crear Cita (Agendar)
**Validaciones implementadas:**
- Campo motivo: mínimo **5** caracteres, máximo **200** caracteres.  
- Fecha y hora futuras (no permitir fechas pasadas).  
- Detección de solapes: no permitir agendar si ya existe cita en ventana de **1 hora**.  
- Validación de disponibilidad del doctor (implementada en UI).  
- Guardado de modalidad (Presencial / Teleconsulta) y motivo.

**Manejo de errores:**
- Mensajes claros en card roja con ícono de advertencia.  
- Botón deshabilitado hasta completar los campos requeridos.  
- Feedback visual inmediato.

---

### 2.2 Reprogramar Cita
**Validaciones implementadas:**
- Límite de **1** reprogramación por cita (`rescheduleCount <= 1`).  
- Ventana de tiempo: no permitir reprogramar con menos de **12 horas** de anticipación.  
- No reprogramar citas pasadas o canceladas.  
- Detección de solapes (excluyendo la cita actual).  
- Fecha futura obligatoria.  
- Prefill automático de datos existentes (fecha, hora, modalidad, motivo).

**Contador de reprogramaciones:**
- Se incrementa automáticamente en BD al reprogramar.  
- Estado cambia a **RESCHEDULED** en la base de datos.

---

### 2.3 Cancelar Cita
**Validaciones implementadas:**
- Diálogo de confirmación obligatorio antes de cancelar.  
- Solo permitido en citas próximas (no pasadas).  
- Botón de confirmación en rojo para indicar acción destructiva.  
- Opción de “No, mantener” para cancelar la acción.

---

## 3. REPOSITORY Y DAO ACTUALIZADOS

**Nuevos métodos en Repository:**
- `hasTimeConflict()` → Verifica solapes de horario (ventana de 1 hora).  
- `canRescheduleAppointment()` → Valida todas las reglas de reprogramación.  
- `scheduleAppointment()` → Actualizado para recibir `consultationType` y `reason`.  
- `updateAppointment()` → Solo actualiza fecha/hora e incrementa `rescheduleCount`.

**Nuevos métodos en DAO:**
- `updateDateTime()` → Actualiza fecha, incrementa contador y cambia estado a `RESCHEDULED`.

---

## 4. VIEWMODEL CON CALLBACKS

**Firmas actualizadas:**
```kotlin
fun scheduleAppointment(
    doctorId, dateTimeMillis, consultationType, reason,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
)

fun updateAppointment(
    appointmentId, newDateTimeMillis,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
)

Validaciones ejecutadas en ViewModel:

Verifica solapes antes de crear/actualizar
Verifica reglas de reprogramación
Maneja excepciones y las convierte en mensajes para el usuario
## 5. INTERFAZ DE USUARIO
Schedul## INFORME DÍA 5 - FUNCIONALIDADES CLAVE Y VALIDACIONES
OBJETIVO DEL DÍA 5
Implementar búsquedas, filtros, validaciones y gestión completa de citas (crear, reprogramar, cancelar). Realizar pruebas y pulir la aplicación.

[TRABAJO COMPLETADO]
## 1. MODELO DE DATOS MEJORADO
Archivo modificado: 
Appointment.kt

Se agregaron 3 campos nuevos al modelo de citas:

consultationType: Tipo de consulta ("Presencial" / "Teleconsulta")
reason: Motivo de la cita
rescheduleCount: Contador de reprogramaciones (para validar límite)
Valores por defecto incluidos para mantener compatibilidad con datos existentes.

Base de datos actualizada a versión 2 con fallbackToDestructiveMigration para evitar conflictos.

## 2. VALIDACIONES DE CITAS IMPLEMENTADAS
Archivo modificado: 
MediturnRepository.kt

Función 
hasTimeConflict()
Valida que no existan solapes de horarios al:

Crear nueva cita
Reprogramar cita existente
Lógica: Verifica que no haya citas con diferencia menor a 1 hora (ventana de 60 minutos).

Función 
canRescheduleAppointment()
Valida 4 condiciones antes de permitir reprogramación:

La cita existe
No está marcada como pasada (isPast = false)
No está cancelada (status != CANCELLED)
Límite de reprogramaciones: Máximo 1 vez (rescheduleCount < 1)
Ventana de tiempo: Mínimo 12 horas de anticipación
Retorna: Par (Boolean, String) con resultado y mensaje de error descriptivo.

## 3. VIEWMODEL CON CALLBACKS DE VALIDACIÓN
Archivo modificado: 
MediturnViewModel.kt

Función 
scheduleAppointment()
 actualizada
Parámetros nuevos:

consultationType: String
reason: String
onSuccess: () -> Unit
onError: (String) -> Unit
Validaciones ejecutadas:

Verifica conflicto de horarios con 
hasTimeConflict()
Mensaje de error si hay solapamiento
Función 
updateAppointment()
 actualizada
Parámetros nuevos:

onSuccess: () -> Unit
onError: (String) -> Unit
Validaciones ejecutadas:

Verifica si se puede reprogramar con 
canRescheduleAppointment()
Valida conflicto de horarios (excluyendo la cita actual)
Mensajes descriptivos por cada tipo de error
## 4. DAO ACTUALIZADO CON LÓGICA DE REPROGRAMACIÓN
Archivo modificado: 
AppointmentDao.kt

Función 
updateDateTime()
 mejorada
Query SQL actualizada:

sql
UPDATE appointments SET 
    dateTime = :newDateTimeMillis, 
    rescheduleCount = rescheduleCount + 1, 
    status = 'RESCHEDULED' 
WHERE id = :appointmentId
Acciones automáticas:

Actualiza fecha/hora
Incrementa contador de reprogramaciones
Cambia estado a RESCHEDULED
## 5. PANTALLA DE AGENDAR/REPROGRAMAR CON VALIDACIONES
Archivo modificado: 
ScheduleAppointmentScreen.kt

[Validaciones de campos implementadas]
Motivo de cita:
Mínimo: 5 caracteres
Máximo: 200 caracteres
Se aplica trim()
Fecha y hora:
Debe ser futura (validación en tiempo real)
Mensaje: "Debes seleccionar una fecha y hora futura"
Prefill en reprogramación:
Carga automática de fecha, hora, modalidad y motivo
LaunchedEffect detecta existingAppointment y prellenó campos
Manejo de errores
Estado errorMessage para mostrar validaciones
Card roja con ícono de advertencia
Mensajes descriptivos desde backend
Flujo de éxito/error
onSuccess: Animación cargando → check → navegación
onError: Detiene overlay, muestra mensaje, permite reintentar
6. DIÁLOGO DE CONFIRMACIÓN PARA CANCELAR CITAS
Archivo modificado: 
AppointmentsScreen.kt

AlertDialog implementado
Título: "Cancelar cita"

Mensaje: "¿Estás seguro de que deseas cancelar esta cita? Esta acción no se puede deshacer."

Botones:

"Cancelar cita" (rojo): Ejecuta 
viewModel.cancelAppointment()
"No, mantener" (turquesa): Cierra diálogo
Estado:

showCancelDialog: Boolean
appointmentToCancel: Long?
Flujo: Botón "Cancelar" → muestra diálogo → confirma → elimina cita → actualiza lista en tiempo real

## 7. COMPILACIÓN Y PRUEBAS
Resultado: BUILD SUCCESSFUL in 3m 9s

Advertencias (no críticas):

Deprecaciones de HorizontalPager (Accompanist → Foundation)
Deprecaciones de íconos AutoMirrored
Warnings de Room sobre índices en FKs
Sin errores de compilación ni runtime

✅[VALIDACIONES IMPLEMENTADAS POR PANTALLA]
Mis Citas (Appointments)
Separación tabs "Próximas" / "Pasadas" funcional
Solo citas próximas muestran botones de acción
Diálogo de confirmación obligatorio para cancelar
Navegación a reprogramar pasa appointmentId correcto
Agendar Cita (Crear)
Campos requeridos: fecha, hora, modalidad, motivo
Validación longitud motivo (5-200 caracteres)
Fecha futura obligatoria
No permite solapes de horarios (ventana 1 hora)
Mensajes de error descriptivos
Reprogramar Cita (Editar)
Prefill completo de datos actuales
Límite: solo 1 reprogramación por cita
Ventana: mínimo 4 horas de anticipación
No permite si está cancelada o pasada
No permite solapes de horarios
Actualiza cita existente (no crea nueva)
Incrementa rescheduleCount automáticamente
Cambia status a RESCHEDULED
Cancelar Cita
Diálogo de confirmación obligatorio
Solo disponible en citas próximas
Actualiza lista en tiempo real
RESULTADO FUNCIONAL
El sistema ahora:

✅[Valida conflictos de horarios al agendar/reprogramar]
Limita reprogramaciones a 1 vez por cita
Requiere 4 horas de anticipación para reprogramar
Pide confirmación antes de cancelar
Guarda y prellenó modalidad y motivo
Muestra mensajes de error descriptivos
Mantiene integridad de datos en BD
Compila sin errores
La aplicación MediTurn está lista para el Día 6 (presentación y documentación) con todas las validaciones de negocio implementadas y funcionales.
