# TransTurkish 🇹🇷

## Descripción del proyecto

TransTurkish es una aplicación móvil educativa para Android,
orientada al aprendizaje del idioma turco mediante actividades
interactivas, vocabulario y traducción de textos.

El proyecto integra una aplicación móvil desarrollada en Kotlin
con un microservicio propio en Spring Boot y una API externa
de traducción.

## Integrantes

- Joaquín Hoces
- Alonso Payacán

## Tecnologías utilizadas

- Android Studio
- Kotlin
- Jetpack Compose
- Material 3
- Arquitectura MVVM
- Navigation Compose
- ViewModel y StateFlow
- Kotlin Coroutines
- DataStore Preferences
- Retrofit y Gson
- Spring Boot
- Base de datos H2
- MyMemory Translation API
- Kotest
- MockK
- JUnit
- JaCoCo
- Compose UI Test
- Git, GitHub y Trello

## Funcionalidades

### Inicio

- Interfaz visual con identidad morada y blanca.
- Acceso al minijuego, diccionario y traductor.
- Acceso al perfil y preferencias.

### Minijuego de traducción

- Modalidad conectada al servidor.
- Modalidad sin conexión.
- Hasta diez preguntas por partida conectada.
- Diez preguntas en modalidad local.
- Tres alternativas por pregunta.
- Preguntas y alternativas aleatorias.
- Animaciones de respuestas correctas e incorrectas.
- Barra de progreso y puntaje.
- Resultado final y porcentaje de aciertos.
- Reinicio de partida y cambio de modalidad.

### Diccionario

- Consulta de vocabulario desde Spring Boot.
- Creación de palabras.
- Edición de palabras existentes.
- Eliminación con confirmación.
- Validación de campos obligatorios.
- Indicadores de carga y mensajes de error.
- Actualización del vocabulario utilizado por el minijuego.

### Traductor externo

- Traducción del turco al español.
- Traducción del español al turco.
- Inversión de idiomas.
- Traducción de palabras y frases.
- Validación de texto vacío.
- Validación del límite de 500 bytes.
- Indicadores de carga y resultado animado.
- Consumo independiente del microservicio propio.

### Login y registro

- Formularios reactivos.
- Validación de correo electrónico.
- Validación de contraseña.
- Validación de campos obligatorios.
- Pantalla de resumen del registro.

La autenticación real con el servidor se encuentra pendiente
de integración.

### Perfil

- Captura de fotografías mediante la cámara.
- Selección de fotografías desde la galería.
- Visualización de la imagen de perfil.
- Uso de Activity Result API y FileProvider.

### Preferencias

- Administración de estados.
- Persistencia local mediante DataStore Preferences.

## Arquitectura

La aplicación utiliza MVVM para separar la interfaz visual,
la lógica de negocio y el acceso a datos.

### Organización de paquetes

- data/model: modelos de datos de las API.
- data/remote: servicios e instancias de Retrofit.
- repository: acceso a los servicios remotos.
- model: estados y lógica del dominio.
- viewmodel: administración de estados y lógica.
- ui/screens: pantallas de la aplicación.
- ui/components: componentes reutilizables.
- ui/navigation: rutas y eventos de navegación.
- ui/theme: colores y estilos visuales.

### Flujo de información

Interfaz Compose → ViewModel → Repository → Retrofit → API

Las respuestas se procesan en el ViewModel y se reflejan
en la interfaz mediante estados observables.

## Microservicio propio

El backend de TransTurkish está desarrollado en Spring Boot
y utiliza una base de datos H2.

Su responsabilidad es administrar el vocabulario utilizado
por el diccionario y el minijuego conectado.

Repositorio del backend:

https://github.com/Alonso28-py/transturkish-backend

### URL de desarrollo

Desde el computador:

    http://localhost:8080/

Desde el emulador Android:

    http://10.0.2.2:8080/

La dirección 10.0.2.2 permite al emulador acceder al
servidor ejecutado en el computador anfitrión.

### Endpoints utilizados por la aplicación

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /api/palabras | Consultar vocabulario |
| POST | /api/palabras | Crear una palabra |
| PUT | /api/palabras/{id} | Actualizar una palabra |
| DELETE | /api/palabras/{id} | Eliminar una palabra |

### Ejemplo de datos

    {
        "id": 1,
        "turco": "Merhaba",
        "espanol": "Hola"
    }

Para crear o actualizar una palabra, la aplicación envía
el texto en turco y su traducción al español.

## API externa

### MyMemory Translation API

Servicio utilizado para traducir palabras y frases entre
turco y español.

URL base:

    https://api.mymemory.translated.net/

Endpoint:

    GET /get

Parámetros:

| Parámetro | Descripción |
|-----------|-------------|
| q | Texto que se desea traducir |
| langpair | Idiomas de origen y destino |

Direcciones disponibles:

- Turco a español: tr%7Ces
- Español a turco: es%7Ctr

Ejemplo de consulta:

    https://api.mymemory.translated.net/get?q=Merhaba&langpair=tr%7Ces

El traductor utiliza una instancia de Retrofit independiente
del microservicio propio.

Documentación oficial:

https://mymemory.translated.net/doc/spec.php

