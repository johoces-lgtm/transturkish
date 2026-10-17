
# Transturkish

## Descripción del proyecto

Transturkish es una aplicación móvil educativa para Android
que busca apoyar el aprendizaje de vocabulario básico
del idioma turco al español.

El proyecto se desarrolla en la asignatura
Programación de Aplicaciones Móviles.

## Tecnologías utilizadas

- Android Studio
- Kotlin
- Jetpack Compose
- Material 3
- Navigation Compose
- Arquitectura MVVM
- ViewModel y StateFlow
- DataStore Preferences

## Pantallas de la aplicación

### Inicio
Presenta Transturkish y permite acceder al minijuego,
al formulario de registro y al perfil.

### Minijuego
Permite practicar vocabulario turco-español mediante
cuatro preguntas con tres alternativas cada una.

Incluye:
- Respuestas correctas e incorrectas.
- Mensajes visuales de retroalimentación.
- Colores verde y rojo con animaciones.
- Puntaje acumulado.
- Opción para volver a jugar.
- Botón para regresar a Inicio.

### Registro
Formulario que solicita nombre, correo electrónico,
contraseña y dirección.

Incluye validaciones, mensajes de error y aceptación
de términos y condiciones.

### Resumen
Muestra los datos del formulario utilizando
un ViewModel compartido.

### Perfil
Pantalla destinada a utilizar recursos nativos
del dispositivo, como la cámara y la galería.

## Funcionalidades implementadas en la rama de Joaquín

- Navegación entre las pantallas principales.
- Formulario con validaciones.
- Contraseña de mínimo seis caracteres.
- Validación de términos y condiciones.
- Mensajes de error en los campos.
- Datos compartidos entre Registro y Resumen.
- Minijuego de cuatro palabras turcas.
- Puntaje y retroalimentación de respuestas.
- Animaciones de aparición y cambio de color.
- Navegación entre Inicio y Minijuego.
- Interfaz de Inicio simplificada.

## Avances en la rama de Alonso

- Implementación de ejercicios de animaciones y estados.
- Implementación de DataStore Preferences.
- Integración de cámara y galería en Perfil.
- Uso de TakePicture y FileProvider.

Estos cambios se desarrollan en una rama separada
y están pendientes de integración final.

## Pendiente

- Integrar los avances de ambas ramas.
- Completar los componentes pendientes de la Guía 13.
- Revisar la integración final de DataStore.
- Comprobar el funcionamiento de la aplicación integrada.

## Organización del trabajo

El desarrollo se realiza colaborativamente mediante
GitHub, utilizando ramas de trabajo.

### Joaquín
- Inicio y navegación.
- Registro y validaciones.
- Resumen.
- Minijuego de traducción.
- README e integración final.

Rama: feature/joaquin-navegacion

### Alonso
- Animaciones y estados.
- DataStore.
- Cámara y galería.
- Componentes asociados a recursos nativos.

Rama: feature/estado-animaciones

## Repositorio

https://github.com/johoces-lgtm/transturkish
