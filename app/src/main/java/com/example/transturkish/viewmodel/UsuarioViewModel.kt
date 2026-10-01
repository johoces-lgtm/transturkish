package com.example.transturkish.viewmodel

import androidx.lifecycle.ViewModel
import com.example.transturkish.model.UsuarioErrores
import com.example.transturkish.model.UsuarioUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UsuarioViewModel : ViewModel() {
    // Estado interno mutable
    private val _estado = MutableStateFlow(UsuarioUiState())
    // Estado expuesto para la UI
    val estado = _estado.asStateFlow()

    fun onNombreChange(valor: String) {
        _estado.update { it.copy(nombre = valor, errores = it.errores.copy(nombre = null)) }
    }

    fun onCorreoChange(valor: String) {
        _estado.update { it.copy(correo = valor, errores = it.errores.copy(correo = null)) }
    }

    fun onClaveChange(valor: String) {
        _estado.update { it.copy(clave = valor, errores = it.errores.copy(clave = null)) }
    }

    fun onDireccionChange(valor: String) {
        _estado.update { it.copy(direccion = valor, errores = it.errores.copy(direccion = null)) }
    }

    fun onAceptarTerminosChange(valor: Boolean) {
        _estado.update { it.copy(aceptaTerminos = valor) }
    }

    // Validación global del formulario
    fun validarFormulario(): Boolean {
        val estadoActual = estado.value
        val errores = UsuarioErrores(
            nombre = if (estadoActual.nombre.isBlank()) "Campo obligatorio" else null,
            correo = if (!estadoActual.correo.contains("@")) "Correo inválido" else null,
            clave = if (estadoActual.clave.length < 5) "Debe tener al menos 5 caracteres" else null,
            direccion = if (estadoActual.direccion.isBlank()) "Campo obligatorio" else null
        )

        val hayErrores = listOfNotNull(errores.nombre, errores.correo, errores.clave, errores.direccion).isNotEmpty()

        _estado.update { it.copy(errores = errores) }

        return !hayErrores && estadoActual.aceptaTerminos
    }
}