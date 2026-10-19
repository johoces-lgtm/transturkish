# TransTurkish 🇹🇷

## Descripción del proyecto

**TransTurkish** es una aplicación móvil educativa desarrollada para Android, cuyo objetivo es apoyar el aprendizaje de vocabulario básico del idioma turco al español mediante actividades interactivas.

El proyecto fue desarrollado para la asignatura **Programación de Aplicaciones Móviles**, utilizando Kotlin, Jetpack Compose y Android Studio.

La aplicación permite practicar traducciones, registrar información mediante formularios y utilizar recursos nativos del dispositivo, como la cámara y la galería.

## Integrantes

- **Joaquín Hoces**
- **Alonso Payacán**

## Tecnologías utilizadas

- **Android Studio:** entorno de desarrollo.
- **Kotlin:** lenguaje de programación.
- **Jetpack Compose:** construcción de interfaces.
- **Material 3:** componentes visuales.
- **Navigation Compose:** navegación entre pantallas.
- **MVVM:** separación de interfaz y lógica.
- **ViewModel y StateFlow:** administración y observación de estados.
- **DataStore Preferences:** almacenamiento local de preferencias.
- **Activity Result API:** integración de cámara y galería.
- **Git y GitHub:** control de versiones y colaboración.

## Pantallas de la aplicación

### 1. Inicio

Pantalla principal de bienvenida a TransTurkish.

Permite acceder a:
- Minijuego de traducción.
- Registro de usuario.
- Perfil.
- Preferencias mediante el menú lateral.

### 2. Minijuego de traducción

Permite practicar vocabulario básico del turco al español.

**Características:**
- Cuatro palabras turcas.
- Tres alternativas por pregunta.
- Comprobación de respuestas.
- Mensajes verdes para respuestas correctas.
- Mensajes rojos para respuestas incorrectas.
- Animaciones con `AnimatedVisibility` y `animateColorAsState`.
- Puntaje acumulado.
- Opción para volver a jugar.
- Botón para regresar a Inicio.

### 3. Registro

Formulario que solicita:
- Nombre.
- Correo electrónico.
- Contraseña.
- Dirección.
- Aceptación de términos y condiciones.

**Validaciones implementadas:**
- Campos obligatorios.
- Correo con comprobación básica de formato.
- Contraseña de al menos seis caracteres.
- Aceptación obligatoria de términos y condiciones.
- Mensajes de error e íconos en los campos.

Las validaciones se encuentran centralizadas en `UsuarioViewModel.kt`.

El formulario dispone de desplazamiento vertical y un botón Registrar accesible cuando se muestra el teclado.

### 4. Resumen

Muestra los datos ingresados en Registro mediante un ViewModel compartido.

La contraseña se presenta mediante asteriscos.

### 5. Perfil

Permite utilizar recursos nativos del dispositivo.

**Funcionalidades:**
- Seleccionar fotografías desde la galería.
- Tomar fotografías con la cámara.
- Mostrar la imagen seleccionada o capturada.
- Presentar un ícono predeterminado cuando no existe una fotografía.
- Mostrar un mensaje de confirmación animado.

Se utilizan:
- `ActivityResultContracts.GetContent()`.
- `ActivityResultContracts.TakePicture()`.
- `FileProvider`.
- `ImagenViewModel`.
- `StateFlow`.
- El componente reutilizable `ImagenInteligente`.

## Arquitectura del proyecto

El proyecto utiliza los principios de la arquitectura **MVVM (Model-View-ViewModel)** para separar la lógica de la interfaz.

### ViewModels principales

**UsuarioViewModel.kt**

Administra los datos del formulario, las validaciones y el estado compartido entre Registro y Resumen.

**ImagenViewModel.kt**

Administra el estado de la imagen seleccionada o capturada en Perfil.

**EstadoViewModel.kt**

Administra el estado de una preferencia y utiliza DataStore para guardar y recuperar su valor.

**MainViewModel.kt**

Gestiona eventos de navegación entre las pantallas.

### Componentes reutilizables

**MiniJuegoTraduccion.kt**

Contiene la actividad interactiva de traducción turco-español.

**ImagenInteligente.kt**

Muestra una fotografía circular cuando existe una imagen o un ícono cuando no se ha seleccionado ninguna.