El servicio externo tiene límites de uso y requiere conexión
a Internet. No se deben enviar datos confidenciales.

## Instrucciones de ejecución

### Requisitos

- Android Studio.
- Android SDK compatible con el proyecto.
- Emulador Android o dispositivo físico.
- Java compatible con el backend.
- Acceso al repositorio del microservicio.
- Conexión a Internet para el traductor externo.

### Ejecutar el backend

1. Descargar o clonar el repositorio del backend.
2. Abrir una terminal en la carpeta del proyecto Spring Boot.
3. Ejecutar en Windows:

       .\mvnw.cmd spring-boot:run

4. Comprobar que el servidor se encuentre activo.
5. Abrir en el navegador:

       http://localhost:8080/api/palabras

6. Comprobar que existan palabras registradas.

Para una partida conectada de diez preguntas se recomienda
disponer de al menos diez palabras válidas con traducciones
distintas.

### Ejecutar la aplicación Android

1. Clonar o descargar el repositorio:

       https://github.com/johoces-lgtm/transturkish

2. Abrir el proyecto desde Android Studio.
3. Seleccionar la rama de desarrollo correspondiente.
4. Sincronizar Gradle.
5. Encender el emulador.
6. Mantener Spring Boot funcionando.
7. Ejecutar la configuración app mediante Run.

La URL local del backend está configurada en
PalabraRepository.kt.

La conexión HTTP local está habilitada para compilaciones
debug. La configuración de conexión para la entrega firmada
se encuentra pendiente.

## Pruebas automatizadas

### Pruebas unitarias

Se utilizan Kotest, MockK, JUnit y herramientas de pruebas
de corrutinas.

Módulos evaluados:

- LoginViewModel
- UsuarioViewModel
- JuegoViewModel
- GeneradorPreguntas
- NuevaPalabra
- PalabraRepository
- PalabraViewModel
- TraduccionRepository
- TraduccionViewModel

Cantidad de pruebas unitarias implementadas: 76.

Ejecutar desde la terminal:

    .\gradlew.bat :app:testDebugUnitTest

Informe:

    app/build/reports/tests/testDebugUnitTest/index.html

### Pruebas de interfaz

Se utiliza Compose UI Test con JUnit para comprobar:

- Navegación hacia el selector de modalidades.
- Inicio de una partida sin conexión.
- Validación de texto vacío en el traductor.

Se incluyen tres pruebas nuevas y la prueba instrumentada
original del proyecto.

Ejecutar con un emulador activo:

    .\gradlew.bat :app:connectedDebugAndroidTest

### Cobertura

Se utiliza JaCoCo para medir la cobertura del código.

Generar informe:

    .\gradlew.bat :app:createDebugUnitTestCoverageReport

Ubicación:

    app/build/reports/coverage/test/debug/index.html

La medición definitiva de la cobertura de la lógica
se realizará después de integrar ambas ramas.

## Contenidos aplicados de las guías

| Guía | Contenido |
|------|-----------|
| 7 | Configuración, MVVM y colaboración |
| 8 | Construcción visual con Compose |
| 9 | Adaptabilidad del diseño |
| 10 | Navegación |
| 11 | Formularios y validaciones |
| 12 | Animaciones, estados y DataStore |
| 13 | Cámara y galería |
| 14 | Consumo de API REST mediante Retrofit |
| 15 | Pruebas unitarias y de interfaz |

## Distribución del trabajo

### Joaquín Hoces

- Identidad visual.
- Pantalla de inicio y navegación.
- Formularios y validaciones.
- Minijuego local y conectado.
- Integración móvil del vocabulario mediante Retrofit.
- Interfaz CRUD del diccionario.
- Traductor externo.
- Pruebas unitarias y de interfaz.
- Medición de cobertura.
- Documentación técnica.

### Alonso Payacán

- Desarrollo del backend propio en Spring Boot.
- Base de datos y endpoints del vocabulario.
- Integración de recursos nativos.
- Cámara y galería.
- Persistencia de imágenes de perfil.
- Animaciones y preferencias.

## Control de versiones

Ramas de desarrollo:

- main
- feature/joaquin-entrega3
- feature/alonso-entrega3

Cada integrante desarrolla y registra sus avances
mediante commits en su rama correspondiente.

La integración definitiva se encuentra pendiente.

## Planificación con Trello

Pendiente de incorporar el enlace al tablero y las evidencias
reales de planificación y distribución de tareas.

## APK firmado

Pendiente de generar después de la integración definitiva.

Se incorporarán:

- APK firmado.
- Evidencia del proceso de firma.
- Configuración técnica.
- Llave de firma académica según las instrucciones de entrega.

## Estado del proyecto

Avance de desarrollo correspondiente a la Evaluación Parcial 4
de Desarrollo de Aplicaciones Móviles.

Pendientes de integración y comprobación final:

- Autenticación con el backend.
- Persistencia de sesión.
- Categorías y niveles de vocabulario.
- Progreso individual.
- Integración de ramas.
- Cobertura definitiva.
- Configuración del servidor para la entrega.
- APK firmado.
- Evidencias finales de Trello.

## Repositorio Android

https://github.com/johoces-lgtm/transturkish
