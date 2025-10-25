# 🏥 MediTurn – Día 1: Planificación y Diseño (Figma)

## 🎯 **Objetivo del día**
Definir el alcance del proyecto, estructurar las épicas e historias de usuario, asignar roles dentro del equipo y desarrollar el prototipo visual en **Figma** que representará el flujo principal de la aplicación **MediTurn**.

---

## 👥 **Asignación de Roles del Equipo**

| 👤 Integrante | 🧩 Rol Principal | 📋 Responsabilidades |
|---------------|------------------|-----------------------|
| **Elver Antony Cholan García** | 🎨 Diseñador UI/UX | Encargado del diseño visual y la experiencia del usuario. Responsable del prototipo en Figma y del desarrollo de la interfaz en Jetpack Compose. |
| **Piero De La Cruz Palpa** | 💻 Líder Técnico y Desarrollador Backend | Responsable de la estructura técnica del proyecto, configuración de la base de datos, manejo de conexiones, lógica de negocio y supervisión de la integración general del sistema. |
| **Steven Manuel Saldaña Meléndez** | 🧠 Documentador y Tester | Encargado de la documentación técnica del proyecto y de la validación de funcionalidades mediante pruebas de calidad y usabilidad. |

---

## 🔗 **Enlaces Importantes**

- 🎨 [Figma MediTurn] 
(https://www.figma.com/design/y3Wko1GvMvOxZcUJGbpxxO/Proyecto--MediTurn-%E2%80%93-Citas-M%C3%A9dicas?node-id=0-1&p=f&t=cEaMzKkkebo39xds-0)  

- 💾 [GitHub MediTurn]
(https://github.com/pierdlcr2006/proyecto-01-moviles-mediturn)

---

## 🧱 **Épicas del Proyecto**

| 🆔 CÓD. | 📖 Descripción de la Épica | 🔥 Prioridad | 🎯 Objetivo Principal |
|---------|-----------------------------|--------------|------------------------|
| **EP-01** | Gestión de usuarios y acceso | Alta | Permitir que los pacientes puedan registrarse, iniciar sesión y gestionar su información personal dentro de la plataforma. |
| **EP-02** | Búsqueda y visualización de médicos | Alta | Facilitar al paciente la búsqueda de médicos por especialidad, nombre o disponibilidad, mostrando detalles relevantes de cada profesional. |
| **EP-03** | Gestión de citas médicas | Alta | Permitir al paciente agendar, modificar o cancelar citas médicas según su disponibilidad. |
| **EP-04** | Historial y recordatorios de citas | Media | Ofrecer un registro completo de citas pasadas y próximas, además de notificaciones recordatorias. |
| **EP-05** | Panel principal y navegación general | Media | Proporcionar un menú central que permita al paciente acceder a las secciones del sistema de manera intuitiva. |

---

## 📚 **Historias de Usuario y Criterios de Aceptación**

### 🧩 **EP-01 – Gestión de Usuarios y Acceso**

| 🆔 Código | 📝 Detalle | 🔥 Prioridad | ⏱️ Esfuerzo | ✅ Criterio de Aceptación |
|-----------|-------------|--------------|-------------|----------------------------|
| **HU-01** | Yo como paciente debo poder registrarme en la aplicación para acceder a los servicios médicos. | Alta | 8 | El sistema permite registrar usuarios nuevos y validar que no existan duplicados. |
| **HU-02** | Yo como paciente debo poder iniciar sesión con mis credenciales registradas. | Alta | 5 | El login valida credenciales y redirige correctamente al panel principal. |
| **HU-03** | Yo como paciente debo poder actualizar mis datos personales desde mi perfil. | Media | 3 | El sistema guarda los cambios del perfil y los muestra actualizados en la interfaz. |

---

### 🧩 **EP-02 – Búsqueda y Visualización de Médicos**

| 🆔 Código | 📝 Detalle | 🔥 Prioridad | ⏱️ Esfuerzo | ✅ Criterio de Aceptación |
|-----------|-------------|--------------|-------------|----------------------------|
| **HU-04** | Yo como paciente debo poder buscar médicos por especialidad o nombre. | Alta | 8 | El sistema filtra y muestra médicos según los criterios de búsqueda ingresados. |
| **HU-05** | Yo como paciente debo poder visualizar el perfil y disponibilidad de un médico seleccionado. | Alta | 5 | Al seleccionar un médico, se muestra su perfil, especialidad y horarios disponibles. |

---

### 🧩 **EP-03 – Gestión de Citas Médicas**

| 🆔 Código | 📝 Detalle | 🔥 Prioridad | ⏱️ Esfuerzo | ✅ Criterio de Aceptación |
|-----------|-------------|--------------|-------------|----------------------------|
| **HU-06** | Yo como paciente debo poder agendar una cita médica seleccionando fecha, hora y motivo. | Alta | 13 | El sistema registra citas válidas y muestra una confirmación exitosa. |
| **HU-07** | Yo como paciente debo poder reprogramar o cancelar una cita previamente agendada. | Alta | 8 | El sistema actualiza o elimina correctamente la cita y refleja los cambios en el calendario. |

---

### 🧩 **EP-04 – Historial y Recordatorios de Citas**

| 🆔 Código | 📝 Detalle | 🔥 Prioridad | ⏱️ Esfuerzo | ✅ Criterio de Aceptación |
|-----------|-------------|--------------|-------------|----------------------------|
| **HU-08** | Yo como paciente debo poder visualizar mis citas pasadas y próximas. | Media | 5 | El sistema muestra citas organizadas por estado (próximas y pasadas). |
| **HU-09** | Yo como paciente debo recibir recordatorios automáticos antes de mis citas. | Media | 3 | El sistema genera notificaciones previas con la fecha y hora de la cita. |

---

### 🧩 **EP-05 – Panel Principal y Navegación General**

| 🆔 Código | 📝 Detalle | 🔥 Prioridad | ⏱️ Esfuerzo | ✅ Criterio de Aceptación |
|-----------|-------------|--------------|-------------|----------------------------|
| **HU-10** | Yo como paciente debo poder acceder a todas las secciones del sistema desde un menú principal. | Media | 5 | El panel principal muestra accesos funcionales a Inicio, Búsqueda, Agendar Cita, Mis Citas y Perfil. |

---

## ✅ **Resultado del Día**
Se completó la definición de las **épicas**, **historias de usuario** y **criterios de aceptación**.  
Además, se creó el **repositorio** y se avanzó en el **diseño del prototipo visual en Figma**, incluyendo las pantallas principales:  
**Inicio**, **Búsqueda**, **Detalle del Médico**, **Agendar Cita**, **Mis Citas** y **Perfil**.


## Día 2 – Configuración del Proyecto y Estructura Base

### Objetivo del día
Establecer la base técnica del proyecto en **Kotlin con Jetpack Compose**, definiendo la estructura de paquetes (`ui`, `model`, `navigation`, `data`, `util`) y configurando la navegación principal entre pantallas.  
Se organizaron las ramas de trabajo por integrante y se aplicaron convenciones de commits para un flujo de desarrollo ordenado.

### Proyecto en Kotlin
- Lenguaje: Kotlin  
- UI: Jetpack Compose  
- Estructura de paquetes: `ui`, `model`, `navigation`, `data`, `util`  
- Navegación principal configurada entre pantallas  

### Ramas del Proyecto

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