## Persistencia local con DataStore

TransTurkish implementa **DataStore Preferences** para almacenar localmente un estado booleano.

La funcionalidad está disponible en:

**Inicio → Menú lateral → Preferencias**

### Comprobación de persistencia

1. Abrir TransTurkish.
2. Entrar a Preferencias.
3. Presionar el botón Activar.
4. Comprobar que el botón cambie a Desactivar.
5. Cerrar completamente la aplicación.
6. Volver a abrirla.
7. Entrar nuevamente a Preferencias.
8. Comprobar que el botón siga mostrando Desactivar.

Esta prueba se realizó correctamente en un dispositivo Android Xiaomi, confirmando que el estado se recupera después de cerrar y volver a abrir la aplicación.

## Contenidos aplicados de las guías

| Guía | Contenido aplicado |
|---|---|
| Guía 7 | Configuración del proyecto, MVVM y herramientas colaborativas |
| Guía 8 | Construcción visual con Jetpack Compose |
| Guía 9 | Diseño adaptable y organización de pantallas |
| Guía 10 | Navegación mediante NavHost y NavController |
| Guía 11 | Formularios, validaciones y estado compartido |
| Guía 12 | Animaciones, estados y DataStore |
| Guía 13 | Integración de cámara y galería |

## Distribución del trabajo

El proyecto se desarrolló colaborativamente mediante GitHub, utilizando ramas independientes.

### Joaquín Hoces

- Navegación entre pantallas.
- Pantalla Inicio.
- Registro y validaciones.
- Pantalla Resumen.
- Minijuego de traducción.
- Animaciones del minijuego.
- Mejoras de adaptabilidad.
- Documentación README.
- Integración de las ramas.

### Alonso Payacán

- Implementación de animaciones y estados.
- DataStore Preferences.
- Integración de cámara y galería.
- Configuración de FileProvider.
- ImagenViewModel y StateFlow.
- Componente ImagenInteligente.
- Animación de confirmación en Perfil.

### Organización con GitHub

Se utilizaron las siguientes ramas:

- `feature/joaquin-navegacion`
- `feature/estado-animaciones`
- `integracion/transturkish`
- `main`

Los avances de ambos integrantes fueron integrados en `main`, que corresponde a la rama principal del repositorio.

## Instrucciones para ejecutar la aplicación

### Requisitos

- Android Studio.
- Android SDK compatible con la configuración del proyecto.
- Dispositivo Android o emulador.

### Pasos de ejecución

1. Ingresar al repositorio de GitHub:

   https://github.com/johoces-lgtm/transturkish

2. Clonar el repositorio o descargar el proyecto en formato ZIP.

3. Abrir Android Studio.

4. Seleccionar **Open** y abrir la carpeta del proyecto.

5. Comprobar que se esté utilizando la rama `main`.

6. Esperar a que finalice la sincronización de Gradle.

7. Conectar un dispositivo Android mediante USB o iniciar un emulador.

8. Seleccionar la configuración de ejecución `app`.

9. Presionar **Run** para compilar, instalar y ejecutar TransTurkish.

## Pruebas realizadas

La aplicación integrada fue probada en un dispositivo Android Xiaomi.

Se comprobaron las siguientes funcionalidades:

- Inicio y navegación.
- Minijuego de cuatro preguntas.
- Puntaje y mensajes de respuesta.
- Animaciones y colores del minijuego.
- Formulario de Registro.
- Validación de campos obligatorios.
- Validación de correo electrónico.
- Validación de contraseña.
- Aceptación de términos y condiciones.
- Visualización de datos en Resumen.
- Selección de fotografías desde la galería.
- Captura de fotografías mediante la cámara.
- Visualización de imágenes en Perfil.
- Navegación y conservación del estado de imagen durante la navegación.
- Persistencia local con DataStore después de cerrar y volver a abrir la aplicación.

## Estado del proyecto

Las funcionalidades principales de TransTurkish se encuentran integradas en la rama `main` y han sido probadas en conjunto.

El proyecto utiliza las herramientas y los conceptos trabajados durante las Guías 7 a 13 de la asignatura Programación de Aplicaciones Móviles.

## Repositorio

https://github.com/johoces-lgtm/transturkish