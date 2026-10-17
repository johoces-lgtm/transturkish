# Transturkish

## Descripción del proyecto

Transturkish es una aplicación móvil educativa desarrollada para Android, cuyo objetivo es apoyar el aprendizaje de vocabulario básico del idioma turco al español.

El proyecto se desarrolla en la asignatura **Programación de Aplicaciones Móviles** utilizando Kotlin y Jetpack Compose.

## Tecnologías utilizadas

- Android Studio.
- Kotlin.
- Jetpack Compose.
- Material 3.
- Navigation Compose.
- Arquitectura MVVM.
- ViewModel y StateFlow.
- DataStore Preferences.

## Pantallas principales

**Inicio:** presenta Transturkish y permite acceder a las diferentes funcionalidades.

**Registro:** contiene un formulario de nombre, correo electrónico, contraseña y dirección, con validaciones y mensajes de error.

**Resumen:** muestra los datos ingresados en el formulario mediante un ViewModel compartido.

**Perfil:** pantalla destinada a integrar recursos nativos del dispositivo, como cámara y galería.

## Funcionalidades implementadas y probadas

- Navegación entre Inicio, Registro, Resumen y Perfil.
- Validación de campos del formulario.
- Validación de contraseña de mínimo seis caracteres.
- Validación de aceptación de términos y condiciones.
- Mensajes visuales de error.
- Conservación de los datos del formulario al regresar desde Resumen.
- Pantalla de práctica con animaciones y gestión de estado.
- Captura de fotografías de vista previa utilizando la cámara del dispositivo.

## Funcionalidades pendientes de integración

- Cámara y galería dentro de Perfil.
- Verificación de persistencia de DataStore después de reiniciar la aplicación.
- Integración de animaciones y persistencia en las pantallas principales.
- Minijuego básico de vocabulario turco-español.
- Pruebas finales de la aplicación integrada.

## Organización del trabajo

El desarrollo se realiza colaborativamente mediante GitHub, utilizando ramas de trabajo para organizar los avances.

**Joaquín:** navegación, Inicio, Registro, Resumen, validaciones e integración.

**Alonso:** animaciones, DataStore, recursos nativos y minijuego básico.

## Repositorio

https://github.com/johoces-lgtm/transturkish