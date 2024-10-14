# Jared-Alvidrez-Examen-Practico
Examen practico de Jared Alvidrez para vacante de programador de aplicaciones móviles en Alsuper. 
# Jared-Alvidrez-Examen-Practico
# Task Manager Application

## Descripción del Proyecto

Esta es una aplicación simple de gestión de tareas construida en **Android Nativo** utilizando **Kotlin**. Los usuarios pueden autenticarse utilizando **Firebase Authentication** y manejar sus tareas, que incluyen crear, editar, actualizar y eliminar tareas. Las tareas se guardan localmente en una base de datos **Room**.

## Funcionalidades

- Autenticación de usuarios con **Firebase** (registro e inicio de sesión).
- Gestión de tareas:
  - Añadir nuevas tareas.
  - Editar tareas existentes.
  - Marcar tareas como completadas.
  - Eliminar tareas.
- Persistencia local de tareas usando **Room**.
- Filtros de visualización de tareas:
  - Mostrar tareas completadas.
  - Mostrar tareas pendientes.
  - Mostrar todas las tareas.
- Ordenar tareas por título o estado de completado.

## Tecnologías Utilizadas

- **Kotlin** para el lenguaje de programación.
- **Jetpack Compose** para el desarrollo de la interfaz de usuario.
- **Firebase Authentication** para la autenticación de usuarios.
- **Room** para la persistencia de datos local.
- **LiveData** y **ViewModel** para la gestión de la UI.
- **Coroutines** para la ejecución asíncrona.

## Instrucciones de Instalación

1. Clona este repositorio en tu máquina local.
   ```bash
   git clone https://github.com/tu-usuario/task-manager-app.git
2. Abre el proyecto en Android Studio.
3. Asegúrate de que las siguientes dependencias están añadidas en el archivo build.gradle:
gradle
implementation 'com.google.firebase:firebase-auth:22.0.0'
implementation "androidx.room:room-runtime:2.4.2"
implementation "androidx.room:room-ktx:2.4.2"
implementation 'androidx.lifecycle:lifecycle-livedata-ktx:2.4.1'
implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1'
implementation "androidx.activity:activity-compose:1.4.0"

4. Configura Firebase para el proyecto:
Ve a la Consola de Firebase y crea un nuevo proyecto.
Habilita la autenticación por correo y contraseña.
Descarga el archivo google-services.json y colócalo en la carpeta app del proyecto.
Asegúrate de tener la dependencia de Google Services en el archivo build.gradle:
gradle
classpath 'com.google.gms:google-services:4.3.10'

5. Sincroniza el proyecto con Gradle.
Ejecución de la Aplicación

6. Conéctate a un dispositivo físico o emulador desde Android Studio.
Ejecuta la aplicación usando el botón Run en Android Studio o usa el siguiente comando en la terminal:
bash
./gradlew assembleDebug
Decisiones Técnicas

7. Arquitectura
Se implementó una arquitectura MVVM (Model-View-ViewModel) para asegurar un desacoplamiento entre la lógica de negocio y la interfaz de usuario.
Room fue elegido para la persistencia local debido a su simplicidad y capacidad de trabajar con LiveData.
El manejo de la autenticación se hizo utilizando Firebase Authentication debido a su facilidad de configuración y soporte para múltiples proveedores de autenticación.

8. Estructura del proyecto
   8.1. data
    Esta capa contiene las fuentes de datos, ya sea locales (Room) o remotas (Firebase).
    
    local/db:
    TaskDao: Interfaz para las operaciones CRUD en la base de datos local Room.
    TaskDatabase: Clase abstracta que proporciona acceso a las instancias de TaskDao.
    local/model:
    TaskEntity: Entidad que representa las tareas en la base de datos Room.
    remote:
    FirebaseAuthService: Clase encargada de la autenticación utilizando Firebase Authentication.
    repository:
    TaskRepositoryImpl: Implementación del repositorio que interactúa tanto con la base de datos local (Room) como con Firebase.

   8.2. di 
    Este paquete contiene el archivo de inyección de dependencias.
    
    AppModule: Clase que inicializa las dependencias como la base de datos, repositorio, y ViewModels.
   8.3. domain
    
    Esta capa contiene la lógica de negocio y los modelos de dominio.
    
    model:
    Task: Clase de dominio que representa una tarea en el sistema.
    Funciones de mapeo (toEntity y toDomainModel) para convertir entre Task (dominio) y TaskEntity (base de datos).
    repository:
    TaskRepository: Interfaz para definir las operaciones CRUD.
    usecase:
    Contiene los casos de uso de la aplicación (Add, Delete, Get, Update).

   8.4. presentation
    
    Contiene la lógica de la interfaz de usuario.
    
    auth:
    AuthViewModel: Manejador de autenticación usando Firebase.
    task/ui:
    TaskViewModel: Maneja las tareas (inserción, eliminación, actualización) con LiveData y Room.
    MainScreen, AddEditTaskScreen, AppNavigation: Composables que manejan la UI de la aplicación con Jetpack Compose.

   8.5. ui.theme
    
    Maneja los temas visuales de la aplicación.

10. Persistencia de Datos
Las tareas se guardan en una base de datos local usando Room con un esquema simple de TaskEntity.
Se utiliza un repositorio (TaskRepository) para mediar entre los datos de la base de datos y la UI.
Interfaz de Usuario
La UI está construida completamente con Jetpack Compose, lo que facilita la creación de componentes declarativos.
Se utilizó LiveData para la reactividad en la interfaz, de modo que los cambios en los datos se reflejan automáticamente en la UI.

Contacto

Si tienes alguna pregunta, no dudes en contactar a Jared Alvidrez en jaredalvi@gmail.com
