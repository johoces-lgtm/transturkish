# TransTurkish

## Descripción del proyecto

TransTurkish es una aplicación móvil educativa para Android, desarrollada para apoyar el aprendizaje de vocabulario básico del idioma turco al español mediante preguntas interactivas.

El proyecto se desarrolla en la asignatura **Programación de Aplicaciones Móviles**, utilizando Kotlin, Jetpack Compose y Android Studio.

## Tecnologías utilizadas

- Kotlin
- Android Studio
- Jetpack Compose y Material 3
- Navigation Compose
- Arquitectura MVVM
- ViewModel y StateFlow
- DataStore Preferences
- Recursos nativos de Android: cámara y galería
- Git y GitHub para trabajo colaborativo

## Pantallas de la aplicación

### Inicio

Pantalla de bienvenida con accesos al minijuego, al formulario de registro y al perfil del usuario.

### Minijuego de traducción

Actividad interactiva para practicar cuatro palabras turcas y sus traducciones al español.

Incluye:
- Tres alternativas por pregunta.
- Validación de respuestas correctas e incorrectas.
- Retroalimentación visual en verde y rojo.
- Animaciones con Jetpack Compose.
- Puntaje acumulado.
- Opción para volver a jugar y regresar al inicio.

### Registro

Formulario que solicita nombre, correo electrónico, contraseña y dirección.

Incluye validación de campos, contraseña de mínimo seis caracteres, aceptación de términos y condiciones y mensajes de error.

### Resumen

Muestra los datos ingresados en Registro mediante un `UsuarioViewModel` compartido.

### Perfil

Permite utilizar la cámara y seleccionar fotografías desde la galería del dispositivo.

Utiliza `TakePicture()`, `FileProvider`, `ImagenViewModel`, `StateFlow` y el componente reutilizable `ImagenInteligente`.

También incluye una animación de confirmación al cargar una imagen.

## Contenidos aplicados de las guías

- **Guía 7:** estructura del proyecto y herramientas colaborativas.
- **Guía 8:** construcción de interfaces con Jetpack Compose.
- **Guía 9:** adaptabilidad del diseño.
- **Guía 10:** navegación entre pantallas.
- **Guía 11:** formularios, validaciones y estado compartido.
- **Guía 12:** animaciones, estados y DataStore.
- **Guía 13:** integración de cámara y galería.

## Arquitectura del proyecto

La aplicación organiza sus funcionalidades mediante pantallas, componentes reutilizables y ViewModels.

`UsuarioViewModel` administra el estado y las validaciones del formulario.

`ImagenViewModel` administra el estado de la imagen utilizada en Perfil.

El proyecto incluye una implementación de DataStore Preferences para el manejo persistente de preferencias.

## Trabajo colaborativo

**Joaquín:**
- Navegación entre pantallas.
- Inicio.
- Registro y validaciones.
- Resumen.
- Minijuego de traducción.
- Documentación e integración.

**Alonso:**
- Animaciones y estados.
- Implementación de DataStore.
- Cámara y galería.
- Integración de imágenes mediante ViewModel y componentes reutilizables.
- Animación de confirmación en Perfil.

El trabajo se desarrolló mediante ramas independientes de GitHub y posteriormente se integró en `integracion/transturkish`.

## Pruebas realizadas

La versión integrada fue compilada y probada en un dispositivo Android Xiaomi.

Se comprobaron:
- Navegación desde Inicio.
- Funcionamiento del minijuego.
- Registro, validaciones y Resumen.
- Captura de fotografías con cámara.
- Selección de imágenes desde galería.
- Navegación y conservación de la imagen de Perfil durante la navegación.

## Estado del proyecto

Las funcionalidades principales se encuentran integradas y fueron probadas en conjunto.

Se mantiene la revisión final de la persistencia de DataStore y de la versión definitiva destinada a entrega.

## Repositorio

https://github.com/johoces-lgtm/transturkish